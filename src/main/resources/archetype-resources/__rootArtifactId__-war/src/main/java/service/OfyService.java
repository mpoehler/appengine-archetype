#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import ${package}.entity.Person;

import java.util.Set;

public class OfyService {

    private Set<Class> classes;

    public void init () {
        for (Class clazz : classes) {
            ObjectifyService.register(clazz);
        }
    }

	public Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}

    public Set<Class> getClasses() {
        return classes;
    }

    public void setClasses(Set<Class> classes) {
        this.classes = classes;
    }
}
