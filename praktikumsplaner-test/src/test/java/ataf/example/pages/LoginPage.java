package ataf.example.pages;

import ataf.web.model.LocatorType;
import ataf.web.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class LoginPage extends BasePage {

    public LoginPage(RemoteWebDriver driver) {
        super(driver);
    }

    private final String BENUTZERNAME_EINGABE = "username";
    private final String PASSWORT_EINGABE = "password";
    private final String BUTTON_PASSWORT_ANZEIGEN_AUSBLENDEN = "password-show-password";
    private final String BUTTON_ANMELDUNG = "kc-login";

    public boolean wrongUserMessageIsDisplayed() {
        WebElement e = findElementByLocatorType("Ungültiger Benutzername oder Passwort.", LocatorType.TEXT, false);
        if (e == null)
            return false;
        return true;
    }

    public void loginWithCredentials(String username, String password) {
        this.enterCredentials(username, password);
        this.klickeAnmeldenButton();
    }

    public void enterCredentials(String username, String password) {
        this.enterBenutzername(username);
        this.enterPasswort(password);
    }

    public void enterBenutzername(String benutzername) {
        enterTextInWebElement(DEFAULT_EXPLICIT_WAIT_TIME, benutzername, findElementByLocatorType(this.BENUTZERNAME_EINGABE, LocatorType.ID, true));
    }

    public void enterPasswort(String password) {
        enterTextInWebElement(DEFAULT_EXPLICIT_WAIT_TIME, password, findElementByLocatorType(this.PASSWORT_EINGABE, LocatorType.ID, true));
    }

    public void klickeAugeButton() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME, findElementByLocatorType(this.BUTTON_PASSWORT_ANZEIGEN_AUSBLENDEN, LocatorType.ID, true), false);
    }

    public void klickeAnmeldenButton() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME, findElementByLocatorType(this.BUTTON_ANMELDUNG, LocatorType.ID, true), false);
    }

    //Check the type property if the input, if it's text the text is not masked, if it's password it is masked.
    public boolean isPasswordObscured() {
        WebElement passwordFeld = findElementByLocatorType(this.PASSWORT_EINGABE, LocatorType.ID, true);
        return (passwordFeld.getAttribute("type").compareTo("password") == 0);
    }

    public void obscurePassword() {
        if (!isPasswordObscured())
            klickeAugeButton();
    }

    public void showPassword() {
        if (isPasswordObscured())
            klickeAugeButton();
    }
}
