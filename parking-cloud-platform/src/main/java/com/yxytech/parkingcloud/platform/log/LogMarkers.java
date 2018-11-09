package com.yxytech.parkingcloud.platform.log;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class LogMarkers {

    public static final Marker GrpcMarker = new MarkerManager.Log4jMarker("grpcLog");
}
