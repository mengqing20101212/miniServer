package ly.script;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Isolated loader used exactly once for one pushed class bundle. */
public final class RuntimeScriptClassLoader extends ClassLoader implements AutoCloseable {
  private Map<String, byte[]> definitions;
  private final List<Class<?>> definedClasses = new ArrayList<>();

  public RuntimeScriptClassLoader(ClassLoader parent, Map<String, byte[]> definitions) {
    super(parent);
    this.definitions = new LinkedHashMap<>(definitions);
  }

  @Override
  protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    synchronized (getClassLoadingLock(name)) {
      Class<?> loaded = findLoadedClass(name);
      if (loaded == null && definitions.containsKey(name)) {
        loaded = findClass(name);
      }
      if (loaded == null) {
        loaded = super.loadClass(name, false);
      }
      if (resolve) {
        resolveClass(loaded);
      }
      return loaded;
    }
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    byte[] bytes = definitions.get(name);
    if (bytes == null) {
      throw new ClassNotFoundException(name);
    }
    Class<?> defined = defineClass(name, bytes, 0, bytes.length);
    definedClasses.add(defined);
    return defined;
  }

  public List<Class<?>> getDefinedClasses() {
    return List.copyOf(definedClasses);
  }

  @Override
  public void close() {
    definitions.clear();
    definitions = Map.of();
    definedClasses.clear();
  }
}
