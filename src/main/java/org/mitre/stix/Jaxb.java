package org.mitre.stix;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Jaxb {
    // singleton pattern: one instance per class.
    private static Map<String, Jaxb> singletonMap = new HashMap<>();
    private String pkgName;

    // thread-local pattern: one marshaller/unmarshaller instance per thread
    private ThreadLocal<Marshaller> marshallerThreadLocal = new ThreadLocal<>();
    private ThreadLocal<Unmarshaller> unmarshallerThreadLocal = new ThreadLocal<>();

    // The static singleton getter needs to be thread-safe too,
    // so this method is marked as synchronized.
    public static synchronized Jaxb get(String pkgName) {
        Jaxb jaxb = singletonMap.get(pkgName);
        if (jaxb == null) {
            jaxb = new Jaxb(pkgName);
            singletonMap.put(pkgName, jaxb);
        }
        return jaxb;
    }

    // the constructor needs to be private,
    // because all instances need to be created with the get method.
    private Jaxb(String pkgName) {
        this.pkgName = pkgName;
    }

    /**
     * Gets/Creates a marshaller (thread-safe)
     * 
     * @throws JAXBException
     */
    public Marshaller getMarshaller() throws JAXBException {
        Marshaller m = marshallerThreadLocal.get();
        if (m == null) {
            JAXBContext jc = JAXBContext.newInstance(pkgName);
            m = jc.createMarshaller();
            marshallerThreadLocal.set(m);
        }
        return m;
    }

    /**
     * Gets/Creates an unmarshaller (thread-safe)
     * 
     * @throws JAXBException
     */
    public Unmarshaller getUnmarshaller() throws JAXBException {
        Unmarshaller um = unmarshallerThreadLocal.get();
        if (um == null) {
            JAXBContext jc = JAXBContext.newInstance(pkgName);
            um = jc.createUnmarshaller();
            unmarshallerThreadLocal.set(um);
        }
        return um;
    }
}
