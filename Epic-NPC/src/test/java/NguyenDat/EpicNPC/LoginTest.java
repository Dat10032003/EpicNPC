package NguyenDat.EpicNPC;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setup() {
        // Initialize WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void testLoginPage() {
        // Navigate to the login page
        driver.get("http://localhost:8080/login");

        // Locate the input elements and perform actions
        driver.findElement(By.id("username")).sendKeys("Nagisa"); // Provide the test username
        driver.findElement(By.id("password")).sendKeys("dat123456"); // Provide the test password

        // Submit the login form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Wait for the homepage to load and check if login was successful
        try {
            Thread.sleep(2000); // wait for 2 seconds (not recommended for production code, use WebDriverWait instead)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the username or avatar is displayed on the homepage
        boolean loginSuccess = driver.getPageSource().contains("Nagisa");

        // Assert that login was successful and display a congratulatory message
        assertTrue(loginSuccess);

        // Print a message in the console if login is successful
        if (loginSuccess) {
            System.out.println("Chúc mừng bạn đã đăng nhập thành công!");
        }
    }

    @Test
    public void testLoginPageRedirection() {
        // Navigate to login page
        driver.get("http://localhost:8080/login");

        // Check that the page contains the "Log in" text to ensure it's the correct page
        assertTrue(driver.getPageSource().contains("Log in"));
    }

    // Close the browser after the tests are done
    @AfterAll
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
