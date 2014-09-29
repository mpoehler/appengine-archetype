#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import com.google.api.ads.common.lib.auth.GoogleClientSecretsBuilder;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import ${package}.utils.DevserverAwarePropertyPlaceholderConfigurer;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.MapConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marco on 15.09.14.
 */
public class GaeAuthenticationFilter extends GenericFilterBean {

    public static Log logger = LogFactory.getLog(GaeAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> ads = new WebAuthenticationDetailsSource();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User googleUser = UserServiceFactory.getUserService().getCurrentUser();

        // logout user in spring if the login in google is lost
        if (authentication != null && googleUser == null) {
            SecurityContextHolder.clearContext();
            authentication = null;
            ((HttpServletRequest) request).getSession().invalidate();
        }

        if (authentication == null) {
            // user is not authenticated in spring
            if (googleUser != null) {
                // user is logged in at google, create spring authentication
                PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(googleUser, null);
                token.setDetails(ads.buildDetails((HttpServletRequest) request));
                try {
                    authentication = authenticationManager.authenticate(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (AuthenticationException e) {
                    logger.info("error on authentication", e);
                    return;
                }

			
		if (DevserverAwarePropertyPlaceholderConfigurer.getProperty("oauth2")!=null && DevserverAwarePropertyPlaceholderConfigurer.getProperty("oauth2").equalsIgnoreCase("true")) {
	                logger.info("now create AuthorizationCodeFlow");
        	        try {
        	            GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = newFlow();

        	            logger.info("try to load Credentials from the datastore");
        	            Credential credential = googleAuthorizationCodeFlow.loadCredential(googleUser.getUserId());
        	            if (credential == null) {
        	                logger.info("no credentials found, redirect to permissions page");
        	                GenericUrl url = new GenericUrl(((HttpServletRequest) request).getRequestURL().toString());
        	                url.setRawPath("/oauth2callback");
        	                ((HttpServletResponse) response).sendRedirect(googleAuthorizationCodeFlow.newAuthorizationUrl().setRedirectUri(url.build()).setState(((HttpServletRequest) request).getRequestURI()).build());
        	                return;
        	            } else {
        	                logger.info("credentials found in datastore, can be now used to initialize services.");
        	            }
        	        } catch (Exception e) {
        	            logger.error("Exception on try to authorize for adwords", e);
        	        }
		}
            }
        }
        chain.doFilter(request, response);
    }

    public static GoogleAuthorizationCodeFlow newFlow() throws IOException {
        Configuration config = new MapConfiguration(new HashMap());
        config.addProperty("api.adwords.clientId", DevserverAwarePropertyPlaceholderConfigurer.getProperty("clientId"));
        config.addProperty("api.adwords.clientSecret", DevserverAwarePropertyPlaceholderConfigurer.getProperty("clientSecret"));
        config.addProperty("api.adwords.developerToken", DevserverAwarePropertyPlaceholderConfigurer.getProperty("developerToken"));

        GoogleClientSecrets cs = null;
        try {
            cs = new GoogleClientSecretsBuilder().forApi(GoogleClientSecretsBuilder.Api.ADWORDS).from(config).build();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        // Add more services here if you wish
        List<String> list = new ArrayList<String>();
        if (DevserverAwarePropertyPlaceholderConfigurer.getProperty("adwords-api") != null &&
                DevserverAwarePropertyPlaceholderConfigurer.getProperty("adwords-api").equalsIgnoreCase("true")) {
            list.add("https://adwords.google.com/api/adwords");
        }

        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                cs,
                list)
                .setDataStoreFactory(AppEngineDataStoreFactory.getDefaultInstance())
                .setAccessType("offline")
                .build();
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        Assert.notNull(authenticationManager, "AuthenticationManager must be set");
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}
