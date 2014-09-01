/*
 * Copyright 2014 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.frontend.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * {@link Filter} implementation for filtering events based on a named marker
 */
public class LogbackMarkerFilter extends Filter<ILoggingEvent> {

    private String markerName;

    private Marker marker;

    /**
     * Filter the logging event according to the provided marker
     * @param event The logging event
     * @return FilterReply.ACCEPT if the event's marker is the same as the provided marker; FilterReply.DENY otherwise
     */
    @Override
    public FilterReply decide(final ILoggingEvent event) {
        final Marker marker = event.getMarker();

        if (marker == this.marker) {
            return FilterReply.NEUTRAL;
        } else {
            return FilterReply.DENY;
        }
    }

    /**
     * Start the filter. {@link #setMarkerName} should be called before calling this method
     */
    @Override
    public void start() {
        if (markerName != null) {
            marker = MarkerFactory.getMarker(markerName);
            super.start();
        }
    }

    /**
     * @param markerName The name of the marker to look for
     */
    public void setMarkerName(final String markerName) {
        this.markerName = markerName;
    }
}
