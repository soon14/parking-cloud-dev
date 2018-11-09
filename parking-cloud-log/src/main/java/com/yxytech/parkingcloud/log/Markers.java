package com.yxytech.parkingcloud.log;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Markers {

    public static final Marker DB =  new MarkerManager.Log4jMarker("dblog");  //dblog就是上面MarkerFilter里的标记


}