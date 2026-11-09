package ataf.example.pages;

import ataf.web.model.LocatorType;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MeldezeitraumAnlegenPage extends PraktikumsplanerPage {

    public MeldezeitraumAnlegenPage(RemoteWebDriver driver) {
        super(driver);
    }

    private final String MELDEZEITRAUM_HINZUFÜGEN_XPATH = "//button[@data-test='meldezeitraum-create-open-btn']";
    private final String MELDEZEITRAUM_LÖSCHEN_XPATH = "";//TODO: Wie gehen wir mit der Liste an Meldezeiträumen um? => Beim Meldezeitraum erstellen eindeutige ID als Namen vergeben, damit danach gesucht werden kann beim Löschenprivate final String MELDEZEITRAUM_HINZUFÜGEN_XPATH = "//button[@data-test='meldezeitraum-create-open-btn']";
    private final String MELDEZEITRAUM_NAME_XPATH = "//button[@data-test='meldezeitraum-name-input']";
    private final String MELDEZEITRAUM_START_XPATH = "//button[@data-test='meldezeitraum-start-input']";
    private final String MELDEZEITRAUM_ENDE_XPATH = "//button[@data-test='meldezeitraum-ende-input']";
    private final String MELDEZEITRAUM_ZURUECK_BUTTON_XPATH = "//button[@data-test='meldezeitraum-back-btn']";
    private final String MELDEZEITRAUM_SAVE_XPATH = "//button[@data-test='meldezeitraum-save-btn']";

    public void klickeMeldezeitraumHinzufuegen() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.MELDEZEITRAUM_HINZUFÜGEN_XPATH, LocatorType.XPATH, true), false);
    }

    public void enterName(String name) {
        enterTextInWebElement(DEFAULT_EXPLICIT_WAIT_TIME, name, MELDEZEITRAUM_NAME_XPATH, LocatorType.XPATH, true, false);
    }

    public void enterBeginn(String beginn) {
        enterTextInWebElement(DEFAULT_EXPLICIT_WAIT_TIME, beginn, MELDEZEITRAUM_START_XPATH, LocatorType.XPATH, true, false);
    }

    public void enterEnde(String ende) {
        enterTextInWebElement(DEFAULT_EXPLICIT_WAIT_TIME, ende, MELDEZEITRAUM_ENDE_XPATH, LocatorType.XPATH, true, false);
    }

    public void klickeSpeichern() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.MELDEZEITRAUM_SAVE_XPATH, LocatorType.XPATH, true), false);
    }

    public void klickeZurueck() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.MELDEZEITRAUM_ZURUECK_BUTTON_XPATH, LocatorType.XPATH, true), false);
    }

    public void meldezeitraumAnlegen(String name, String beginn, String ende) {
        this.enterName(name);
        this.enterBeginn(beginn);
        this.enterEnde(ende);
        this.klickeSpeichern();
    }

    //TODO: Richtigen Meldezeitraum auswählen und löschen
    public void navigiereZuMeldezeitraumLoeschen() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(this.MELDEZEITRAUM_LÖSCHEN_XPATH, LocatorType.XPATH, true), false);
    }
}
