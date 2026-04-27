package QAcart.TestCases;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import QAcart.Apis.TasksApi;
import QAcart.Pojos.TaskPojo;
import QAcart.Builds.TaskBuild;
import QAcart.Builds.UserBuild;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TasksTest {
    @Test
    public void AddTask(){
        TaskPojo taskPojo = TaskBuild.CreateNew();
        String token = UserBuild.CreateToken();
        Response response = TasksApi.AddTask(taskPojo, token);
        assertThat(response.statusCode(), equalTo(201));
    }
    @Test
    public void shouldntAddTask(){
        TaskPojo taskPojo = new TaskPojo("new task");
        String token = UserBuild.CreateToken();
        Response response = TasksApi.AddTask(taskPojo, token);
        assertThat(response.statusCode(), equalTo(400));
    }

    @Test
    public void GetTasks(){
        String token = UserBuild.CreateToken();
        Response response = TasksApi.GetAllTasks(token);
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void UpdateTask(){
        String token = UserBuild.CreateToken();
        TaskPojo taskPojo = TaskBuild.CreateNew();
        TasksApi.AddTask(taskPojo, token);
        String taskID = TaskBuild.GetID(taskPojo, token);
        TaskPojo taskUpdate = new TaskPojo("update n", true);
        Response response = TasksApi.EditTask(taskUpdate, token, taskID);
        assertThat(response.statusCode(), equalTo(200));
    }


}
