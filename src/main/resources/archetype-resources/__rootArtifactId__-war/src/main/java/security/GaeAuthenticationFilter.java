#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
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

/**
 * originally taken from https://github.com/spring-projects/spring-security/tree/master/samples/gae-xml
 * checkout the blog post http://spring.io/blog/2010/08/02/spring-security-in-google-app-engine/ for details
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
        if (authentication != null && googleUser==null) {
            SecurityContextHolder.clearContext();
            authentication = null;
            ((HttpServletRequest)request).getSession().invalidate();
        }

        if (authentication == null) {
            if (googleUser != null) {
                PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(googleUser, null);
                token.setDetails(ads.buildDetails((HttpServletRequest) request));
                try {
                    authentication = authenticationManager.authenticate(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (AuthenticationException e) {
                    logger.info("error on authentication", e);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        Assert.notNull(authenticationManager, "AuthenticationManager must be set");
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}
