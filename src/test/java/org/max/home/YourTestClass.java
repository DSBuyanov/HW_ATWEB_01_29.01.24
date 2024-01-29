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
        // Установка пути к драйверу Chrome
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        // Инициализация экземпляра ChromeDriver
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        // Настройка ожидания элементов
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void yourTest() {
        // Ваши действия в тесте
        login("Tester1347", "11bdc518d8");
        addDummy("Dummy", "Tester1347");
        // Дополнительные шаги теста, если необходимо
        // ...
        // Сохранение скриншота окна браузера
        // saveScreenshot("название_скриншота");
    }

    @AfterAll
    public static void tearDown() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        saveScreenshot("screenshot_" + timestamp + ".png");
        // Завершение работы драйвера после выполнения всех тестов
        if (driver != null) {
            driver.quit();
        }

    }

    // Метод для сохранения скриншота
    private static void saveScreenshot(String filename) {
        try {
            // Используем класс TakesScreenshot для создания скриншота
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            // Получаем изображение
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            // Копируем изображение в директорию resources
            File destinationFile = new File("src/main/resources/" + filename);
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void login(String username, String password) {
        // Открытие страницы логина
        driver.get("https://test-stand.gb.ru/login");

        // Ввод логина и пароля
        WebElement usernameField = driver.findElement(By.cssSelector("form#login input[type='text']"));
        WebElement passwordField = driver.findElement(By.cssSelector("form#login input[type='password']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        // Нажатие кнопки "Login"
        WebElement loginButton = driver.findElement(By.cssSelector("form#login button"));
        loginButton.click();

        // Добавьте явные ожидания, если необходимо дождаться каких-либо элементов после логина
    }

    private static void addDummy(String dummyName, String dummyDescription) {
        try {
            // Ваши действия по добавлению dummy

            // Нажатие на кнопку '+'
            WebElement addButton = driver.findElement(By.id("create-btn"));
            addButton.click();

            // Явное ожидание после нажатия кнопки '+'
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-item")));

            // Ввод значения в поле Title
            WebElement titleInput = driver.findElement(By.xpath("//*[@type='text']"));
            titleInput.sendKeys(dummyName);

            // Ввод значения в поле Description
            WebElement descriptionInput = driver.findElement(By.cssSelector("div.field textarea.mdc-text-field__input"));
            descriptionInput.sendKeys(dummyDescription);

            // Нажатие кнопки Save
            WebElement saveButton = driver.findElement(By.cssSelector("button[type='submit'][form='create-item']"));
            saveButton.click();

            // Явное ожидание после нажатия кнопки Save
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("create-item")));

            // Дополнительные шаги теста, если необходимо
            // ...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}