#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * originally taken from https://github.com/spring-projects/spring-security/tree/master/samples/gae-xml
 * checkout the blog post http://spring.io/blog/2010/08/02/spring-security-in-google-app-engine/ for details
 */
public class GoogleAccountsAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public static Log logger = LogFactory.getLog(GoogleAccountsAuthenticationEntryPoint.class);

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        UserService userService = UserServiceFactory.getUserService();
        response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
    }
}
