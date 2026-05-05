package QAcart.Shared.Builds;

import com.github.javafaker.Faker;
import QAcart.Testing_APIs.Apis.UsersApi;
import QAcart.Shared.Pojos.UserPojo;

public class UserBuild {
    public static UserPojo CreateNew(){
        Faker f = new Faker();
        String firstName, lastName;
        do{
            firstName = f.name().firstName();
            lastName = f.name().lastName();
        } while(lastName.length() <3 || firstName.length() <3);
        String email = f.internet().emailAddress();
        String password = "TestPass";

        return new UserPojo(firstName, lastName, email, password);
    }

    public static String CreateToken(){
         return UsersApi.Register(CreateNew()).body().path("access_token");
    }
}
