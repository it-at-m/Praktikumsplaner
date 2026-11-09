package ataf.example.data;

import ataf.core.data.Environment;
import ataf.core.data.TestUser;
import ataf.core.data.UserType;
import ataf.core.helpers.TestDataHelper;
import ataf.core.logging.ScenarioLogManager;
import ataf.web.model.LocatorType;

/**
 * @author Daniel Basaran
 */
public class TestData {

    //Environments
    public static final Environment PRODUCTION = new Environment("PROD");
    public static final Environment TEST = new Environment("TEST");
    //Remote via Gitlab Pipeline Variablen
    public static final TestUser PRAKTIKUMSPLANER_USER = new TestUser(System.getenv("TEST_USER_NAME"),
            System.getenv("TEST_USER_PASSWORD"), TEST, UserType.NONE);
    //Lokal via Kommandozeilenparameter
    //public static final TestUser PRAKTIKUMSPLANER_USER = new TestUser(System.getProperty("sso.username"),
    //        System.getProperty("sso.password"), TEST, UserType.NONE);

    public static void init() {
        ScenarioLogManager.getLogger().info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Start of initializing test data>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        ScenarioLogManager.getLogger().info("Adding systems for Test");
        //Remote via Gitlab Pipeline Variablen
        TEST.addSystem("PRAKTIKUMSPLANER", System.getenv("TEST_URL"));
        //Lokal via Kommandozeilenparameter
        //TEST.addSystem("PRAKTIKUMSPLANER", System.getProperty("testurl"));
		
        ScenarioLogManager.getLogger().info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Finished initialization of test data<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
