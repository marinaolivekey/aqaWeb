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

public class OrderAppTestV1 {
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
    void shouldTestHappyPath() throws InterruptedException {
        //загрузить стр
        driver.get("http://localhost:9999/");

        //поиск элемента
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");

        //взаимодействие с элементом
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldShowInvalidInputIfNothingEntered() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldShowInvalidInputIfNameEnteredPhoneNotCheckBoxNot() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[2]/span/span/span[3]")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldShowInvalidInputIfNameEnteredPhoneEnteredCheckBoxNot() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input_invalid")).getText();

        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

    @Test
    void shouldShowInvalidIfNameEnglish() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivan petrov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");

        //взаимодействие с элементом
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldShowInvalidIfNameSpecialCharacters() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("!!! @@@");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");

        //взаимодействие с элементом
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldAcceptIfSpaceBeforeName() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(" Рита Маргарита");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");

        //взаимодействие с элементом
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
        }

    @Test
    void shouldAcceptIfSpaceOnly() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(" ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");

        //взаимодействие с элементом
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldShowInvalidIfNameNotFull() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ольга");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456789");

        //взаимодействие с элементом
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }
}


/* механизм поняла, нет времени дописать тесты
из-за кучи тех моментов - то JNI ошибки, то Gradle JVM версии и тд, чего я только уже не исправила
самм искала, время потеряла, нужно догнать программу

дописала бы такие тесты
 + короткое имя Ю Ю
 + длинное имя 50 символов+50 символов
 + дефис перед именем
 + дефис после имени
 + тройное имя

 Тел:
 - буквы
 - спец символы
 - только плюс
 - 10 цифр
 - 12 цифр
 - 11 цифр и пробел
 - пробел в начале
 - код страны +7 (а не +32) - а тут через contains  проверку сделать?


*/

