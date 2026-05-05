package QAcart.Shared.Builds;

import QAcart.Testing_APIs.Apis.TasksApi;
import com.github.javafaker.Faker;
import QAcart.Shared.Pojos.TaskPojo;

public class TaskBuild {
    public static TaskPojo CreateNew(){
        Faker f = new Faker();
        String item = f.book().title();

        return new TaskPojo(item, false);
    }
    public static String GetID(TaskPojo taskPojo, String token){
        return TasksApi.AddTask(taskPojo, token).body().path("_id");
    }
}
