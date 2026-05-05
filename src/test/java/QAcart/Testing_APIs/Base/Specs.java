package QAcart.Testing_APIs.Base;

import QAcart.Shared.Data.Routes;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {
    public static RequestSpecification getRequestSpecNoAuth(){
        return given()
                .baseUri(Routes.BaseURL)
                .contentType(ContentType.JSON);
    }
    public static RequestSpecification getRequestSpecAuth(String token){
        return given()
                .baseUri(Routes.BaseURL)
                .contentType(ContentType.JSON)
                .auth().oauth2(token);
    }
}
