package QAcart.Testing_APIs.TestSuites;

import QAcart.Testing_APIs.Data.ErrorMsgs;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import QAcart.Testing_APIs.Apis.UsersApi;
import QAcart.Testing_APIs.Builds.UserBuild;
import QAcart.Testing_APIs.Pojos.ErrorPojo;
import QAcart.Testing_APIs.Pojos.UserPojo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UsersTest {
    //Register test cases
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
    public void inValidRegisterDup(){
        UserPojo userPojo1 = UserBuild.CreateNew();
        UsersApi.Register(userPojo1);
        UserPojo userPojo2 = UserBuild.CreateNew();
        userPojo2.setEmail(userPojo1.getEmail());
        Response response = UsersApi.Register(userPojo2);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(resMsg.getMessage(), containsString(ErrorMsgs.EmailAlreadyExists));
    }
    @Test
    @Description("Negative registration - Malformed email")
    public void inValidRegisterMal(){
        UserPojo userPojo = UserBuild.CreateNew();
        userPojo.setEmail("NotAValidEmailForm");
        Response response = UsersApi.Register(userPojo);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(resMsg.getMessage(), containsString(ErrorMsgs.NotValidEmailForm));
    }
    @Test
    @Description("Negative registration - Required field (password) missing")
    public void inValidRegisterMissing(){
        UserPojo userPojo = UserBuild.CreateNew();
        userPojo.setPassword(null);
        Response response = UsersApi.Register(userPojo);
        ErrorPojo resMsg = response.body().as(ErrorPojo.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(resMsg.getMessage(), containsString(ErrorMsgs.PasswordIsRequired));
    }


    //Login test cases
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
