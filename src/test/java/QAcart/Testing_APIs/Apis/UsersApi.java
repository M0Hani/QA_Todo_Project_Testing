package QAcart.Testing_APIs.Apis;

import QAcart.Testing_APIs.Base.Specs;
import QAcart.Testing_APIs.Data.Routes;
import io.restassured.response.Response;
import QAcart.Testing_APIs.Pojos.UserPojo;

import static io.restassured.RestAssured.given;

public class UsersApi {

    public static Response Register(UserPojo userPojo){
        return given()
                .spec(Specs.getRequestSpecNoAuth())
                .body(userPojo)
                .when()
                .post(Routes.RegisterPath)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response Login(UserPojo userPojo){
        return given()
                .spec(Specs.getRequestSpecNoAuth())
                .body(userPojo)
                .when()
                .post(Routes.LoginPath)
                .then()
                .log().all()
                .extract().response();
    }

}
