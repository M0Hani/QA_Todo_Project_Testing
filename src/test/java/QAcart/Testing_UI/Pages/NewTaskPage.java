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

public class NewTaskPage extends BasePage {
    public NewTaskPage(WebDriver driver){
        super(driver);
    }
    @FindBy(css = "[data-testid=\"new-todo\"]")
    WebElement NewItemBox;
    @FindBy(css = "[data-testid=\"submit-newTask\"]")
    WebElement submitItem;
    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div/div/div/p")
    WebElement helperText;
    @FindBy(xpath = "//*[@id=\"root\"]/div[1]/div/div/button")
    private WebElement LogOut;

    public NewTaskPage load(){
        driver.get(Routes.BaseURL + Routes.NewTaskPage);
        return this;
    }

    public BasePage fillItem(String content){
        NewItemBox.sendKeys(content);
        submitItem.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        try {
            wait.until(ExpectedConditions.urlToBe(Routes.BaseURL + Routes.HomePage));
            return new HomePage(driver);
        } catch (TimeoutException e) {
            return this;
        }
    }

    public String getHelperText(){ return helperText.getText(); }

    public LoginPage logOut(){
        LogOut.click();
        return new LoginPage(driver);
    }
}
