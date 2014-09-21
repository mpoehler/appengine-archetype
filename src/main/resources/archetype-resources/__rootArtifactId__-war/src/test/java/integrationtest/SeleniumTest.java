#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integrationtest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is a test that runs on an external firefox. It runs in the intergration-test phase and expects that a
 * local devserver is running.
 */
public class SeleniumTest {

    @Test
    public void testPersonIsShownOnUserPage()
    {
        //WebDriver driver = new HtmlUnitDriver();
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8080");
        Assert.assertEquals("homepage", driver.getTitle());

        driver.findElement(By.linkText("User Section")).click();
        driver.findElement(By.id("btn-login")).click();

        driver.close();
    }
}