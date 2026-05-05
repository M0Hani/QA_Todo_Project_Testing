package QAcart.Testing_APIs.TestSuites;

import QAcart.Shared.Data.ErrorMsgs;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import QAcart.Testing_APIs.Apis.UsersApi;
import QAcart.Shared.Builds.UserBuild;
import QAcart.Shared.Pojos.ErrorPojo;
import QAcart.Shared.Pojos.UserPojo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiLoginTest {

    @Test
    @Description("Positive login in api")
    public void ValidLogin(){
        UserPojo userPojo = UserBuild.CreateNew();
        UsersApi.Register(userPojo);
        UserPojo newLogin = new UserPojo(userPojo.getEmail(), userPojo.getPassword());
        Response response = UsersApi.Login(newLogin);
        UserPojo resUser = response.body().as(UserPojo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(resUser.getAccessToken(), not(equalTo(null)));
    }
    @Test
    @Description("Negative login - Wrong password case")
    public void inValidLoginPassword(){
        UserPojo userPojo = UserBuild.CreateNew();
        UsersApi.Register(userPojo);
        UserPojo newLogin = new UserPojo(userPojo.getEmail(), "Incorrect");
        Response response = UsersApi.Login(newLogin);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(resMsg.getMessage(), equalTo(ErrorMsgs.WrongPassword));
    }
    @Test
    @Description("Negative login - Unregistered email case")
    public void inValidLoginEmail(){
        UserPojo newLogin = new UserPojo("not@there.com", "random password");
        Response response = UsersApi.Login(newLogin);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(resMsg.getMessage(), equalTo(ErrorMsgs.UnRegisteredEmail));
    }

}
