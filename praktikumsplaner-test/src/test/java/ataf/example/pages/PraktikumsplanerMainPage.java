package ataf.example.pages;

import ataf.web.model.LocatorType;
import ataf.web.pages.BasePage;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class PraktikumsplanerMainPage extends PraktikumsplanerPage {
    public PraktikumsplanerMainPage(RemoteWebDriver driver) {
        super(driver);
    }

    private final String NACHWUCHSKRÄFTE_ANLEGEN_XPATH = "//a[@data-test='main-card-nachwuchskraefte']";
    private final String MELDEZEITRAUM_ANLEGEN_XPATH = "//a[@data-test='main-card-meldezeitraum']";
    private final String PRAKTIKUMSPLATZ_MELDEN_XPATH = "//a[@data-test='main-card-praktikumsplaetze']";
    private final String ZUWEISUNG_DURCHFUEHREN_XPATH = "//a[@data-test='main-card-zuweisung']";

    public void navigiereZuNachwuchskräfte() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.NACHWUCHSKRÄFTE_ANLEGEN_XPATH, LocatorType.XPATH, true), false);
    }

    public void navigiereZuMeldezeitraum() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.MELDEZEITRAUM_ANLEGEN_XPATH, LocatorType.XPATH, true), false);
    }

    public void navigiereZuPraktikumsplatz() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.PRAKTIKUMSPLATZ_MELDEN_XPATH, LocatorType.XPATH, true), false);
    }

    public void navigiereZuZuweisung() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.ZUWEISUNG_DURCHFUEHREN_XPATH, LocatorType.XPATH, true), false);
    }

    public void ichBinAufDerStartseite() {
        Assert.assertTrue(isWebElementVisible(DEFAULT_EXPLICIT_WAIT_TIME,
                "//h1[text() = 'Willkommen beim Praktikumsplaner!']", LocatorType.XPATH, false),
                "Praktikumsplaner is not available");
    }

}
