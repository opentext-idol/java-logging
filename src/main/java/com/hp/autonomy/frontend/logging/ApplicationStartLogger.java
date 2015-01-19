/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.frontend.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Utility class for logging at Application startup. Creating an instance of this class as a Spring bean will cause
 * the provided message to be logged at startup.
 */
@Slf4j
public class ApplicationStartLogger implements ApplicationListener<ContextRefreshedEvent> {

    private boolean hasStarted = false;

    private final String message;

    /**
     * Creates an ApplicationStartLogger with the message "APPLICATION STARTED"
     */
    public ApplicationStartLogger() {
        this("APPLICATION STARTED");
    }

    /**
     * Creates an ApplicationStartLogger with the given message
     */
    public ApplicationStartLogger(final String message) {
        this.message = message;
    }

    /**
     * Logs the provided message at log level info when it first receives an event
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!hasStarted) {
            hasStarted = true;
            log.info(Markers.AUDIT, message);
        }
    }

}
