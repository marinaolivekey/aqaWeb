package ru.netology.order;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class orderAppTestV1 {
    private WebDriver driver;



    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver","driver/chromedriver");
    }
    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldTestSomething(){
        //загрузить стр
        driver.get("http://0.0.0.0:9999/");
        //поиск элемента
        //взаимодействие с элементом
    }
}
