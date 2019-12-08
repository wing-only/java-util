package com.wing.java.util.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

//import com.sun.xml.internal.ws.util.StringUtils;

public class JaxbUtil {

	/**
	 * Java Object->Xml.
	 */
	public static String toXml(Class types, Object obj, String... encoding) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(types);
			StringWriter writer = new StringWriter();
			int length = encoding.length;
			if(length > 0){
				createMarshaller(jaxbContext, encoding[0]).marshal(obj, writer);
			}else{
				createMarshaller(jaxbContext, "utf-8").marshal(obj, writer);
			}
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Xml->Java Object, 支持大小写敏感或不敏感.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXml(Class types, String xml, boolean... caseSensitive) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(types);
			String fromXml = xml;
			int length = caseSensitive.length;
			if(length > 0){
				boolean b = caseSensitive[0];
				if (!b) {
					fromXml = xml.toLowerCase();
				}
			}
			StringReader reader = new StringReader(fromXml);
			return (T) createUnmarshaller(jaxbContext).unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建Marshaller, 设定encoding(可为Null).
	 */
	private static Marshaller createMarshaller(JAXBContext jaxbContext, String encoding) {
		try {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			if (encoding != null) {
				marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			}

			return marshaller;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建UnMarshaller.
	 */
	private static Unmarshaller createUnmarshaller(JAXBContext jaxbContext) {
		try {
			return jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 封装Root Element 是 Collection的情况.
	 */
	public static class CollectionWrapper {
		@XmlAnyElement
		protected Collection collection;
	}

}