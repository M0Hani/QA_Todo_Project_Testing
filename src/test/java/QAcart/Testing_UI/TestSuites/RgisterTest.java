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
import QAcart.Testing_UI.Pages.RegisterPage;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class RgisterTest extends BaseTest {

    @Test
    @Description("User should register successfully with valid data")
    public void PositiveRegister(){
        UserPojo userPojo = UserBuild.CreateNew();
        RegisterPage registerPage = new RegisterPage(getDriver());
        BasePage result = registerPage.load().register(userPojo.getFirstName(), userPojo.getLastName(), userPojo.getEmail(), userPojo.getPassword(), userPojo.getPassword());
        if(result instanceof HomePage homePage) {
            assertTrue(homePage.isWelMesDisplayed());
            assertTrue(homePage.EmptyTodos());
            assertTrue(homePage.getWelcomeMessage().contains(userPojo.getFirstName()));
        }
        else throw new RuntimeException("Test failed. Something went wrong during the register process");
    }
    @Test
    @Description("User shouldn't be able to register when using a registered email")
    public void NegativeRegister(){
        UserPojo userPojo = UserBuild.CreateNew();
        UsersApi.Register(userPojo);
        RegisterPage registerPage = new RegisterPage(getDriver());
        BasePage result = registerPage.load().register(userPojo.getFirstName(), userPojo.getLastName(), userPojo.getEmail(), userPojo.getPassword(), userPojo.getPassword());
        if(result instanceof RegisterPage) { assertThat(registerPage.getErrorMessage(), equalTo(ErrorMsgs.EmailAlreadyExists)); }
        else throw new RuntimeException("Test failed. Something went wrong, user shouldn't be able to register");
    }
    @Test
    @Description("User shouldn't be able to register when using a malformed email")
    public void NegativeRegisterMalForm(){
        UserPojo userPojo = UserBuild.CreateNew();
        RegisterPage registerPage = new RegisterPage(getDriver());
        BasePage result = registerPage.load().register(userPojo.getFirstName(), userPojo.getLastName(), "InvalidEmailForm", userPojo.getPassword(), userPojo.getPassword());
        if(result instanceof RegisterPage) { assertThat(registerPage.getEmailHelperText(), equalTo(HelperText.IncorrectEmail)); }
        else throw new RuntimeException("Test failed. Something went wrong, user shouldn't be able to register");
    }
    @Test
    @Description("User shouldn't be able to register when fields are empty")
    public void NegativeRegisterEmptyFields(){
        UserPojo userPojo = UserBuild.CreateNew();
        UsersApi.Register(userPojo);
        RegisterPage registerPage = new RegisterPage(getDriver());
        BasePage result = registerPage.load().register(Fillings.EmptyText, Fillings.EmptyText, Fillings.EmptyText, Fillings.EmptyText, Fillings.EmptyText);
        if(result instanceof RegisterPage) { assertThat(registerPage.getFNameHelperText(), equalTo(HelperText.FirstNameRequired)); }
        else throw new RuntimeException("Test failed. Something went wrong, user shouldn't be able to register");

    }
}
