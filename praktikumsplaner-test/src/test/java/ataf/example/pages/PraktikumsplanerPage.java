package ataf.example.pages;

import ataf.web.pages.BasePage;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class PraktikumsplanerPage extends BasePage {

    private MenuBar menuBar;

    public PraktikumsplanerPage(RemoteWebDriver driver) {
        super(driver);
        this.menuBar = new MenuBar(driver);
    }
}
