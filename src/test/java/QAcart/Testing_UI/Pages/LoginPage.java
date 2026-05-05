package QAcart.Testing_UI.Pages;

import QAcart.Shared.Data.Routes;
import QAcart.Testing_UI.Base.BasePage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public BasePage login(String email, String pass) {
        this.email.sendKeys(email);
        password.sendKeys(pass);
        submit.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        try {
            wait.until(ExpectedConditions.urlContains(Routes.HomePage));
            return new HomePage(driver);
        } catch (TimeoutException e) {
            return this;
        }
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
