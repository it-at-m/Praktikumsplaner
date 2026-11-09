package ataf.example.pages;

import ataf.web.model.LocatorType;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PraktikumsplatzMeldenPage extends PraktikumsplanerPage {

    public PraktikumsplatzMeldenPage(RemoteWebDriver driver) {
        super(driver);
    }

    private final String PRAKTIKUMSPLATZ_NAME_XPATH = "//input[@data-test='praktikumsplatz-name-input']";
    private final String PRAKTIKUMSPLATZ_BESCHREIBUNG_XPATH = "//textarea[@data-test='praktikumsplatz-beschreibung-input']";
    private final String PRAKTIKUMSPLATZ_SPEICHERN_XPATH = "//button[@data-test='praktikumsplatz-save-btn']";

    public void enterName(String name) {
        enterTextInWebElement(DEFAULT_EXPLICIT_WAIT_TIME, name, PRAKTIKUMSPLATZ_NAME_XPATH, LocatorType.XPATH, true, false);
    }

    public void enterBeschreibung(String beschreibung) {
        enterTextInWebElement(DEFAULT_EXPLICIT_WAIT_TIME, beschreibung, PRAKTIKUMSPLATZ_BESCHREIBUNG_XPATH, LocatorType.XPATH, true, false);
    }

    public void klickeSpeichern() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(PRAKTIKUMSPLATZ_SPEICHERN_XPATH, LocatorType.XPATH, true), false);
    }

    public void praktikumsPlatzmelden(String name, String beschreibung) {
        enterName(name);
        enterBeschreibung(beschreibung);
        klickeSpeichern();
    }
}
