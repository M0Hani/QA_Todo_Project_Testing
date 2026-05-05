package QAcart.Shared.Builds;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class CookiesBuild {

    public static List<Cookie> convertToSeleniumCookie(List<io.restassured.http.Cookie> restCookies){
        List<org.openqa.selenium.Cookie> selenCookies = new ArrayList<>();
        for(io.restassured.http.Cookie cookie : restCookies){
            org.openqa.selenium.Cookie selenCookie = new org.openqa.selenium.Cookie(cookie.getName(), cookie.getValue());
            selenCookies.add(selenCookie);
        }
        return selenCookies;
    }
}
