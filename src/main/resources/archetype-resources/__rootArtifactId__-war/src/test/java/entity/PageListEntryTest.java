#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity;

import ${package}.entity.ChangeIntervall;
import ${package}.entity.PageListEntry;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by marco on 15.10.14.
 */
public class PageListEntryTest {

    @Test
    public void testParse() throws ParseException {

        PageListEntry pageListEntry = PageListEntry.parse("/contact.html,,2014-10-23,weekly,0.7");
        assertEquals("", pageListEntry.getState());
        assertEquals("/contact.html", pageListEntry.getUrl());
        assertEquals(ChangeIntervall.weekly,pageListEntry.getIntervall());
        assertEquals("2014-10-23",new SimpleDateFormat("yyyy-MM-dd").format(pageListEntry.getLastChanged()));
        assertEquals(0.7,pageListEntry.getPriority(), 0.00001);

        pageListEntry = PageListEntry.parse("/,/imprint,2014-07-30,daily,0.5");
        assertEquals("/", pageListEntry.getUrl());
        assertEquals("/imprint", pageListEntry.getState());
        assertEquals(ChangeIntervall.daily,pageListEntry.getIntervall());
        assertEquals("2014-07-30",new SimpleDateFormat("yyyy-MM-dd").format(pageListEntry.getLastChanged()));
        assertEquals(0.5,pageListEntry.getPriority(), 0.00001);

        pageListEntry = PageListEntry.parse("/,/imprint, 2014-07-30, daily ,0.5 ");
        assertEquals("/", pageListEntry.getUrl());
        assertEquals("/imprint", pageListEntry.getState());
        assertEquals(ChangeIntervall.daily,pageListEntry.getIntervall());
        assertEquals("2014-07-30",new SimpleDateFormat("yyyy-MM-dd").format(pageListEntry.getLastChanged()));
        assertEquals(0.5,pageListEntry.getPriority(), 0.00001);
    }
}
