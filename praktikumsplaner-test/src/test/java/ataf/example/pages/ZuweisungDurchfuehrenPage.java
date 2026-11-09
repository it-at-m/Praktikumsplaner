package ataf.example.pages;

import ataf.web.model.LocatorType;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ZuweisungDurchfuehrenPage extends PraktikumsplanerPage {

    public ZuweisungDurchfuehrenPage(RemoteWebDriver driver) {
        super(driver);
    }

    private final String PRAKTIKUMSPLATZ_DROPDOWN_XPATH = "//select[@data-test='praktikumsplatz-zuweisung-dropdown']";
    private final String STUDENT_DROPDOWN_XPATH = "//select[@data-test='student-dropdown']";
    private final String Zuweisung_SPEICHERN_XPATH = "//button[@data-test='zuteilung-save-btn']";

    public void waehlePraktikumsplatz(String platzName) {
        selectDropDownListValueByContainingVisibleText(DEFAULT_EXPLICIT_WAIT_TIME, PRAKTIKUMSPLATZ_DROPDOWN_XPATH, LocatorType.XPATH, platzName);
    }

    public void waehleStudent(String studentName) {
        selectDropDownListValueByContainingVisibleText(DEFAULT_EXPLICIT_WAIT_TIME, STUDENT_DROPDOWN_XPATH, LocatorType.XPATH, studentName);
    }

    public void durchfuehreZuweisung() {
        clickOnWebElement(DEFAULT_EXPLICIT_WAIT_TIME,
                findElementByLocatorType(Zuweisung_SPEICHERN_XPATH, LocatorType.XPATH, true), false);
    }

    public void zuweisungDurchfuehren(String platzName, String studentName) {
        waehlePraktikumsplatz(platzName);
        waehleStudent(studentName);
        durchfuehreZuweisung();
    }
}
