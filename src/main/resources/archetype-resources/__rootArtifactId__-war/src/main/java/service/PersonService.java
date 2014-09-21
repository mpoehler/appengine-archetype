#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import ${package}.entity.Person;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

/**
 * Created by marco on 12.09.14.
 */
public class PersonService {
    public static Log logger = LogFactory.getLog(PersonService.class);

    private OfyService ofyService;

    @Secured("ROLE_USER")
    public Person get(Long id) {
        return ofyService.ofy().load().type(Person.class).id(id).now();
    }

    @Secured("ROLE_USER")
    public List<Person> fetchAll() {
        return ofyService.ofy().load().type(Person.class).list();
    }

    @Secured("ROLE_ADMIN")
    public Person save(Person person) {
        ofyService.ofy().save().entity(person).now();
        return person;
    }

    @Secured("ROLE_ADMIN")
    public void delete(Long id) {
        ofyService.ofy().delete().type(Person.class).id(id).now();
    }

    public OfyService getOfyService() {
        return ofyService;
    }

    public void setOfyService(OfyService ofyService) {
        this.ofyService = ofyService;
    }
}
