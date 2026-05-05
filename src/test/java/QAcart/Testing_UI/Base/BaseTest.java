package QAcart.Testing_UI.Base;

import QAcart.Shared.Builds.CookiesBuild;
import io.restassured.http.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.List;

public class BaseTest {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return this.driver.get();
    }

    public void setDriver(WebDriver tempDriver) {
        this.driver.set(tempDriver);
    }

    @BeforeMethod
    public void initialize(){
        WebDriver tempDriver = new DriverSetup().setupchrome();
        setDriver(tempDriver);
    }

    @AfterMethod
    public void term(){ getDriver().quit(); }


    public void injectCookies(List<Cookie> restCookies){
        List<org.openqa.selenium.Cookie> selenCookies = CookiesBuild.convertToSeleniumCookie(restCookies);
        for(org.openqa.selenium.Cookie selenCookie : selenCookies){
            getDriver().manage().addCookie(selenCookie);
        }
    }
}
