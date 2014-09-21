#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import ${package}.entity.Person;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Check /WEB-INF/cron.xml and CronController for execution times.
 *
 * Created by marco on 12.09.14.
 */
public class CronService {

    public static Log logger = LogFactory.getLog(CronService.class);

    private OfyService ofyService;

    public void dailyreport() {
        logger.info("Daily Report scheduled");
    }

    public OfyService getOfyService() {
        return ofyService;
    }

    public void setOfyService(OfyService ofyService) {
        this.ofyService = ofyService;
    }
}
