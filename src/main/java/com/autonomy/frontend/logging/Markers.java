package com.autonomy.frontend.logging;

/*
 * $Id: $
 *
 * Copyright (c) 2014, Autonomy Systems Ltd.
 *
 * Last modified by $Author: $ on $Date: $
 */

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Markers {

    private Markers() {}

    public static Marker AUDIT = MarkerFactory.getMarker("AUDIT");

}
