#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

/**
 * originally taken from https://github.com/spring-projects/spring-security/tree/master/samples/gae-xml
 * checkout the blog post http://spring.io/blog/2010/08/02/spring-security-in-google-app-engine/ for details
 */
public class GaeUser implements Serializable {
    private final String userId;
    private final String email;
    private final String nickname;
    private final Set<AppRole> authorities;
    private final boolean enabled;

    /**
     * Pre-registration constructor.
     *
     * Assigns the user the "ROLE_USER" role only.
     */
    public GaeUser(String userId, String nickname, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.authorities = EnumSet.of(AppRole.ROLE_USER);
        this.email = email;
        this.enabled = true;
    }

    /**
     * Post-registration constructor
     */
    public GaeUser(String userId, String nickname, String email, Set<AppRole> authorities, boolean enabled) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.authorities = authorities;
        this.enabled= enabled;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Collection<AppRole> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return "GaeUser{" +
                "userId='" + userId + '${symbol_escape}'' +
                ", nickname='" + nickname + '${symbol_escape}'' +
                ", authorities=" + authorities +
                '}';
    }
}
