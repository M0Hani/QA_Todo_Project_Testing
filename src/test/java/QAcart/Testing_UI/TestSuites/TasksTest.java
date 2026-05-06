package QAcart.Testing_UI.TestSuites;

import QAcart.Shared.Builds.TaskBuild;
import QAcart.Shared.Builds.UserBuild;
import QAcart.Shared.Data.Fillings;
import QAcart.Shared.Data.HelperText;
import QAcart.Shared.Pojos.TaskPojo;
import QAcart.Shared.Pojos.UserPojo;
import QAcart.Testing_APIs.Apis.TasksApi;
import QAcart.Testing_APIs.Apis.UsersApi;
import QAcart.Testing_UI.Base.BasePage;
import QAcart.Testing_UI.Base.BaseTest;
import QAcart.Testing_UI.Pages.HomePage;
import QAcart.Testing_UI.Pages.NewTaskPage;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class TasksTest extends BaseTest {

    @Test
    @Description("Adding a new task")
    public void AddTaskSuccessfully(){
        UserPojo userPojo = UserBuild.CreateNew();
        List<Cookie> cookies = UsersApi.Register(userPojo).getDetailedCookies().asList();
        HomePage homePage = new HomePage(getDriver());
        homePage.load();
        injectCookies(cookies);
        NewTaskPage newTaskPage = homePage.load().AddItem();
        TaskPojo taskPojo = TaskBuild.CreateNew();
        BasePage result = newTaskPage.fillItem(taskPojo.getItem());
        if(result instanceof HomePage){
            assertThat(homePage.item_text(), equalTo(taskPojo.getItem()));
            homePage.logOut();
        }
        else throw new RuntimeException("Test failed. Task wasn't added successfully");
    }

    @Test
    @Description("Trying to add task with empty name")
    public void AddEmptyTask(){
        UserPojo userPojo = UserBuild.CreateNew();
        List<Cookie> cookies = UsersApi.Register(userPojo).getDetailedCookies().asList();
        HomePage homePage = new HomePage(getDriver());
        homePage.load();
        injectCookies(cookies);
        NewTaskPage newTaskPage = homePage.load().AddItem();
        BasePage result = newTaskPage.load().fillItem(Fillings.EmptyText);
        if(result instanceof NewTaskPage) {
            assertThat(newTaskPage.getHelperText(), equalTo(HelperText.TaskNameRequired));
            newTaskPage.logOut();
        }
        else throw new RuntimeException("Test failed. Task should be rejected");
    }

    @Test
    @Description("Trying to add task with whitespaces only name")
    public void AddWhiteSpacesTask(){
        UserPojo userPojo = UserBuild.CreateNew();
        List<Cookie> cookies = UsersApi.Register(userPojo).getDetailedCookies().asList();
        HomePage homePage = new HomePage(getDriver());
        homePage.load();
        injectCookies(cookies);
        NewTaskPage newTaskPage = homePage.load().AddItem();
        BasePage result = newTaskPage.load().fillItem(Fillings.WhiteSpaces);
        if(result instanceof NewTaskPage) assertThat(newTaskPage.getHelperText(), equalTo(HelperText.TaskNameRequired));
        else {
            homePage.logOut();
            throw new RuntimeException("Test failed. Task should be rejected and treated as empty");
        }
    }

    @Test
    @Description("When a user has more than 5 tasks, a paging icon should appear")
    public void TasksPaging(){
        UserPojo userPojo = UserBuild.CreateNew();
        Response response = UsersApi.Register(userPojo);
        String token = response.body().path("access_token");
        List<Cookie> cookies = response.getDetailedCookies().asList();
        for(int i=1; i<8; i++){
            TaskPojo taskPojo = TaskBuild.CreateNew();
            TasksApi.AddTask(taskPojo, token);
        }
        HomePage homePage = new HomePage(getDriver());
        homePage.load();
        injectCookies(cookies);
        homePage.load();
        assertTrue(homePage.pagingIsDisplayed());
        homePage.logOut();
    }

    @Test
    @Description("Delete a task from the list")
    public void DeleteTask(){
        UserPojo userPojo = UserBuild.CreateNew();
        Response response = UsersApi.Register(userPojo);
        String token = response.body().path("access_token");
        List<Cookie> cookies = response.getDetailedCookies().asList();
        TaskPojo taskPojo = TaskBuild.CreateNew();
        TasksApi.AddTask(taskPojo, token);
        HomePage homePage = new HomePage(getDriver());
        homePage.load();
        injectCookies(cookies);
        homePage.load().DeleteItem();
        assertTrue(homePage.EmptyTodos());
        homePage.logOut();
    }

    @Test
    @Description("Mark a task as completed")
    public void CheckTask(){
        UserPojo userPojo = UserBuild.CreateNew();
        Response response = UsersApi.Register(userPojo);
        String token = response.body().path("access_token");
        List<Cookie> cookies = response.getDetailedCookies().asList();
        TaskPojo taskPojo = TaskBuild.CreateNew();
        TasksApi.AddTask(taskPojo, token);
        HomePage homePage = new HomePage(getDriver());
        homePage.load();
        injectCookies(cookies);
        homePage.load().CheckTask();
        homePage.load(); //Task is checked but have to reload the page to be read in the client side
        assertTrue(homePage.isTaskChecked());
        homePage.logOut();
    }
}
