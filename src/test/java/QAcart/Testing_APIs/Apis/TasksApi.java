package QAcart.Testing_APIs.Apis;

import QAcart.Testing_APIs.Base.Specs;
import QAcart.Shared.Data.Routes;
import io.restassured.response.Response;
import QAcart.Shared.Pojos.TaskPojo;

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

    public static Response DeleteTask(String token, String taskID){
        if(token == null)
        {
            return given()
                    .spec(Specs.getRequestSpecNoAuth())
                    .when()
                    .delete(Routes.TasksPath + "/" + taskID)
                    .then()
                    .log().all()
                    .extract().response();
        }
        return given()
                .spec(Specs.getRequestSpecAuth(token))
                .when()
                .delete(Routes.TasksPath + "/" + taskID)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response UpdateTask(TaskPojo taskPojo, String token, String taskID){
        if(token == null)
        {
            return given()
                    .spec(Specs.getRequestSpecNoAuth())
                    .body(taskPojo)
                    .when()
                    .put(Routes.TasksPath + "/" + taskID)
                    .then()
                    .log().all()
                    .extract().response();
        }
        return given()
                .spec(Specs.getRequestSpecAuth(token))
                .body(taskPojo)
                .when()
                .put(Routes.TasksPath + "/" + taskID)
                .then()
                .log().all()
                .extract().response();
    }
}
