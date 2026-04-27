package QAcart.TestCases;

import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import QAcart.Apis.UsersApi;
import QAcart.Builds.UserBuild;
import QAcart.Pojos.ErrorPojo;
import QAcart.Pojos.UserPojo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UsersTest {
    @Test
    @Description("Positive registration")
    public void ValidRegister(){
        UserPojo userPojo = UserBuild.CreateNew();
        Response response = UsersApi.Register(userPojo);
        UserPojo resUser = response.body().as(UserPojo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(resUser.getFirstName(), equalTo(userPojo.getFirstName()));
    }
    @Test
    @Description("Negative registration - Duplicated email")
    public void inValidRegister(){
        UserPojo userPojo = UserBuild.CreateNew();
        userPojo.setEmail("hani@me.com");
        Response response = UsersApi.Register(userPojo);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(resMsg.getMessage(), containsString("exists"));
    }

    @Test
    @Description("Positive login")
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
    @Description("Negative login")
    public void inValidLogin(){
        UserPojo userPojo = UserBuild.CreateNew();
        UsersApi.Register(userPojo);
        UserPojo newLogin = new UserPojo(userPojo.getEmail(), "Incorrect");
        Response response = UsersApi.Login(newLogin);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(resMsg.getMessage(), equalTo("The email and password combination is not correct, please fill a correct email and password"));
    }
}
