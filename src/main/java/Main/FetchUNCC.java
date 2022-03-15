package Main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class FetchUNCC {
    public void screenshot() throws IOException {
        // Load sovi
        System.setProperty("webdriver.gecko.driver", "geckodriver");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        FirefoxOptions options = new FirefoxOptions().setHeadless(true).setLogLevel(FirefoxDriverLogLevel.INFO);
        WebDriver sovi = new FirefoxDriver(options);
        String soviurl = "https://app.safespace.io/api/display/live-occupancy/15da3cfa?view=percent";
        sovi.get(soviurl);
        sovi.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // capture screenshot and save the image
        Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(sovi);
        ImageIO.write(s.getImage(),"PNG",new File("./img/sovi.png"));
        sovi.quit();

        // Load crown
        WebDriver crown = new FirefoxDriver(options);
        String crownurl = "https://app.safespace.io/api/display/live-occupancy/7a9c0a24?view=percent";
        crown.get(crownurl);
        crown.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // capture screenshot and save the image
        Screenshot c = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(crown);
        ImageIO.write(c.getImage(),"PNG",new File("./img/crown.png"));
        crown.quit();

        // Load parking
        WebDriver parking = new FirefoxDriver(options);
        String parkingurl = "https://parkingavailability.charlotte.edu/";
        parking.get(parkingurl);
        parking.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement list = parking.findElement(By.className("mat-list"));

        // capture screenshot, crop it, and then save the image
        Screenshot p = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(parking);
        BufferedImage listScreenshot = p.getImage().getSubimage(list.getLocation().getX(), list.getLocation().getY(),
                list.getSize().getWidth() / 2, (int) ((double) list.getSize().getHeight() / 1.175));
        ImageIO.write(listScreenshot,"PNG",new File("./img/parking.png"));
        crown.quit();
    }
}
