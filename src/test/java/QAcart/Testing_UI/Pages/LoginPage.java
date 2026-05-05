package QAcart.Testing_UI.Pages;

import QAcart.Shared.Data.Routes;
import QAcart.Testing_UI.Base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver){
        super(driver);
    }
    @FindBy(css = "[data-testid=\"email\"]")
    private WebElement email;

    @FindBy(css = "[data-testid=\"password\"]")
    private WebElement password;

    @FindBy(css = "[data-testid=\"submit\"]")
    private WebElement submit;

    @FindBy(css = "[data-testid=\"error-alert\"]")
    private WebElement errorMessage;

    @FindBy(css = "[id=\"email-helper-text\"]")
    private WebElement helperText;

    public BasePage login(String email, String pass){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        this.email.sendKeys(email);
        password.sendKeys(pass);
        submit.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        if(driver.getCurrentUrl().contains(Routes.HomePage)) {
            return new HomePage(driver);
        }
        else {return this;}
    }

    public LoginPage load(){
        driver.get(Routes.BaseURL);
        return this;
    }

    public String getErrorMessage(){
        return errorMessage.getText();
    }
    public String getHelperText(){
        return helperText.getText();
    }
}
