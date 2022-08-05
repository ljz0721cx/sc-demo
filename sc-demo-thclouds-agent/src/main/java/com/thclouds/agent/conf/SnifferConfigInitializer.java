package com.thclouds.agent.conf;

import com.thclouds.agent.boot.AgentPackageNotFoundException;
import com.thclouds.agent.boot.AgentPackagePath;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.logging.core.JsonLogResolver;
import com.thclouds.agent.logging.core.PatternLogResolver;
import com.thclouds.agent.utils.ConfigInitializer;
import com.thclouds.agent.utils.PropertyPlaceholderHelper;
import com.thclouds.agent.utils.StringUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 初始化所有配置
 */
public class SnifferConfigInitializer {

    private static ILog LOGGER = LogManager.getLogger(SnifferConfigInitializer.class);
    private static final String SPECIFIED_CONFIG_PATH = "thclouds_config";
    private static final String DEFAULT_CONFIG_FILE_NAME = "/conf/agent.conf";
    private static final String ENV_KEY_PREFIX = "thclouds.";
    private static Properties AGENT_SETTINGS;
    private static boolean IS_INIT_COMPLETED = false;



    /**
     * 1、读取配置文件中的配置
     * 2、读取系统配置覆盖配置
     * 3、解析参数中的配置，覆盖配置
     * 4、灌入配置类中
     *
     * @param agentOptions
     */
    public static void initializeCoreConfig(String agentOptions) {
        AGENT_SETTINGS = new Properties();
        try (final InputStreamReader configFileStream = loadConfig()) {
            AGENT_SETTINGS.load(configFileStream);
            for (String key : AGENT_SETTINGS.stringPropertyNames()) {
                String value = (String) AGENT_SETTINGS.get(key);
                AGENT_SETTINGS.put(key, PropertyPlaceholderHelper.INSTANCE.replacePlaceholders(value, AGENT_SETTINGS));
            }

        } catch (Exception e) {
            LOGGER.error(e, "Failed to read the config file, thclouds is going to run in default config.");
        }

        try {
            overrideConfigBySystemProp();
        } catch (Exception e) {
            LOGGER.error(e, "Failed to read the system properties.");
        }

        agentOptions = StringUtil.trim(agentOptions, ',');
        if (!StringUtil.isEmpty(agentOptions)) {
            try {
                agentOptions = agentOptions.trim();
                LOGGER.info("Agent options is {}.", agentOptions);

                overrideConfigByAgentOptions(agentOptions);
            } catch (Exception e) {
                LOGGER.error(e, "Failed to parse the agent options, val is {}.", agentOptions);
            }
        }

        initializeConfig(Config.class);
        // reconfigure logger after config initialization
        configureLogger();
        LOGGER = LogManager.getLogger(SnifferConfigInitializer.class);

        if (StringUtil.isEmpty(Config.Agent.SERVICE_NAME)) {
            throw new ExceptionInInitializerError("`agent.service_name` is missing.");
        }

        IS_INIT_COMPLETED = true;
    }

    /**
     * Initialize field values of any given config class.
     *
     * @param configClass to host the settings for code access.
     */
    public static void initializeConfig(Class configClass) {
        if (AGENT_SETTINGS == null) {
            LOGGER.error("Plugin configs have to be initialized after core config initialization.");
            return;
        }
        try {
            ConfigInitializer.initialize(AGENT_SETTINGS, configClass);
        } catch (IllegalAccessException e) {
            LOGGER.error(e,
                    "Failed to set the agent settings {}"
                            + " to Config={} ",
                    AGENT_SETTINGS, configClass
            );
        }
    }


    private static void overrideConfigByAgentOptions(String agentOptions) throws IllegalArgumentException {
        for (List<String> terms : parseAgentOptions(agentOptions)) {
            if (terms.size() != 2) {
                throw new IllegalArgumentException("[" + terms + "] is not a key-value pair.");
            }
            AGENT_SETTINGS.put(terms.get(0), terms.get(1));
        }
    }

    private static List<List<String>> parseAgentOptions(String agentOptions) {
        List<List<String>> options = new ArrayList<>();
        List<String> terms = new ArrayList<>();
        boolean isInQuotes = false;
        StringBuilder currentTerm = new StringBuilder();
        for (char c : agentOptions.toCharArray()) {
            if (c == '\'' || c == '"') {
                isInQuotes = !isInQuotes;
            } else if (c == '=' && !isInQuotes) {   // key-value pair uses '=' as separator
                terms.add(currentTerm.toString());
                currentTerm = new StringBuilder();
            } else if (c == ',' && !isInQuotes) {   // multiple options use ',' as separator
                terms.add(currentTerm.toString());
                currentTerm = new StringBuilder();

                options.add(terms);
                terms = new ArrayList<>();
            } else {
                currentTerm.append(c);
            }
        }
        // add the last term and option without separator
        terms.add(currentTerm.toString());
        options.add(terms);
        return options;
    }

    public static boolean isInitCompleted() {
        return IS_INIT_COMPLETED;
    }

    /**
     * Override the config by system properties. The property key must start with `thclouds`, the result should be as
     * same as in `agent.conf`
     * <p>
     * such as: Property key of `agent.service_name` should be `thclouds.agent.service_name`
     */
    private static void overrideConfigBySystemProp(){
        Properties systemProperties = System.getProperties();
        for (final Map.Entry<Object, Object> prop : systemProperties.entrySet()) {
            String key = prop.getKey().toString();
            if (key.startsWith(ENV_KEY_PREFIX)) {
                String realKey = key.substring(ENV_KEY_PREFIX.length());
                AGENT_SETTINGS.put(realKey, prop.getValue());
            }
        }
    }

    /**
     * 加载指定的配置文件或默认配置文件
     *
     */
    private static InputStreamReader loadConfig() throws AgentPackageNotFoundException, ConfigNotFoundException {
        String specifiedConfigPath = System.getProperty(SPECIFIED_CONFIG_PATH);
        File configFile = StringUtil.isEmpty(specifiedConfigPath) ? new File(
                AgentPackagePath.getPath(), DEFAULT_CONFIG_FILE_NAME) : new File(specifiedConfigPath);

        if (configFile.exists() && configFile.isFile()) {
            try {
                LOGGER.info("Config file found in {}.", configFile);

                return new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8);
            } catch (FileNotFoundException e) {
                throw new ConfigNotFoundException("Failed to load agent.config", e);
            }
        }
        throw new ConfigNotFoundException("Failed to load agent.config.");
    }

    static void configureLogger() {
        switch (Config.Logging.RESOLVER) {
            case JSON:
                LogManager.setLogResolver(new JsonLogResolver());
                break;
            case PATTERN:
            default:
                LogManager.setLogResolver(new PatternLogResolver());
        }
    }
}
