package ly.startup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ly.config.ServerConfig;
import ly.config.ServerTypeEnum;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * 启动配置加载器，从 STARTUP.SKILL.md 读取默认参数并校验各服务启动配置。
 */
public final class StartupSkillLoader {
    private static final String SKILL_FILE_NAME = "STARTUP.SKILL.md";
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());
    private static volatile StartupSkill cachedSkill;
    private static volatile Path cachedPath;

    private StartupSkillLoader() {
    }

    public static StartupSkill loadRequired() {
        StartupSkill local = cachedSkill;
        if (local != null) {
            return local;
        }
        synchronized (StartupSkillLoader.class) {
            local = cachedSkill;
            if (local != null) {
                return local;
            }
            Path skillPath = findSkillFile();
            String markdown = readFile(skillPath);
            String frontMatter = extractFrontMatter(markdown, skillPath);
            try {
                local = YAML_MAPPER.readValue(frontMatter, StartupSkill.class);
            } catch (IOException e) {
                throw new IllegalStateException("解析启动 skill 文件失败: " + skillPath, e);
            }
            validate(local, skillPath);
            cachedSkill = local;
            cachedPath = skillPath;
            return local;
        }
    }

    public static ResolvedServerArgs resolveLoginArgs() {
        StartupSkill skill = loadRequired();
        return resolveServerArgs(skill.startup.login, null, ServerTypeEnum.LOGIN);
    }

    public static ResolvedServerArgs resolveServerArgs(ServerTypeEnum serverType, String[] args) {
        StartupSkill skill = loadRequired();
        StartupServer section = getServerSection(skill, serverType);
        if (args == null || args.length == 0) {
            return resolveServerArgs(section, null, serverType);
        }
        if (args.length != 3) {
            throw new IllegalArgumentException(serverType.getType() + " 启动参数必须是 3 个: <nacosUrl> <env> <serverId>");
        }
        return resolveServerArgs(section, new CliServerArgs(args[0], args[1], args[2]), serverType);
    }

    public static ResolvedBotArgs resolveBotArgs(String[] args) {
        StartupSkill skill = loadRequired();
        StartupBot bot = requireNonNull(skill.startup.bot, "startup.bot", cachedPath);
        requireNonBlank(bot.command, "startup.bot.command", cachedPath);
        requireNonBlank(bot.loginHost, "startup.bot.loginHost", cachedPath);
        requirePositive(bot.loginHttpPort, "startup.bot.loginHttpPort", cachedPath);
        requirePositive(bot.numBots, "startup.bot.numBots", cachedPath);

        if (args == null || args.length == 0) {
            return new ResolvedBotArgs(bot.command, bot.loginHost, bot.loginHttpPort, bot.numBots);
        }
        if (!Objects.equals(args[0], bot.command)) {
            if ("--test-protocol".equals(args[0])) {
                return new ResolvedBotArgs("--test-protocol", null, null, null);
            }
            throw new IllegalArgumentException("Bot 启动命令与 skill 不一致, skill=" + bot.command + ", cli=" + args[0]);
        }
        if (args.length == 1) {
            return new ResolvedBotArgs(bot.command, bot.loginHost, bot.loginHttpPort, bot.numBots);
        }
        if (args.length != 4) {
            throw new IllegalArgumentException("Bot 启动参数必须是 1 个或 4 个: --run-bots <host> <port> <num>");
        }
        requireEquals(bot.loginHost, args[1], "startup.bot.loginHost", "cli.host");
        requireEquals(String.valueOf(bot.loginHttpPort), args[2], "startup.bot.loginHttpPort", "cli.port");
        requireEquals(String.valueOf(bot.numBots), args[3], "startup.bot.numBots", "cli.numBots");
        return new ResolvedBotArgs(bot.command, bot.loginHost, bot.loginHttpPort, bot.numBots);
    }

    public static void validateServerConfig(ServerTypeEnum serverType, ServerConfig serverConfig) {
        if (serverConfig == null) {
            throw new IllegalArgumentException("serverConfig 不能为空");
        }
        StartupServer section = getServerSection(loadRequired(), serverType);
        if (section.netPort != null && section.netPort > 0 && serverConfig.serverPort != section.netPort) {
            throw new IllegalStateException(
                    "Nacos 配置端口与 skill 不一致, serverType=" + serverType.getType()
                            + ", skill.netPort=" + section.netPort
                            + ", nacos.serverPort=" + serverConfig.serverPort);
        }
    }

    private static ResolvedServerArgs resolveServerArgs(StartupServer section, CliServerArgs cli, ServerTypeEnum serverType) {
        requireNonNull(section, "startup." + serverType.getType().toLowerCase(), cachedPath);
        requireNonBlank(section.serverType, "startup." + serverType.getType().toLowerCase() + ".serverType", cachedPath);
        requireNonBlank(section.serverId, "startup." + serverType.getType().toLowerCase() + ".serverId", cachedPath);
        requireNonBlank(section.env, "startup." + serverType.getType().toLowerCase() + ".env", cachedPath);
        if (!serverType.getType().equalsIgnoreCase(section.serverType)) {
            throw new IllegalStateException("skill 中的 serverType 不匹配, expect=" + serverType.getType() + ", actual=" + section.serverType);
        }

        String nacosUrl = loadRequired().startup.nacos.url;
        String env = section.env;
        String serverId = section.serverId;
        if (cli != null) {
            requireEquals(nacosUrl, cli.nacosUrl, "startup.nacos.url", "cli.nacosUrl");
            requireEquals(env, cli.env, "startup." + serverType.getType().toLowerCase() + ".env", "cli.env");
            requireEquals(serverId, cli.serverId, "startup." + serverType.getType().toLowerCase() + ".serverId", "cli.serverId");
        }
        return new ResolvedServerArgs(nacosUrl, section.serverType, serverId, env, section.netPort, section.springPort);
    }

    private static StartupServer getServerSection(StartupSkill skill, ServerTypeEnum serverType) {
        requireNonNull(skill.startup, "startup", cachedPath);
        return switch (serverType) {
            case LOGIN -> skill.startup.login;
            case GAME -> skill.startup.game;
            case GATE -> skill.startup.gate;
            default -> throw new IllegalArgumentException("不支持的 serverType: " + serverType.getType());
        };
    }

    private static void validate(StartupSkill skill, Path skillPath) {
        requireNonBlank(skill.name, "name", skillPath);
        requireNonBlank(skill.description, "description", skillPath);
        requireNonNull(skill.startup, "startup", skillPath);
        requireNonNull(skill.startup.nacos, "startup.nacos", skillPath);
        requireNonBlank(skill.startup.nacos.url, "startup.nacos.url", skillPath);
        validateServer(skill.startup.login, ServerTypeEnum.LOGIN, skillPath);
        validateServer(skill.startup.game, ServerTypeEnum.GAME, skillPath);
        validateServer(skill.startup.gate, ServerTypeEnum.GATE, skillPath);
        StartupValidation validation = skill.startup.validation;
        if (validation != null && validation.loginSpringPortOffset != null
                && skill.startup.login.netPort != null && skill.startup.login.springPort != null) {
            int expectedPort = skill.startup.login.netPort + validation.loginSpringPortOffset;
            if (expectedPort != skill.startup.login.springPort) {
                throw new IllegalStateException("LoginServer springPort 与 offset 规则不一致, expect=" + expectedPort
                        + ", actual=" + skill.startup.login.springPort);
            }
        }
        if (validation != null && Boolean.TRUE.equals(validation.requireBotHttpPortEqualsLoginSpringPort)) {
            requireNonNull(skill.startup.bot, "startup.bot", skillPath);
            if (!Objects.equals(skill.startup.bot.loginHttpPort, skill.startup.login.springPort)) {
                throw new IllegalStateException("Bot 登录端口必须等于 LoginServer springPort");
            }
        }
        if (validation != null && validation.startupOrder != null && !validation.startupOrder.isEmpty()) {
            List<String> expectedOrder = List.of("login", "game", "gate", "bot");
            if (!validation.startupOrder.equals(expectedOrder)) {
                throw new IllegalStateException("startup.validation.startupOrder 必须是 " + expectedOrder);
            }
        }
    }

    private static void validateServer(StartupServer server, ServerTypeEnum serverType, Path skillPath) {
        String prefix = "startup." + serverType.getType().toLowerCase();
        requireNonNull(server, prefix, skillPath);
        requireNonBlank(server.serverType, prefix + ".serverType", skillPath);
        requireNonBlank(server.serverId, prefix + ".serverId", skillPath);
        requireNonBlank(server.env, prefix + ".env", skillPath);
        if (!serverType.getType().equalsIgnoreCase(server.serverType)) {
            throw new IllegalStateException(prefix + ".serverType 必须是 " + serverType.getType());
        }
        if (server.netPort != null) {
            requirePositive(server.netPort, prefix + ".netPort", skillPath);
        }
        if (server.springPort != null) {
            requirePositive(server.springPort, prefix + ".springPort", skillPath);
        }
    }

    private static Path findSkillFile() {
        Path current = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        while (current != null) {
            Path candidate = current.resolve(SKILL_FILE_NAME);
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }
        throw new IllegalStateException("未找到启动 skill 文件: " + SKILL_FILE_NAME);
    }

    private static String readFile(Path skillPath) {
        try {
            return Files.readString(skillPath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("读取启动 skill 文件失败: " + skillPath, e);
        }
    }

    private static String extractFrontMatter(String markdown, Path skillPath) {
        if (!markdown.startsWith("---")) {
            throw new IllegalStateException("启动 skill 文件缺少 YAML front matter: " + skillPath);
        }
        int start = markdown.startsWith("---\r\n") ? 5 : 4;
        int end = markdown.indexOf("\n---", start);
        if (end < 0) {
            end = markdown.indexOf("\r\n---", start);
        }
        if (end < 0) {
            throw new IllegalStateException("启动 skill 文件 front matter 不完整: " + skillPath);
        }
        String frontMatter = markdown.substring(start, end).trim();
        if (frontMatter.isEmpty()) {
            throw new IllegalStateException("启动 skill 文件 front matter 不能为空: " + skillPath);
        }
        return frontMatter;
    }

    private static <T> T requireNonNull(T value, String field, Path skillPath) {
        if (value == null) {
            throw new IllegalStateException("启动 skill 配置缺失: " + field + ", file=" + skillPath);
        }
        return value;
    }

    private static void requireNonBlank(String value, String field, Path skillPath) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("启动 skill 配置不能为空: " + field + ", file=" + skillPath);
        }
    }

    private static void requirePositive(Integer value, String field, Path skillPath) {
        if (value == null || value <= 0) {
            throw new IllegalStateException("启动 skill 配置必须大于 0: " + field + ", file=" + skillPath);
        }
    }

    private static void requireEquals(String expected, String actual, String expectedField, String actualField) {
        if (!Objects.equals(expected, actual)) {
            throw new IllegalArgumentException("启动参数与 skill 不一致, " + expectedField + "=" + expected
                    + ", " + actualField + "=" + actual);
        }
    }

    public static final class ResolvedServerArgs {
        public final String nacosUrl;
        public final String serverType;
        public final String serverId;
        public final String env;
        public final Integer netPort;
        public final Integer springPort;

        public ResolvedServerArgs(String nacosUrl, String serverType, String serverId, String env, Integer netPort, Integer springPort) {
            this.nacosUrl = nacosUrl;
            this.serverType = serverType;
            this.serverId = serverId;
            this.env = env;
            this.netPort = netPort;
            this.springPort = springPort;
        }
    }

    public static final class ResolvedBotArgs {
        public final String command;
        public final String loginHost;
        public final Integer loginHttpPort;
        public final Integer numBots;

        public ResolvedBotArgs(String command, String loginHost, Integer loginHttpPort, Integer numBots) {
            this.command = command;
            this.loginHost = loginHost;
            this.loginHttpPort = loginHttpPort;
            this.numBots = numBots;
        }
    }

    private record CliServerArgs(String nacosUrl, String env, String serverId) {
    }

    public static class StartupSkill {
        public String name;
        public String description;
        public Startup startup;
    }

    public static class Startup {
        public StartupNacos nacos;
        public StartupServer login;
        public StartupServer game;
        public StartupServer gate;
        public StartupBot bot;
        public StartupValidation validation;
    }

    public static class StartupNacos {
        public String url;
        public String namespace;
    }

    public static class StartupServer {
        public String serverType;
        public String serverId;
        public String env;
        public Integer netPort;
        public Integer springPort;
    }

    public static class StartupBot {
        public String command;
        public String loginHost;
        public Integer loginHttpPort;
        public Integer numBots;
    }

    public static class StartupValidation {
        public Integer loginSpringPortOffset;
        public Boolean requireBotHttpPortEqualsLoginSpringPort;
        public List<String> startupOrder;
    }
}
