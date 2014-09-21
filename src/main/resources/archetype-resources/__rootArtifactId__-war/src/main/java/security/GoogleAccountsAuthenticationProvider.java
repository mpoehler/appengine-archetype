#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * originally taken from https://github.com/spring-projects/spring-security/tree/master/samples/gae-xml
 * checkout the blog post http://spring.io/blog/2010/08/02/spring-security-in-google-app-engine/ for details
 */
public class GoogleAccountsAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

    public static Log logger = LogFactory.getLog(GoogleAccountsAuthenticationProvider.class);

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User googleUser = (User) authentication.getPrincipal();
        GaeUser user = null;
        if (user == null) {
            // User not in registry. Needs to register
            user = new GaeUser(googleUser.getUserId(), googleUser.getNickname(), googleUser.getEmail());
            if (UserServiceFactory.getUserService().isUserAdmin()) {
                user.getAuthorities().add(AppRole.ROLE_ADMIN);
            }
        }

        return new GaeUserAuthentication(user, authentication.getDetails());
    }

    @Override
    public final boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
