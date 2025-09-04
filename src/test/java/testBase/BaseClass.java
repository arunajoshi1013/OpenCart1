package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

    public static WebDriver driver;
    public Properties p;

    @BeforeClass(groups = {"Sanity", "Regression", "Master", "Datadriven"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException {
        // Load properties
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        String executionEnv = p.getProperty("execution_env");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (executionEnv.equalsIgnoreCase("remote")) {
            // Set platform
            if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            }
                else if (os.equalsIgnoreCase("linux")) {
                    capabilities.setPlatform(Platform.LINUX);
            } else if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);
            } else {
                System.out.println("No matching OS");
                return;
            }

            // Set browser
            switch (br.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    break;
                case "edge":
                    capabilities.setBrowserName("edge");
                    break;
                case "safari":
                    capabilities.setBrowserName("safari");
                    break;
                default:
                    System.out.println("No matching browser");
                    return;
            }

            // Start Remote WebDriver              //for selenium grid and docker
           // try {
               // driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
           // } catch (Exception e) {
               // e.printStackTrace();
               // return;
           // }

        } else if (executionEnv.equalsIgnoreCase("local")) {
            // Local execution
            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "safari":
                    driver = new SafariDriver();
                    break;
                default:
                    System.out.println("Invalid browser name");
                    return;
            }
        }

        // ✅ Driver must be initialized before this point
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get(p.getProperty("appURL2")); // Open application
            driver.manage().window().maximize();
        } else {
            System.out.println("Driver was not initialized. Please check configuration.");
        }
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master", "Datadriven"})
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Utility methods

    public String randomeString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomeNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomeAlphaNumeric() {
        String letters = RandomStringUtils.randomAlphabetic(3);
        String numbers = RandomStringUtils.randomNumeric(3);
        return letters + "$" + numbers;
    }

    public String captureScreen(String tname) {
        String timestamp = new SimpleDateFormat("yy-MM-dd-HH-mm-ss").format(new Date());
        String screenshotsDir = System.getProperty("user.dir") + File.separator + "screenshots";
        String targetPath = screenshotsDir + File.separator + tname + "-" + timestamp + ".png";

        // Ensure screenshots folder exists
        new File(screenshotsDir).mkdirs();

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(targetPath);

        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return targetPath;
    }
}