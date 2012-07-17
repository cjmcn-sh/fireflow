/**
 * Copyright 2007-2010 非也
 * All rights reserved. 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation。
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses. *
 */
package org.fireflow.model.io.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.fireflow.model.io.ModelElementNames;
import org.fireflow.model.io.SerializerException;
import org.fireflow.model.io.Util4Serializer;
import org.fireflow.model.resourcedef.ResourceDef;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * 
 * @author 非也
 * @version 2.0
 */
public class ResourceSerializer implements ModelElementNames {
	private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	static {
		documentBuilderFactory.setNamespaceAware(true);
	}
	
	public static void writeResources(List<ResourceDef> resources,Element parentElement)throws SerializerException{
		Document document = parentElement.getOwnerDocument();
		Element resourcesElement = document.createElementNS(RESOURCE_NS_URI, RESOURCE_NS_PREFIX+":"+RESOURCES);
		parentElement.appendChild(resourcesElement);
		
		if (resources == null || resources.size() == 0) {
			return;
		}

		for (ResourceDef r :resources){
			writeResource(r, resourcesElement);
		}
		
	}
	
	public static Document serializeToDOM(List<ResourceDef> resources)throws  SerializerException{
		
		try{
			DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
		
			Document document = docBuilder.newDocument();
			
			Element resourcesElement = document.createElementNS(RESOURCE_NS_URI, RESOURCE_NS_PREFIX+":"+RESOURCES);
			document.appendChild(resourcesElement);
			

//			resourcesElement.addNamespace(RESOURCE_NS_PREFIX, RESOURCE_NS_URI);
//			resourcesElement.addNamespace(XSD_NS_PREFIX, XSD_URI);
//			resourcesElement.addNamespace(XSI_NS_PREFIX, XSI_URI);

//			QName qname = df.createQName("schemaLocation", "xsi", XSI_URI);
//			resourcesElement.addAttribute(qname, RESOURCE_SCHEMA_LOCATION);

			if (resources == null || resources.size() == 0) {
				return document;
			}

			for (ResourceDef r :resources){
				writeResource(r, resourcesElement);
			}
			
			return document;
		} catch (ParserConfigurationException e) {
			throw new SerializerException(e);

		}finally{
			
		}

	}
	
	public static String serializeToXmlString(List<ResourceDef> resources)throws  SerializerException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
			serialize(resources, out);
		} catch (IOException e) {
			throw new SerializerException(e);
		}
        return out.toString();
	}
	
	public static  void serialize(List<ResourceDef> resources, OutputStream out)
			throws IOException, SerializerException {
		try{
			Document document = serializeToDOM(resources);
			
			TransformerFactory  transformerFactory  =  TransformerFactory.newInstance();  
			Transformer transformer = transformerFactory.newTransformer();  
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");  
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");  
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");  

			transformer.transform(new DOMSource(document), new StreamResult(out)); 
			out.flush();
		} catch (TransformerConfigurationException e) {
			throw new SerializerException(e);
		} catch (TransformerException e) {
			throw new SerializerException(e);
		}finally{
			
		}

	}

	protected static void writeResource(ResourceDef r, Element resourcesElement)
			throws SerializerException {


			Element resourceElem = Util4Serializer.addElement(resourcesElement,
					RESOURCE);
			resourceElem.setAttribute(ID, r.getId());
			resourceElem.setAttribute(NAME, r.getName());
			if (r.getDisplayName() != null && !r.getDisplayName().trim().equals("")) {
				resourceElem.setAttribute(DISPLAY_NAME, r.getDisplayName());
			}

			resourceElem
					.setAttribute(RESOURCE_TYPE, r.getResourceType().getValue());
			resourceElem.setAttribute(VALUE, r.getValue());
			if (!StringUtils.isEmpty(r.getResolverBeanName()) 
					|| !StringUtils.isEmpty(r.getResolverClassName())){
				Element resolverElem = Util4Serializer.addElement(resourceElem,
						RESOLVER);
				if (!StringUtils.isEmpty(r.getResolverBeanName())){
					resolverElem.setAttribute(BEAN_NAME, r.getResolverBeanName());
				}
				if (!StringUtils.isEmpty(r.getResolverClassName())){
					resolverElem.setAttribute(CLASS_NAME, r.getResolverClassName());
				}
			}
			
			writeExtendedAttributes(r.getExtendedAttributes(),resourceElem);

	}

    protected static Element writeExtendedAttributes(Map<String,String> extendedAttributes,
            Element parent) {

        if (extendedAttributes == null || extendedAttributes.size() == 0) {
            return null;
        }

        Element extendedAttributesElement =
                Util4Serializer.addElement(parent,
                EXTENDED_ATTRIBUTES);

        Iterator<String> keys = extendedAttributes.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = extendedAttributes.get(key);

            Element extendedAttributeElement = Util4Serializer.addElement(
                    extendedAttributesElement, EXTENDED_ATTRIBUTE);
            extendedAttributeElement.setAttribute(NAME, key.toString());
            if (value != null) {
                extendedAttributeElement.setAttribute(VALUE, value.toString());
            }

        }

        return extendedAttributesElement;

    }
}
