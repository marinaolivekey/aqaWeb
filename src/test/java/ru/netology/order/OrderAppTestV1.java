package ru.netology.order;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderAppTestV1 {
    private WebDriver driver;


    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldTestHappyPath() throws InterruptedException {
        //поиск элемента
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");

        //взаимодействие с элементом
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=order-success]")).isDisplayed());
    }

    @Test
    void shouldShowInvalidInputIfNothingEntered() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldShowInvalidInputIfNameNotPhoneYesCheckBoxYes() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldShowInvalidInputIfNameEnteredPhoneNotCheckBoxYes() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldShowInvalidInputIfNameEnteredPhoneYesCheckBoxNot() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input_invalid")).getText();

        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

    @Test
    void shouldShowInvalidIfNameEnglish() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivan petrov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldShowInvalidIfNameSpecialCharacters() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("!!! @@@");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldAcceptIfSpaceBeforeName() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(" Рита Маргарита");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
        }

    @Test
    void shouldShowInvalidIfSpaceOnly() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(" ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldShowInvalidIfNameNotFull() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ольга");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456789");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldAcceptIfNamesIsOneLetter() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ю Ю");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldAcceptIfName100Letters() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Напу Амо Хала Она Она Анека Вехи Вехи Она Хивеа Нена Вава Кехо Онка Кахе Хеа Леке Еа Она Ней Нана Ниа Кеко Оа Ога Ван Ика Ванао");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456789");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldShowInvalidIfNameStartWithDash() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("-Ольга Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456789");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно", text);
    }

    @Test
    void shouldShowInvalidIfNameEndsWithDash() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ольга Петров-");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79123456789");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно", text);
    }

    @Test
    void shouldShowInvalidIfPhoneIsNotCorrect() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петров-Долгих Ольга");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("Qwerty");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }
}


/*
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

