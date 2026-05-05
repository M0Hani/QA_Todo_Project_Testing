package QAcart.Testing_UI.TestSuites;

import QAcart.Shared.Builds.UserBuild;
import QAcart.Shared.Data.ErrorMsgs;
import QAcart.Shared.Data.Fillings;
import QAcart.Shared.Data.HelperText;
import QAcart.Shared.Pojos.UserPojo;
import QAcart.Testing_APIs.Apis.UsersApi;
import QAcart.Testing_UI.Base.BasePage;
import QAcart.Testing_UI.Base.BaseTest;
import QAcart.Testing_UI.Pages.HomePage;
import QAcart.Testing_UI.Pages.LoginPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.*;

import io.restassured.http.Cookie;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import java.util.List;

public class LoginTest extends BaseTest {

    @Test
    @Description("Login using valid credentials")
    public void PositiveLogin(){
        UserPojo userPojo = UserBuild.CreateNew();
        UsersApi.Register(userPojo);
        LoginPage loginPage = new LoginPage(getDriver());
        BasePage result = loginPage.load().login(userPojo.getEmail(), userPojo.getPassword());
        if(result instanceof HomePage homePage){
            assertTrue(homePage.isWelMesDisplayed());
            assertTrue(homePage.getWelcomeMessage().contains(userPojo.getFirstName().toUpperCase()));
        }
        else throw new RuntimeException("Test failed. Something went wrong with the login process");
    }

    @Test
    @Description("Login using an existing email with a wrong password")
    public void NegativeLoginWrongPassword(){
        UserPojo userPojo = UserBuild.CreateNew();
        UsersApi.Register(userPojo);
        LoginPage loginPage = new LoginPage(getDriver());
        BasePage result = loginPage.load().login(userPojo.getEmail(), "Incorrect");
        if(result instanceof LoginPage) assertThat(loginPage.getErrorMessage(), equalTo(ErrorMsgs.WrongPassword));
        else throw new RuntimeException("Test failed. Something went wrong, user shouldn't be able to login");
    }
    @Test
    @Description("Login using an unregistered email")
    public void NegativeLoginUnregistered(){
        LoginPage loginPage = new LoginPage(getDriver());
        BasePage result = loginPage.load().login("doesnt_exist@db.com", "blahblah");
        if(result instanceof LoginPage) assertThat(loginPage.getErrorMessage(), equalTo(ErrorMsgs.UnRegisteredEmail));
        else throw new RuntimeException("Test failed. Something went wrong, user shouldn't be able to login");
    }
    @Test
    @Description("Try to login with empty fields")
    public void TryLoginWithEmptyFields(){
        LoginPage loginPage = new LoginPage(getDriver());
        BasePage result = loginPage.load().login(Fillings.EmptyText, Fillings.EmptyText);
        if(result instanceof LoginPage) assertThat(loginPage.getHelperText(), equalTo(HelperText.IncorrectEmail));
        else throw new RuntimeException("Test failed. Something went wrong, user shouldn't be able to login");
    }
    @Test
    @Description("Try to Login with entering only whitespaces")
    public void LoginWithWhiteSpaces(){
        LoginPage loginPage = new LoginPage(getDriver());
        BasePage result = loginPage.load().login(Fillings.WhiteSpaces, Fillings.WhiteSpaces);
        if(result instanceof LoginPage) assertThat(loginPage.getHelperText(), equalTo(HelperText.IncorrectEmail));
        else throw new RuntimeException("Test failed. Something went wrong, user shouldn't be able to login");
    }



}
