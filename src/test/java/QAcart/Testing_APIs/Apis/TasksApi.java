package QAcart.Testing_APIs.Apis;

import QAcart.Testing_APIs.Base.Specs;
import QAcart.Testing_APIs.Data.Routes;
import io.restassured.response.Response;
import QAcart.Testing_APIs.Pojos.TaskPojo;

import static io.restassured.RestAssured.given;

public class TasksApi {
    public static Response AddTask(TaskPojo taskPojo, String token){
        if(token == null)
        {
            return given()
                    .spec(Specs.getRequestSpecNoAuth())
                    .body(taskPojo)
                    .when()
                    .post(Routes.TasksPath)
                    .then()
                    .log().all()
                    .extract().response();
        }
        return given()
                .spec(Specs.getRequestSpecAuth(token))
                .body(taskPojo)
                .when()
                .post(Routes.TasksPath)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response GetTasks(String token){
        if (token == null)
        {
            return given()
                    .spec(Specs.getRequestSpecNoAuth())
                    .when()
                    .get(Routes.TasksPath)
                    .then()
                    .log().all()
                    .extract().response();
        }
        return given()
                .spec(Specs.getRequestSpecAuth(token))
                .when()
                .get(Routes.TasksPath)
                .then()
                .log().all()
                .extract().response();
    }
}
