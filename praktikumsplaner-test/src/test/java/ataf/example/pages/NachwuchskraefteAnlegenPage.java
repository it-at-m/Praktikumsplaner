package ataf.example.pages;

import ataf.web.pages.BasePage;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NachwuchskraefteAnlegenPage extends PraktikumsplanerPage {

    public NachwuchskraefteAnlegenPage(RemoteWebDriver driver) {
        super(driver);
    }

    private final String NACHWUCHSKRAFT_HINZUFÜGEN_XPATH = "//button[@data-test='nwk-create-dialog-open-btn']";
    private final String EXCEL_HOCHLADEN_XPATH = "//button[@data-test='nwk-excel-import-open-btn']";
    private final String SUCHE_TEXTFELD_XPATH = "//div[@data-test='data-table-search']";
    private final String SUCHE_DROPDOWN_XPATH = "//div[@data-test='data-table-group-by']";

    private final String NACHWUCHSKRAFT_LÖSCHEN_XPATH = "";//TODO: Wie gehen wir mit der Liste an NWKS um? => Beim NWK erstellen eindeutige ID als Namen vergeben, damit danach gesucht werden kann beim Löschenprivate final String MELDEZEITRAUM_HINZUFÜGEN_XPATH = "//button[@data-test='meldezeitraum-create-open-btn']";

    private final String NWK_VORNAME_XPATH = "//div[@data-test='nwk-vorname-input']";
    private final String NWK_NACHNAME_XPATH = "//div[@data-test='nwk-vorname-input']";
    private final String NWK_JAHRGANG_XPATH = "//div[@data-test='nwk-jahrgang-input']";
    private final String NWK_VORLESUNGSTAGE_XPATH = "//div[@data-test='nwk-vorlesungstage-select']";
    private final String NWK_RICHTUNG_DROPDOWN_XPATH = "//div[@data-test='nwk-richtung-select']";
    private final String NWK_ABBRECHEN_BUTTON_XPATH = "//button[@data-test='nwk-cancel-btn']";
    private final String NWK_AKZEPTIEREN_XPATH = "//button[@data-test='nwk-accept-btn']";
}
