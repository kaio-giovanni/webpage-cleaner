package com.project.study.websearch.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Log {

    private final Logger logger;

    public Log(Class<? extends Object> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void error(String error) {
        logger.error(error);
    }
}
