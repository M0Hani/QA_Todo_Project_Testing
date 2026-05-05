package QAcart.Testing_UI.Pages;

import QAcart.Shared.Data.Routes;
import QAcart.Testing_UI.Base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public NewTaskPage load(){
        driver.get(Routes.BaseURL + Routes.NewTaskPage);
        return this;
    }

    public BasePage fillItem(String content){
        NewItemBox.sendKeys(content);
        submitItem.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        if(driver.getCurrentUrl().contains(Routes.HomePage)) {
            return new HomePage(driver);
        }
        else {return this;}

    }

    public String getHelperText(){ return helperText.getText(); }
}
