package com.autonomy.frontend.logging;

/*
 * $Id: $
 *
 * Copyright (c) 2014, Autonomy Systems Ltd.
 *
 * Last modified by $Author: $ on $Date: $
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
public class ApplicationStartLogger implements ApplicationListener<ContextRefreshedEvent> {

    private boolean hasStarted = false;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!hasStarted) {
            hasStarted = true;
            log.info(Markers.AUDIT, "APPLICATION STARTED");
        }
    }

}
