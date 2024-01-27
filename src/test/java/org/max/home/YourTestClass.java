package org.max.home;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class YourTestClass {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        // Установка пути к драйверу Chrome
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        // Инициализация экземпляра ChromeDriver
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        driver = new ChromeDriver(chromeOptions);
        // Настройка ожидания элементов
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void yourTest() {
        // Ваши действия в тесте
        login("GB202308cd04f17", "98628f7f2b");
        addDummy("dummyName", "dummyLogin");
        // Дополнительные шаги теста, если необходимо
        // ...
        // Сохранение скриншота окна браузера
        // saveScreenshot("название_скриншота");
    }

    @AfterAll
    public static void tearDown() {
        // Завершение работы драйвера после выполнения всех тестов
        if (driver != null) {
            driver.quit();
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

    private static void addDummy(String dummyName, String dummyLogin) {
        // Ваши действия по добавлению dummy
        // ...
    }

    // Дополнительные методы теста, если необходимо
    // ...
}
