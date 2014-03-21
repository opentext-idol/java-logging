package com.autonomy.frontend.logging;

/*
 * $Id: $
 *
 * Copyright (c) 2014, Autonomy Systems Ltd.
 *
 * Last modified by $Author: $ on $Date: $
 */

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.Setter;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LogbackMarkerFilter extends Filter<ILoggingEvent> {

    @Setter
    private String markerName;

    private Marker marker;

    @Override
    public FilterReply decide(final ILoggingEvent event) {
        final Marker marker = event.getMarker();

        if (marker == this.marker) {
            return FilterReply.NEUTRAL;
        } else {
            return FilterReply.DENY;
        }
    }

    @Override
    public void start() {
        if (markerName != null) {
            marker = MarkerFactory.getMarker(markerName);
            super.start();
        }
    }

}
