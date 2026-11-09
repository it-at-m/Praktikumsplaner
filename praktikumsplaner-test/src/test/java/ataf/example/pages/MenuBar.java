package ataf.example.pages;

import ataf.web.model.LocatorType;
import ataf.web.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MenuBar extends BasePage {

    public MenuBar(RemoteWebDriver driver) {
        super(driver);
    }

    //Attribute
    private final String HOME_BUTTON_XPATH = "//button[@data-test='app-home-title']";

    //OpenMenu
    private final String TOGGLE_MENU_BAR_XPATH = "//button[@data-test='app-nav-toggle']";

    //4 Seiten
    private final String NACHWUCHSKRÄFTE_ANLEGEN_XPATH = "//a[@data-test='nav-nachwuchskraefte']";
    private final String MELDEZEITRAUM_ANLEGEN_XPATH = "//a[@data-test='nav-meldezeitraum']";
    private final String PRAKTIKUMSPLATZ_MELDEN_XPATH = "//a[@data-test='nav-praktikumsplaetze']";
    private final String ZUWEISUNG_DURCHFUEHREN_XPATH = "//a[@data-test='nav-zuweisung']";

    public void toggleMenuBar() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.TOGGLE_MENU_BAR_XPATH, LocatorType.XPATH, true), false);
    }

    public void openMenuBar() {
        if (!this.isMenuBarVisible()) //Ist MenuBar unsichtbar
            toggleMenuBar();
    }

    public void closeMenuBar() {
        if (this.isMenuBarVisible()) //Ist MenuBar unsichtbar
            toggleMenuBar();
    }

    private boolean isMenuBarVisible() {
        WebElement e = findElementByLocatorType(this.MELDEZEITRAUM_ANLEGEN_XPATH, LocatorType.XPATH,
                false);
        return e.isDisplayed();
    }

    public void navigiereZuHomePage() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME, findElementByLocatorType(this.HOME_BUTTON_XPATH,
                LocatorType.XPATH, true), false);
    }

    public void navigiereZuNachwuchskraefteAnlegen() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.NACHWUCHSKRÄFTE_ANLEGEN_XPATH, LocatorType.XPATH, true), false);
    }

    public void navigiereZuMeldezeitraumAnlegen() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.MELDEZEITRAUM_ANLEGEN_XPATH, LocatorType.XPATH, true), false);
    }

    public void navigiereZuPraktikumsplatzMelden() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.PRAKTIKUMSPLATZ_MELDEN_XPATH, LocatorType.XPATH, true), false);
    }

    public void navigiereZuZuweisungDurchfuehren() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.ZUWEISUNG_DURCHFUEHREN_XPATH, LocatorType.XPATH, true), false);
    }

}
