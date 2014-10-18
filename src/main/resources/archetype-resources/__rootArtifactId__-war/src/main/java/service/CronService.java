#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import ${package}.entity.Person;
import ${package}.entity.PageListEntry;
import ${package}.utils.HTMLSnapshotFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.io.IOException;

/**
 * Check /WEB-INF/cron.xml and CronController for execution times.
 *
 * Created by marco on 12.09.14.
 */
public class CronService {

    public static Log logger = LogFactory.getLog(CronService.class);

    private OfyService ofyService;

    private PageListService pageListService;

    public void dailyreport() {
        logger.info("Daily Report scheduled");
    }

    public void updateHTMLSnapshots(StringBuffer requestURL) {
        for (PageListEntry pageListEntry : pageListService.getPages()) {
            try {
                HTMLSnapshotFilter.refreshCache(pageListEntry.getState(), requestURL.substring(0, requestURL.indexOf("/", "https://".length())) + pageListEntry.getUrl());
            } catch (IOException e) {
                logger.error("Problems with refreshing prerendered page in Cache", e);
            }
        }
    }

    public OfyService getOfyService() {
        return ofyService;
    }

    public void setOfyService(OfyService ofyService) {
        this.ofyService = ofyService;
    }

    public PageListService getPageListService() {
        return pageListService;
    }

    public void setPageListService(PageListService pageListService) {
        this.pageListService = pageListService;
    }
}
