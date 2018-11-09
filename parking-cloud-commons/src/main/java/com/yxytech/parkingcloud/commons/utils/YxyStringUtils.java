package com.yxytech.parkingcloud.commons.utils;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class YxyStringUtils {

    private final static VelocityEngine ve = new VelocityEngine();
    static {
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute" );
    }

    public static String velocityRender(String template, Map<String, Object>data) throws IOException {
        StringWriter write = new StringWriter();
        VelocityContext context = new VelocityContext();

        data.forEach((key, val) ->  context.put(key, val) );
        ve.evaluate(context, write, "", template);

        return write.toString();
    }
}
