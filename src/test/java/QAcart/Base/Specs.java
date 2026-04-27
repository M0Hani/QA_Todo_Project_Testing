package QAcart.Base;

import QAcart.Data.Routes;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {
    public static RequestSpecification getRequestSpec(){
        return given()
                .baseUri(Routes.BaseURL)
                .contentType(ContentType.JSON);
    }
}
