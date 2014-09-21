#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import java.util.List;

import ${package}.service.PersonService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ${package}.entity.Person;
import ${package}.service.OfyService;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

    private PersonService personService;

	public static Log logger = LogFactory.getLog(PersonController.class);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Person get(@PathVariable Long id) {
		return personService.get(id);
	}

    @RequestMapping(value = "/fetchAll", method = RequestMethod.GET)
	public List<Person> fetchAll() {
		return personService.fetchAll();
	}

    @RequestMapping(value = "", method = RequestMethod.POST)
	public Person save(@RequestBody Person person) {
		return personService.save(person);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		personService.delete(id);
	}

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
}