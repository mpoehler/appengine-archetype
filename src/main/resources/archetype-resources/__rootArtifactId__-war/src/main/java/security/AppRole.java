#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * originally taken from https://github.com/spring-projects/spring-security/tree/master/samples/gae-xml
 * checkout the blog post http://spring.io/blog/2010/08/02/spring-security-in-google-app-engine/ for details
 */
public enum AppRole implements GrantedAuthority {
    ROLE_ADMIN (0),
    ROLE_USER (1),
    ROLE_PREMIUMUSER (2);

    private final int bit;

    AppRole(int bit) {
        this.bit = bit;
    }

    public int getBit() {
        return bit;
    }

    public String getAuthority() {
        return toString();
    }
}
