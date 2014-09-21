#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import ${package}.entity.Person;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is an ordinary unit tests. The only special here is the ServiceHelper needed to initialize the GAE Stub system
 * to simulate datastore access.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    /* This will init the local gae service stubs */
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testSaveAndGet() {

        // create and save a Person
        Person person = new Person();
        person.setName("Hugo");
        long id = personService.save(person).getId();

        // load Person from database
        Person p = personService.get(id);
        Assert.assertEquals("Hugo", p.getName());
    }

}
