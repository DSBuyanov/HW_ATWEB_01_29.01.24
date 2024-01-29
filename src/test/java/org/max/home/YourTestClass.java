package org.max.home;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class YourTestClass {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void yourTest() {
        login("Tester1347", "11bdc518d8");
        addDummy("Dummy", "Tester1347");

    }

    @AfterAll
    public static void tearDown() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        saveScreenshot("screenshot_" + timestamp + ".png");

        if (driver != null) {
            driver.quit();
        }

    }


    private static void saveScreenshot(String filename) {
        try {
            // Используем класс TakesScreenshot для создания скриншота
            TakesScreenshot screenshot = (TakesScreenshot) driver;

            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File("src/main/resources/" + filename);
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void login(String username, String password) {

        driver.get("https://test-stand.gb.ru/login");


        WebElement usernameField = driver.findElement(By.cssSelector("form#login input[type='text']"));
        WebElement passwordField = driver.findElement(By.cssSelector("form#login input[type='password']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);


        WebElement loginButton = driver.findElement(By.cssSelector("form#login button"));
        loginButton.click();


    }

    private static void addDummy(String dummyName, String dummyDescription) {
        try {


            // Нажатие на кнопку '+'
            WebElement addButton = driver.findElement(By.id("create-btn"));
            addButton.click();


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-item")));


            WebElement titleInput = driver.findElement(By.xpath("//*[@type='text']"));
            titleInput.sendKeys(dummyName);


            WebElement descriptionInput = driver.findElement(By.cssSelector("div.field textarea.mdc-text-field__input"));
            descriptionInput.sendKeys(dummyDescription);


            WebElement saveButton = driver.findElement(By.cssSelector("button[type='submit'][form='create-item']"));
            saveButton.click();


            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("create-item")));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}