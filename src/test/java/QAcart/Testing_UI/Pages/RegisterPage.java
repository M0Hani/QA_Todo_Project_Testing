package QAcart.Testing_UI.Pages;

import QAcart.Shared.Data.Routes;
import QAcart.Testing_UI.Base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class RegisterPage extends BasePage {
    public RegisterPage(WebDriver driver){
        super(driver);
    }
    @FindBy(css = "[data-testid = \"first-name\"]")
    private WebElement firstName;
    @FindBy(css = "[data-testid = \"last-name\"]")
    private WebElement lastName;
    @FindBy(css = "[data-testid = \"email\"]")
    private WebElement email;
    @FindBy(css = "[data-testid = \"password\"]")
    private WebElement password;
    @FindBy(css = "[data-testid = \"confirm-password\"]")
    private WebElement confirmPassword;
    @FindBy(css = "[data-testid = \"submit\"]")
    private WebElement submit;
    @FindBy(css = "[data-testid=\"error\"]")
    private WebElement errorMessage;
    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div/div/div[1]/p")
    private WebElement firstNameHelperText;
    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div/div/div[3]/p")
    private WebElement emailHelperText;

    public BasePage register(String firstName, String lastName, String email, String password, String confirmPassword){
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.email.sendKeys(email);
        this.password.sendKeys(password);
        this.confirmPassword.sendKeys(confirmPassword);
        submit.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        if(driver.getCurrentUrl().contains(Routes.HomePage)) {
            return new HomePage(driver);
        }
        else {return this;}
    }

    public RegisterPage load(){
        driver.get(Routes.BaseURL + Routes.RegisterPage);
        return this;
    }

    public String getErrorMessage(){
        return errorMessage.getText();
    }
    public String getEmailHelperText(){
        return emailHelperText.getText();
    }

    public String getFNameHelperText(){ return firstNameHelperText.getText(); }
}
