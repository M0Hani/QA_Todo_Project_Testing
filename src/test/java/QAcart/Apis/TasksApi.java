package QAcart.Apis;

import QAcart.Base.Specs;
import QAcart.Data.Routes;
import io.restassured.response.Response;
import QAcart.Pojos.TaskPojo;

import static io.restassured.RestAssured.given;

public class TasksApi {
    public static Response AddTask(TaskPojo taskPojo, String token){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .body(taskPojo)
                .when()
                .post(Routes.TasksPath)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response EditTask(TaskPojo taskPojo, String token, String taskID){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .body(taskPojo)
                .when()
                .put(Routes.TasksPath + "/" + taskID)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response GetAllTasks(String token){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Routes.TasksPath)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response GetTask(String token, String taskID){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Routes.TasksPath + "/" + taskID)
                .then()
                .log().all()
                .extract().response();
    }
}
