package QAcart.Testing_UI.Pages;

import QAcart.Shared.Data.Routes;
import QAcart.Testing_UI.Base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;


public class HomePage extends BasePage {
    public HomePage(WebDriver driver){
        super(driver);
    }
    @FindBy(css = "[data-testid=\"add\"]")
    private WebElement addbutt;
    @FindBy(css = "[data-testid=\"welcome\"]")
    private WebElement WelcomeMessage;
    @FindBy(css = "[data-testid=\"todo-item\"]")
    private WebElement item;
    @FindBy(css = "[data-testid=\"delete\"]")
    private WebElement delbutt;
    @FindBy(css = "[data-testid=\"complete-task\"]")
    private WebElement taskComplete;
    @FindBy(css = "[data-testid=\"no-todos\"]")
    private WebElement empty;
    @FindBy(xpath = "//*[@id=\"root\"]/div[1]/div/div/button")
    private WebElement LogOut;
    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div/div/ul")
    private WebElement taskPaging;

    public HomePage load(){
        driver.get(Routes.BaseURL + Routes.HomePage);
        return this;
    }

    public String getWelcomeMessage(){
        return WelcomeMessage.getText();
    }

    public boolean isWelMesDisplayed(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return WelcomeMessage.isDisplayed();
    }

    public NewTaskPage AddItem(){
        addbutt.click();
        return new NewTaskPage(driver);
    }

    public String item_text(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return item.getText();
    }

    public HomePage DeleteItem(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        delbutt.click();
        return this;
    }

    public boolean EmptyTodos(){
        return empty.isDisplayed();
    }

    public LoginPage logOut(){
        LogOut.click();
        return new LoginPage(driver);
    }

    public boolean pagingIsDisplayed(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return taskPaging.isDisplayed();
    }

    public void CheckTask(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        taskComplete.click();
    }
    public boolean isTaskChecked(){ return taskComplete.isSelected(); }
}
