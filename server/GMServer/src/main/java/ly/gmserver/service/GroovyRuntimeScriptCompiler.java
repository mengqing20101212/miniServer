package ly.gmserver.service;

import groovy.lang.GroovyClassLoader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import ly.script.ClassBundleCodec;
import ly.script.GmRuntimeScript;
import ly.script.RuntimeScriptClassLoader;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.tools.GroovyClass;
import org.springframework.stereotype.Component;

/** GM-only Groovy compiler. Target servers receive bytecode and never compile source. */
@Component
public class GroovyRuntimeScriptCompiler {
  public static final int MAX_SOURCE_BYTES = 256 * 1024;

  public CompilationResult compile(String source, String entryClass) throws Exception {
    if (source == null || source.isBlank()) {
      throw new IllegalArgumentException("Groovy 源码不能为空");
    }
    if (source.getBytes(StandardCharsets.UTF_8).length > MAX_SOURCE_BYTES) {
      throw new IllegalArgumentException("Groovy 源码超过 " + MAX_SOURCE_BYTES + " 字节");
    }
    if (entryClass == null
        || !entryClass.matches("scripts(?:\\.[A-Za-z_$][A-Za-z0-9_$]*)+")) {
      throw new IllegalArgumentException("入口类必须位于 scripts 包");
    }

    CompilerConfiguration configuration = new CompilerConfiguration();
    configuration.setTargetBytecode(CompilerConfiguration.JDK25);
    ClassLoader parent = Thread.currentThread().getContextClassLoader();
    try (GroovyClassLoader compilerLoader = new GroovyClassLoader(parent, configuration)) {
      CompilationUnit unit =
          new CompilationUnit(configuration, (java.security.CodeSource) null, compilerLoader);
      unit.addSource(entryClass.replace('.', '/') + ".groovy", source);
      unit.compile(Phases.CLASS_GENERATION);

      Map<String, byte[]> classes = new LinkedHashMap<>();
      List<GroovyClass> generatedClasses = unit.getClasses();
      for (GroovyClass generatedClass : generatedClasses) {
        classes.put(generatedClass.getName(), generatedClass.getBytes());
      }
      if (!classes.containsKey(entryClass)) {
        throw new IllegalArgumentException("编译结果中没有入口类: " + entryClass);
      }
      byte[] bundle = ClassBundleCodec.encode(classes);
      verifyEntryClass(entryClass, classes);
      String sha256 =
          HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bundle));
      return new CompilationResult(bundle, sha256, List.copyOf(classes.keySet()));
    }
  }

  private void verifyEntryClass(String entryClass, Map<String, byte[]> classes) throws Exception {
    try (RuntimeScriptClassLoader loader =
        new RuntimeScriptClassLoader(getClass().getClassLoader(), classes)) {
      Class<?> type = loader.loadClass(entryClass);
      if (!GmRuntimeScript.class.isAssignableFrom(type)) {
        throw new IllegalArgumentException("入口类必须实现 ly.script.GmRuntimeScript");
      }
    }
  }

  public record CompilationResult(byte[] bundle, String sha256, List<String> classNames) {}
}
