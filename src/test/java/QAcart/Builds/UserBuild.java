package QAcart.Builds;

import com.github.javafaker.Faker;
import QAcart.Apis.UsersApi;
import QAcart.Pojos.UserPojo;

public class UserBuild {
    public static UserPojo CreateNew(){
        Faker f = new Faker();
        String firstName = f.name().firstName();
        String lastName = f.name().lastName();
        String email = f.internet().emailAddress();
        String password = "TestPass";

        return new UserPojo(firstName, lastName, email, password);
    }

    public static String CreateToken(){
         return UsersApi.Register(CreateNew()).body().path("access_token");
    }
}
