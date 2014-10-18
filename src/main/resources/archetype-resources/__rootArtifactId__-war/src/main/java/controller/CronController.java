#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import ${package}.entity.Person;
import ${package}.service.CronService;
import ${package}.service.PersonService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cron")
public class CronController {

    private CronService cronService;

	public static Log logger = LogFactory.getLog(CronController.class);

    @RequestMapping(value = "/dailyreport", method = RequestMethod.GET)
	public void dailyreport() {
		cronService.dailyreport();
	}

    @RequestMapping(value = "/updateHTMLSnapshots", method = RequestMethod.GET)
    public void updateHTMLSnapshots(HttpServletRequest request) {
        cronService.updateHTMLSnapshots(request.getRequestURL());
    }

    public CronService getCronService() {
        return cronService;
    }

    public void setCronService(CronService cronService) {
        this.cronService = cronService;
    }
}