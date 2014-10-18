#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integrationtest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.assertEquals;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is a test that runs on an external firefox. It runs in the intergration-test phase and expects that a
 * local devserver is running.
 */
public class SeleniumTest {

    @Test
    public void testPersonIsShownOnUserPage()
    {
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080");
        assertEquals("homepage", driver.getTitle());

        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("btn-login")).click();
        assertEquals("user", driver.findElement(By.cssSelector("h1")).getText());
        driver.close();
    }
}