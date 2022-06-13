package script.pages;

import Utility.OperatorsUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class OpenGoogleFactory {
    private WebDriver driver;
    private OperatorsUtility operators;

    public OpenGoogleFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        operators = new OperatorsUtility(driver);
    }
}
