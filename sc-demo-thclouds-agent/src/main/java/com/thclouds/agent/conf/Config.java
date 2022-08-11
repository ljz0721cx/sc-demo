package com.thclouds.agent.conf;


import com.thclouds.agent.logging.core.LogLevel;
import com.thclouds.agent.logging.core.LogOutput;
import com.thclouds.agent.logging.core.ResolverType;

/**
 * @description 统一配置信息
 */
public class Config {

    public static class Agent {

        public static String SERVICE_NAME = "demo";

        public static boolean IS_OPEN_DEBUGGING_CLASS = false;

        /**
         * 忽略的链路追踪的的后缀
         */
        public static String IGNORE_SUFFIX = ".jpg,.jpeg,.js,.css,.png,.bmp,.gif,.ico,.mp3,.mp4,.html,.svg";
    }

    public static class Logging {
        /**
         * 日志文件名称
         */
        public static String FILE_NAME = "thclouds-api.log";

        /**
         * 存放目录，默认{gAgentJarDir}/logs
         */
        public static String DIR = "";

        /**
         * The max size of log file. If the size is bigger than this, archive the current file, and write into a new
         * file.
         */
        public static int MAX_FILE_SIZE = 300 * 1024 * 1024;

        /**
         * The max history log files. When rollover happened, if log files exceed this number, then the oldest file will
         * be delete. Negative or zero means off, by default.
         */
        public static int MAX_HISTORY_FILES = -1;

        /**
         * The log level. Default is debug.
         */
        public static LogLevel LEVEL = LogLevel.DEBUG;

        /**
         * The log output. Default is FILE.
         */
        public static LogOutput OUTPUT = LogOutput.FILE;

        /**
         * The log resolver type. Default is PATTERN which will create PatternLogResolver later.
         */
        public static ResolverType RESOLVER = ResolverType.PATTERN;

        /**
         * The log patten. Default is "%level %timestamp %thread %class : %msg %throwable". Each conversion specifiers
         * starts with a percent sign '%' and fis followed by conversion word. There are some default conversion
         * specifiers: %thread = ThreadName %level = LogLevel  {@link LogLevel} %timestamp = The now() who format is
         * 'yyyy-MM-dd HH:mm:ss:SSS' %class = SimpleName of TargetClass %msg = Message of user input %throwable =
         * Throwable of user input %agent_name = ServiceName of Agent {@link Agent#SERVICE_NAME}
         */
        public static String PATTERN = "%level %timestamp %thread %class : %msg %throwable";

    }

    public static class Rule {

        public static String REMOVE_ADDRESS = "127.0.0.1:8848";

        public static String GROUP_ID = "SENTINEL_GROUP";

        public static String NAMESPACE_ID = "dev";

        public static String FLOW_DATA_ID = "thcloud_company-flow-rules1";

        public static String DEGRADE_DATA_ID = "degrade.rule";

        public static String SYSTEM_DATA_ID = "system.rule";

    }

    public static class Sentinel {

        public static final String HEARTBEAT_CLIENT_IP = "";

        public static String DASHBOARD_SERVER = "127.0.0.1:8888";

        public static String HEARTBEAT_INTERVAL_MS = "1000";

    }

    public static class Plugin {
        /**
         * Control the length of the peer field.
         */
        public static int PEER_MAX_LENGTH = 200;

        /**
         * Exclude activated plugins
         */
        public static String EXCLUDE_PLUGINS = "";

    }
}
