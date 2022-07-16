package Main;

import java.io.IOException;
import java.awt.image.BufferedImage;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.Screenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import javax.imageio.ImageIO;
import java.io.File;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.AShot;
import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;

public class FetchUNCC
{
    public void screenshot() throws IOException, InterruptedException {
        TimeUnit.SECONDS.sleep(3L);
        System.out.println("\n\n********************************\n***   Fetching UNCC Pages   ***\n********************************\n\n");
        System.setProperty("webdriver.gecko.driver", "chromedriver");
        System.setProperty("webdriver.chrome.verboseLogging", "true");
        final ChromeOptions options = new ChromeOptions().setHeadless(true).setLogLevel(ChromeDriverLogLevel.SEVERE);
        final WebDriver sovi = new ChromeDriver(options);
        final String soviurl = "https://app.safespace.io/api/display/live-occupancy/15da3cfa?view=percent";
        sovi.get(soviurl);
        sovi.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        final Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(sovi);
        ImageIO.write(s.getImage(), "PNG", new File("./img/sovi.png"));
        sovi.quit();
        final WebDriver crown = new ChromeDriver(options);
        final String crownurl = "https://app.safespace.io/api/display/live-occupancy/7a9c0a24?view=percent";
        crown.get(crownurl);
        crown.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        final Screenshot c = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(crown);
        ImageIO.write(c.getImage(), "PNG", new File("./img/crown.png"));
        crown.quit();
        final WebDriver parking = new ChromeDriver(options);
        final String parkingurl = "https://parkingavailability.charlotte.edu/";
        parking.get(parkingurl);
        parking.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        final WebElement list = parking.findElement(By.className("mat-list"));
        final Screenshot p = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(parking);
        final BufferedImage listScreenshot = p.getImage().getSubimage(list.getLocation().getX(), list.getLocation().getY(), list.getSize().getWidth() / 2, list.getSize().getHeight());
        ImageIO.write(listScreenshot, "PNG", new File("./img/parking.png"));
        parking.quit();
        listScreenshot.flush();
        System.out.println("\n\n********************************\n*** Done Fetching UNCC Pages ***\n********************************\n\n");
    }
}
