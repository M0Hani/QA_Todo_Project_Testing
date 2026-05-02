package QAcart.Testing_APIs.TestSuites;

import QAcart.Testing_APIs.Data.ErrorMsgs;
import QAcart.Testing_APIs.Data.Fillings;
import QAcart.Testing_APIs.Pojos.ErrorPojo;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import QAcart.Testing_APIs.Apis.TasksApi;
import QAcart.Testing_APIs.Pojos.TaskPojo;
import QAcart.Testing_APIs.Builds.TaskBuild;
import QAcart.Testing_APIs.Builds.UserBuild;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TasksTest {
    //Post Requests
    @Test
    @Description("Adds a task successfully")
    public void AddTask(){
        TaskPojo taskPojo = TaskBuild.CreateNew();
        String token = UserBuild.CreateToken();
        Response response = TasksApi.AddTask(taskPojo, token);
        TaskPojo resTask = response.body().as(TaskPojo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(resTask.getItem(), equalTo(taskPojo.getItem()));
    }
    @Test
    @Description("Shouldn't add the task - Missing name")
    public void NegativeAddTaskMissName(){
        TaskPojo taskPojo = TaskBuild.CreateNew();
        String token = UserBuild.CreateToken();
        taskPojo.setItem(Fillings.EmptyText);
        Response response = TasksApi.AddTask(taskPojo, token);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(resMsg.getMessage(), equalTo(ErrorMsgs.ItemNameRequired));
    }
    @Test
    @Description("Shouldn't add the task - Name is whitespaces only")
    public void NegativeAddTaskWhiteSpaces(){
        TaskPojo taskPojo = TaskBuild.CreateNew();
        String token = UserBuild.CreateToken();
        taskPojo.setItem(Fillings.WhiteSpaces);
        Response response = TasksApi.AddTask(taskPojo, token);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(resMsg.getMessage(), equalTo(ErrorMsgs.ItemNameRequired));
    }
    @Test
    @Description("Add a task with XSS payload as a plain text - Response includes the name as entered")
    public void AddTaskWithXSSpayload(){
        TaskPojo taskPojo = TaskBuild.CreateNew();
        String token = UserBuild.CreateToken();
        taskPojo.setItem(Fillings.XSS_payload);
        Response response = TasksApi.AddTask(taskPojo, token);
        TaskPojo resTask = response.body().as(TaskPojo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(resTask.getItem(), equalTo(taskPojo.getItem()));
    }
    @Test
    @Description("Shouldn't add the task - No provided token")
    public void NegativeAddTaskNoToken(){
        TaskPojo taskPojo = TaskBuild.CreateNew();
        String token = null;
        Response response = TasksApi.AddTask(taskPojo, token);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(resMsg.getMessage(), equalTo(ErrorMsgs.UnAuthorized));
    }

    //Get requests
    @Test
    @Description("Returns a list of all the tasks the logged-in user has")
    public void GetTasks(){
        String token = UserBuild.CreateToken();
        Response response = TasksApi.GetTasks(token); //Empty list as the user is only created now
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().path("tasks"), notNullValue());
        assertThat(response.body().path("tasks"), instanceOf(List.class));
        assertThat(response.body().path("tasks"), empty());
    }
    @Test
    @Description("Returns a message that user isn't authorized")
    public void GetTasksNoToken(){
        String token = null;
        Response response = TasksApi.GetTasks(token);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(resMsg.getMessage(), equalTo(ErrorMsgs.UnAuthorized));
    }
    @Test
    @Description("Returns a message that user is either unauthorized or forbidden to access")
    public void GetTasksInValidToken(){
        String token = Fillings.InValidToken;
        Response response = TasksApi.GetTasks(token);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
        assertThat(resMsg.getMessage(), anyOf(equalTo(ErrorMsgs.UnAuthorized), equalTo(ErrorMsgs.ForbiddenAccess)));
    }
}
