package ataf.example.steps;

import ataf.core.context.TestExecutionContext;
import ataf.core.data.Environment;
import ataf.core.helpers.TestPropertiesHelper;
import ataf.core.properties.DefaultValues;
import ataf.core.utils.RunnerUtils;
import ataf.example.data.TestData;
import ataf.example.pages.LoginPage;
import ataf.web.model.LocatorType;
import ataf.web.pages.SingleSignOnPage;
import ataf.web.utils.DriverUtil;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import org.testng.Assert;

public class LoginSteps {

    private final LoginPage LOGIN_PAGE;

    public LoginSteps() {
        LOGIN_PAGE = new LoginPage(DriverUtil.getDriver());
    }

    @Wenn("Sie {string} als Benutzername eingeben")
    public void wennSieAlsBenutzernameEingeben(String username) {
        LOGIN_PAGE.enterBenutzername(username);
    }

    @Wenn("Sie {string} als Passwort eingeben")
    public void wennSieAlsPasswortEingeben(String password) {
        LOGIN_PAGE.enterPasswort(password);
    }

    @Wenn("Sie auf das Augensymbol klicken")
    public void wennSieAufDasAugensymbolKlicken() {
        LOGIN_PAGE.klickeAugeButton();
    }

    @Wenn("Sie auf Anmelden klicken")
    public void wennSieAufAnmeldenKlicken() {
        LOGIN_PAGE.klickeAnmeldenButton();
    }

    @Dann("steht das Passwort im Klartext")
    public void dannStehtDasPasswortImKlartext() {
        Assert.assertTrue(!LOGIN_PAGE.isPasswordObscured(), "User menu is not visibly open!");
    }

    @Dann("erscheint eine Fehlermeldung")
    public void dannErscheintEineFehlermeldung() {
        Assert.assertTrue(LOGIN_PAGE.wrongUserMessageIsDisplayed(), "User menu is not visibly open!");
    }

    @Angenommen("man befindet sich auf der Praktikumsplaner Website")
    public void angenommenManBefindetSichAufDerPraktikumsplanerWebsite() {
        LOGIN_PAGE.navigateToPage("https://praktikumsplaner-test.muenchen.de/");
    }

    @Wenn("Sie die korrekten Credentials eines Users aus dem TestUserPool eingeben")
    public void wennSieDieKorrektenCredentialsEinesUsersAusDemTestUserPoolEingeben() {
        //TODO: Environment mit TestUserPool anlegen, um Verschlüsselung zu demonstrieren
        LOGIN_PAGE.loginWithCredentials("", "");
    }

    @Dann("sind Sie angemeldet auf der Praktikumsplaner Startseite")
    public void dannSindSieAngemeldetAufDerPraktikumsplanerStartseite() {
        LOGIN_PAGE.waitForPage("Praktikumsplaner");
    }

    @Angenommen("man ist auf der Praktikumsplaner Startseite")
    public void angenommenManIstAufDerPraktikumsplanerStartseite() {
        Environment environment;
        if (RunnerUtils.isJiraBasedTestExecution()) {
            environment = Environment.contains(TestExecutionContext.get().ENVIRONMENT);
        } else {
            environment = TestData.TEST;
        }
        Assert.assertNotNull(environment, "Test environment has not been set! Check your test execution issue. Valid environments: [TEST]");

        // Navigiere zur WiLMA Startseite
        LOGIN_PAGE.navigateToPage(environment.getSystemUrl("PRAKTIKUMSPLANER"));

        /*
         * final StringBuilder clearUserName = new StringBuilder();
         * final StringBuilder clearUserPassword = new StringBuilder();
         * // Vatiante 1. SSO User über properties
         * // Credentials mit properties setzen: -Dsso.username=BENUTZERNAME -Dsso.password=PASSWORD
         * try {
         * // Hole sicher die LDAP Credentials
         * AuthenticationHelper.getUserName().access(clearUserName::append);
         * AuthenticationHelper.getUserPassword().access(clearUserPassword::append);
         *
         * if (TestPropertiesHelper.getPropertyAsBoolean("useIncognitoMode", true,
         * DefaultValues.USE_INCOGNITO_MODE)) {
         * // Logge dich bei SSO ein
         * new SingleSignOnPage(DriverUtil.getDriver(), "username", LocatorType.ID, "password",
         * LocatorType.ID, "kc-login", LocatorType.ID,
         * clearUserPassword.toString());
         * }
         *
         * } finally {
         * // Lösche LDAP Credentials
         * clearUserName.delete(0, clearUserName.length() - 1);
         * clearUserName.setLength(0);
         * clearUserPassword.delete(0, clearUserPassword.length() - 1);
         * clearUserPassword.setLength(0);
         * }
         */

        // Vatiante 2. Test User
        if (TestPropertiesHelper.getPropertyAsBoolean("useIncognitoMode", true, DefaultValues.USE_INCOGNITO_MODE)) {
            // Logge dich bei SSO ein
            new SingleSignOnPage(DriverUtil.getDriver(), "username", LocatorType.ID, "password", LocatorType.ID, "kc-login", LocatorType.ID)
                    .executeSingleSignOnLogin(TestData.PRAKTIKUMSPLANER_USER.getUserName(), TestData.PRAKTIKUMSPLANER_USER.getPassword(), "");
        }

        // Warte auf WiLMA Startseite
        LOGIN_PAGE.waitForPage("Praktikumsplaner");

    }

    @Wenn("man den Browser schliesst")
    public void wennManDenBrowserSchliesst() {
        DriverUtil.getDriver().close();
    }

    @Dann("kann man neu testen")
    public void dannKannManNeuTesten() {
        Assert.assertTrue(DriverUtil.getDriver() == null);
    }
}
