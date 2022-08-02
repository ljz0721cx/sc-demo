package com.thclouds.agent.conf;


import com.thclouds.agent.logging.core.LogLevel;
import com.thclouds.agent.logging.core.LogOutput;
import com.thclouds.agent.logging.core.ResolverType;

/**
 * @description 统一配置信息
 */
public class Config {

    public static class Agent {

        public static final String SERVICE_NAME = "demo";
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
         *
         * @see PatternLogger#DEFAULT_CONVERTER_MAP
         */
        public static String PATTERN = "%level %timestamp %thread %class : %msg %throwable";

    }

    public static class FeignRule {

        public static String REMOVE_ADDRESS = "nacos.test.qy566.com:80";
        // nacos group
        public static String GROUP_ID = "DEFAULT_GROUP";

        public static String NACOS_NAMESPACE_ID = "dev";

        public static final String FLOW_DATA_ID = "flow.rule";

        public static final String DEGRADE_DATA_ID = "degrade.rule";

        public static final String SYSTEM_DATA_ID = "system.rule";


    }
}