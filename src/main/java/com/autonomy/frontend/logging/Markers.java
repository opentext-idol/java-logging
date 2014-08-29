/*
 * Copyright 2014 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.autonomy.frontend.logging;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Utility class holding {@link Marker} instances with common names
 */
public class Markers {

    private Markers() {}

    /**
     * Marker representing audit level events
     */
    public static Marker AUDIT = MarkerFactory.getMarker("AUDIT");

}
