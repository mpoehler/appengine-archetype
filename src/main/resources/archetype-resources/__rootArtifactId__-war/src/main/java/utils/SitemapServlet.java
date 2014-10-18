#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.utils;

import ${package}.entity.PageListEntry;
import ${package}.service.PageListService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marco on 13.10.14.
 */
public class SitemapServlet extends HttpServlet {

    public static Log logger = LogFactory.getLog(SitemapServlet.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private PageListService pageListService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (pageListService==null) {
            WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
            pageListService = (PageListService) context.getBean("pageListService");
        }

        Writer writer = resp.getWriter();

        // header
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">\n");

        // pages
        logger.error("pageListService: " + pageListService);
        for (PageListEntry pageListEntry : pageListService.getPages()) {
            logger.error("run for entry " + pageListEntry);
            addUrl(req, resp, pageListEntry.getUrl() + (!pageListEntry.getState().equals("") ? "#!" +  pageListEntry.getState() : ""), pageListEntry.getIntervall().toString(), pageListEntry.getPriority(), pageListEntry.getLastChanged());
        }

        // footer
        writer.write("</urlset>");
    }

    private void addUrl(HttpServletRequest req, HttpServletResponse resp, String url, String intervall, double priority, Date lastchanged) throws IOException {
        resp.getWriter().write("<url><loc>" + req.getRequestURL().toString().substring(0, req.getRequestURL().toString().indexOf('/', "https://".length()) ) + url + "</loc><lastmod>" + (lastchanged == null ? sdf.format(new Date()) : sdf.format(lastchanged)) + "</lastmod><changefreq>" + intervall + "</changefreq><priority>" + priority + "</priority></url>\n");
    }


}
