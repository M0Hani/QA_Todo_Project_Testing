package QAcart.Testing_APIs.Builds;

import com.github.javafaker.Faker;
import QAcart.Testing_APIs.Apis.TasksApi;
import QAcart.Testing_APIs.Pojos.TaskPojo;

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
