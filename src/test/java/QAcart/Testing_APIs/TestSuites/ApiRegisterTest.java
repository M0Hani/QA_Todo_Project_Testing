package QAcart.Testing_APIs.TestSuites;

import QAcart.Shared.Builds.UserBuild;
import QAcart.Shared.Data.ErrorMsgs;
import QAcart.Shared.Pojos.ErrorPojo;
import QAcart.Shared.Pojos.UserPojo;
import QAcart.Testing_APIs.Apis.UsersApi;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class ApiRegisterTest {

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

}
