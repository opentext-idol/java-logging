/*
 * (c) Copyright 2015 Micro Focus or one of its affiliates.
 *
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License.
 *
 * The only warranties for products and services of Micro Focus and its affiliates
 * and licensors ("Micro Focus") are as may be set forth in the express warranty
 * statements accompanying such products and services. Nothing herein should be
 * construed as constituting an additional warranty. Micro Focus shall not be
 * liable for technical or editorial errors or omissions contained herein. The
 * information contained herein is subject to change without notice.
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
