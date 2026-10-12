package ly.logic.hero;

import java.nio.file.Files;
import java.nio.file.Path;
import ly.ParserExcelConfig;
import ly.config.HeroInfoConfigManager;
import ly.config.HeroExpConfigManager;
import ly.config.HeroStarConfigManager;
import ly.config.HeroAwakenConfigManager;

/** 从仓库 Excel 准备英雄测试配置，不依赖本机预先导出的目录。 */
public final class HeroTestConfig {
    private static boolean loaded;
    private HeroTestConfig() {}

    public static synchronized void load() throws Exception {
        if (loaded) { return; }
        Path root = Path.of("").toAbsolutePath();
        while (root != null && !Files.isDirectory(root.resolve("excel"))) {
            root = root.getParent();
        }
        if (root == null) {
            throw new IllegalStateException("无法定位仓库 Excel 目录");
        }
        Path input = Path.of("target/hero-test-excel");
        Path output = Path.of("target/hero-test-config");
        Files.createDirectories(input);
        for (String name : new String[]{"heroInfo", "heroExp", "heroStar", "heroAwaken"}) {
            Files.copy(root.resolve("excel/" + name + ".xlsx"), input.resolve(name + ".xlsx"),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        new ParserExcelConfig(input.toString(), output.toString(), false).startParser();
        String directory = output.toAbsolutePath().toString();
        new HeroInfoConfigManager().loadConfig(ly.LoggerDef.SystemLogger, directory);
        new HeroExpConfigManager().loadConfig(ly.LoggerDef.SystemLogger, directory);
        new HeroStarConfigManager().loadConfig(ly.LoggerDef.SystemLogger, directory);
        new HeroAwakenConfigManager().loadConfig(ly.LoggerDef.SystemLogger, directory);
        loaded = true;
    }
}
