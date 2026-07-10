package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    public void shouldSubmitOrder() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            driver.get("http://localhost:9999");
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='name'] input")))
                .sendKeys("Иван Петров");
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='phone'] input")))
                .sendKeys("+79001234567");
            
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test-id='agreement']")))
                .click();
            
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button")))
                .click();
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='order-success']")));
            
            // 🔑 ДОБАВИЛИ .trim() — убирает лишние пробелы!
            String message = driver.findElement(By.cssSelector("[data-test-id='order-success']"))
                                   .getText()
                                   .trim();
                                   
            assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", message);
            
        } finally {
            driver.quit();
        }
    }
}
