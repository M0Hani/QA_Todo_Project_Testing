package QAcart.Apis;

import QAcart.Base.Specs;
import QAcart.Data.Routes;
import io.restassured.response.Response;
import QAcart.Pojos.UserPojo;

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
