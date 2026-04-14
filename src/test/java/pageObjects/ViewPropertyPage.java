package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewPropertyPage extends BasePage{

    public ViewPropertyPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//button[normalize-space()='Edit Property']")
    public WebElement EditPropertyBtn;

    public void  clickEditProperty(){
        EditPropertyBtn.click();
    }

    @FindBy(xpath = "//span[normalize-space()='Sold out Property']")
    public WebElement SoldOutPropertyBtn;

    public void  clickSoldOutProperty(){
        SoldOutPropertyBtn.click();
    }
    @FindBy(xpath = "//span[normalize-space()='Delete Property']")
    public WebElement DeletePropertyBtn;

    public void  clickDeleteProperty(){
        DeletePropertyBtn.click();
    }

    @FindBy(xpath = "//button[normalize-space()='No, Keep it']")
    WebElement NoKeepItBtn;


    public void  clickNoKeepIt(){
        NoKeepItBtn.click();
    }

    @FindBy(xpath = "//button[contains(normalize-space(),'Yes Delete')]")
    WebElement YesDeleteBtn;
    public void  clickYesDelete(){
        YesDeleteBtn.click();
    }

    @FindBy(xpath = "//button[contains(@class,'sucess_ok_button')]")
    WebElement sucess_ok_btn;
    public void  clicksucess_ok(){
        sucess_ok_btn.click();
    }
    @FindBy(xpath="//button[text()='View Property']") WebElement ViewPropertyBtn;
    public void clickOnViewPropertybtn(){
        ViewPropertyBtn.click();
    }
}
