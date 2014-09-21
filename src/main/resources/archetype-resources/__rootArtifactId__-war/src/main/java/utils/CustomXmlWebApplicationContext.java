#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.google.appengine.api.utils.SystemProperty;

public class CustomXmlWebApplicationContext extends XmlWebApplicationContext {
	protected void initBeanDefinitionReader(
			XmlBeanDefinitionReader beanDefinitionReader) {
		super.initBeanDefinitionReader(beanDefinitionReader);
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			beanDefinitionReader.setValidating(false);
			beanDefinitionReader.setNamespaceAware(true);
		}
	}
}
