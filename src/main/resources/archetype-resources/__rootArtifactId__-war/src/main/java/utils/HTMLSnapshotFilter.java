#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.utils;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.urlfetch.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.google.appengine.api.urlfetch.FetchOptions.Builder.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Created by marco on 30.09.14.
 */
public class HTMLSnapshotFilter implements Filter {

    public static Log logger = LogFactory.getLog(HTMLSnapshotFilter.class);

    private static URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();

    private static MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request.getParameter("_escaped_fragment_")!=null) {

            List<String> collectedAlerts = new ArrayList<String>();

            String state = request.getParameter("_escaped_fragment_");
            logger.info("create page snapshot for " + state);

            String baseURL = ((HttpServletRequest) request).getRequestURL().toString();
            String targeturl = baseURL + (state.equals("") ? "" : "#!" + state);

            // check cache
            byte[] value = (byte[]) syncCache.get("SnapshotPageCache:" + targeturl);
            if (value == null) {
                logger.error("no Cache hit for " + targeturl);
                response.getOutputStream().write(refreshCache(state, baseURL));
            } else {
                // write the value from the cache
                logger.error("Cache hit for " + targeturl);
                response.getOutputStream().write(value);
            }

        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     *
     * @param state the state, that means everything behind #!
     * @param baseURL - the requestURL to get the target server from
     * @return
     * @throws IOException
     */
    public static byte[] refreshCache(String state, String baseURL) throws IOException {
        String hostname = baseURL.substring(0,baseURL.indexOf("/", "https://".length()));
        logger.debug("refreshing cache for " + state);
        String targeturl = baseURL + (state.equals("") ? "" : "#!" + state);
        String urlToGet = DevserverAwarePropertyPlaceholderConfigurer.getProperty("htmlsnapshot.baseurl") + "/htmlsnapshot/snapshot?url=" + URLEncoder.encode(targeturl, "UTF-8");
        logger.debug("refreshing with url for " + urlToGet);
        URL url = new URL(urlToGet);
        HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, withDeadline(60));
        HTTPResponse getResponse = urlFetchService.fetch(req);
        if (getResponse.getResponseCode()==200) {
            // populate cache
            logger.debug("cache put " + targeturl);
            syncCache.put("SnapshotPageCache:" + targeturl, getResponse.getContent());
        } else {
            // in case of errors show error and send email!
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(DevserverAwarePropertyPlaceholderConfigurer.getProperty("email.from"), "Example.com Admin"));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(DevserverAwarePropertyPlaceholderConfigurer.getProperty("email.alert"), "Mr. User"));
                msg.setSubject("ALERT: Can't crawl pages thru the HTMLSnapshot Service");
                msg.setText("Problem on crawling the HTMLSnapshot Service with the url " + urlToGet);
                Transport.send(msg);
            } catch (Exception e) {
                logger.error("Can't send email", e);
            }
        }
        return getResponse.getContent();
    }

    @Override
    public void destroy() {
    }
}