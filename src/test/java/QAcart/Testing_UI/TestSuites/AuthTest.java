package QAcart.Testing_UI.TestSuites;

import QAcart.Shared.Builds.TaskBuild;
import QAcart.Shared.Builds.UserBuild;
import QAcart.Shared.Data.Routes;
import QAcart.Shared.Pojos.TaskPojo;
import QAcart.Shared.Pojos.UserPojo;
import QAcart.Testing_APIs.Apis.TasksApi;
import QAcart.Testing_APIs.Apis.UsersApi;
import QAcart.Testing_UI.Base.BasePage;
import QAcart.Testing_UI.Base.BaseTest;
import QAcart.Testing_UI.Pages.HomePage;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class AuthTest extends BaseTest {

    @Test
    @Description("Added tasks still exist in different sessions")
    public void TasksPersistAcrossSessions(){
        UserPojo userPojo = UserBuild.CreateNew();
        Response response = UsersApi.Register(userPojo);
        String token = response.body().path("access_token");
        List<Cookie> cookies = response.getDetailedCookies().asList();
        TaskPojo taskPojo = TaskBuild.CreateNew();
        TasksApi.AddTask(taskPojo, token);
        HomePage homePage = new HomePage(getDriver());
        homePage.load();
        injectCookies(cookies);
        BasePage result = homePage.load()
                .logOut()
                .login(userPojo.getEmail(), userPojo.getPassword());
        if(result instanceof HomePage) assertThat(homePage.item_text(), equalTo(taskPojo.getItem()));
        else throw new RuntimeException("Test failed. Something went wrong with the login process");
    }

    @Test
    @Description("Different users can't access each others tasks")
    public void NoTasksConflict(){
        //First user
        UserPojo userPojo = UserBuild.CreateNew();
        Response response = UsersApi.Register(userPojo);
        String token = response.body().path("access_token");
        List<Cookie> cookies = response.getDetailedCookies().asList();
        TaskPojo taskPojo = TaskBuild.CreateNew();
        TasksApi.AddTask(taskPojo, token);
        HomePage homePage = new HomePage(getDriver());
        homePage.load();
        injectCookies(cookies);
        homePage.load();
        assertThat(homePage.item_text(), equalTo(taskPojo.getItem()));
        homePage.logOut();

        //Second user
        userPojo = UserBuild.CreateNew();
        response = UsersApi.Register(userPojo);
        cookies = response.getDetailedCookies().asList();
        homePage.load();
        injectCookies(cookies);
        homePage.load();
        assertTrue(homePage.EmptyTodos());
    }

    @Test
    @Description("While not being logged-in, users can't access the home page")
    public void UnAuthorizedAccess(){
        HomePage homePage = new HomePage(getDriver());
        homePage.load();
        String url = getDriver().getCurrentUrl();
        assertThat(url, equalTo(Routes.BaseURL + Routes.LoginPage));
    }

    @Test
    @Description("After a log-out, session is destroyed and home page can't be accessed without a log-in")
    public void DestroyedSession(){
        UserPojo userPojo = UserBuild.CreateNew();
        Response response = UsersApi.Register(userPojo);
        List<Cookie> cookies = response.getDetailedCookies().asList();
        HomePage homePage = new HomePage(getDriver());
        homePage.load();
        injectCookies(cookies);
        homePage.load().logOut();
        homePage.load();
        String url = getDriver().getCurrentUrl();
        assertThat(url, equalTo(Routes.BaseURL + Routes.LoginPage));
    }

}
