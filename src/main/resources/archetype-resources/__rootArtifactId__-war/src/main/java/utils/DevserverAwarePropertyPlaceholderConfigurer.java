#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils;

import com.google.appengine.api.utils.SystemProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by marco on 29.09.14.
 *
 */
public class DevserverAwarePropertyPlaceholderConfigurer
        extends PropertyPlaceholderConfigurer implements InitializingBean {

    public static Log logger = LogFactory.getLog(DevserverAwarePropertyPlaceholderConfigurer.class);

    private int springSystemPropertiesMode = SYSTEM_PROPERTIES_MODE_FALLBACK;

    public static final String PRODUCTION_PREFIX = "prd";
    public static final String DEVELOPMENT_PREFIX = "dev";

    private static String prefix = null;

    private static Map<String, String> propertiesMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            prefix = PRODUCTION_PREFIX;
        } else {
            prefix = DEVELOPMENT_PREFIX;
        }
        logger.info("set property prefix to " + prefix);
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {

        String propVal = null;
        if (systemPropertiesMode == SYSTEM_PROPERTIES_MODE_OVERRIDE) {
            propVal = resolveSystemProperty(prefix + "." + placeholder);
            if (propVal == null) {
                propVal = resolveSystemProperty(placeholder);
            }
        }
        if (propVal == null) {
            propVal = resolvePlaceholder(prefix + "." + placeholder, props);
            if (propVal == null) {
                propVal = resolvePlaceholder(placeholder, props);
            }
        }
        if (propVal == null && systemPropertiesMode == SYSTEM_PROPERTIES_MODE_FALLBACK) {
            propVal = resolveSystemProperty(prefix + "." + placeholder);
            if (propVal == null) {
                propVal = resolveSystemProperty(placeholder);
            }
        }
        return propVal;
    }

    @Override
    public void setSystemPropertiesMode(int systemPropertiesMode) {
        super.setSystemPropertiesMode(systemPropertiesMode);
        springSystemPropertiesMode = systemPropertiesMode;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        super.processProperties(beanFactory, props);
        propertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String valueStr = resolvePlaceholder(keyStr, props, springSystemPropertiesMode);
            logger.info("add to map: " + keyStr + ", " + valueStr);
            propertiesMap.put(keyStr, valueStr);
        }
    }

    public static String getProperty(String name) {
        if (propertiesMap.containsKey(prefix + "." + name)) {
            return propertiesMap.get(prefix + "." + name).toString();
        } else {
            return propertiesMap.get(name).toString();
        }
    }

}
