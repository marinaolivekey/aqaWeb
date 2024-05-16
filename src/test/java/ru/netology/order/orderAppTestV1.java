package ru.netology.order;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class orderAppTestV1 {
    private WebDriver driver;



    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver","driver/chromedriver");
    }
    @BeforeEach
    void setup() {
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
    void shouldTestSomething() throws InterruptedException {
        //загрузить стр
        driver.get("http://0.0.0.0:9999/");

        //поиск элемента
        WebElement form = driver.findElement(By.className("form"));
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Оксана Петрова");
        elements.get(1).sendKeys("+79991234567");

        //взаимодействие с элементом
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
        Thread.sleep(50000);


    }
}
