package com.yxytech.parkingcloud.core.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class JaxbXMLUtil {
    public static <T> T xmlToBean(String xml, Class<T> bean) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(bean);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            T t = (T) unmarshaller.unmarshal(new StringReader(xml));

            return t;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String beanToXML(Object object) {
        String result = null;

        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);

            result = writer.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return result;
    }
}
