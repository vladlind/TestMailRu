package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SeleniumBase {

    private static Logger log = Logger.getLogger(SeleniumBase.class.getName());

    public static final String BASE_URL = "https://account.mail.ru/";

    public static ChromeDriver driver;

    private String bodyToCheck;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "\\Users\\Владимир\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
        Dimension dem = new Dimension(2048,1024);
        driver.manage().window().setSize(dem);
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        log.info("Webdriver started");
    }

    @After
    public void tearDown() {
        driver.quit();
        log.info("Webdriver closed");
    }

    public SeleniumBase callPage(String uri) {
        driver.get(BASE_URL + uri);
        return this;
    }
    public SeleniumBase pause() throws InterruptedException {
        Thread.sleep(5000);
        return this;
    }

    public SeleniumBase login(String uri) {
        List<String> loginAndPass = AccountFileReader.readLoginAndPassword(uri);
        WebElement elementLogin = driver.findElement(By.name("username"));
        elementLogin.sendKeys(loginAndPass.get(0));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/form/div[2]/div/div[3]/div/div/div[1]/button/span"));
        loginButton.click();
        WebElement elementPassword = driver.findElement(By.name("password"));
        elementPassword.sendKeys(loginAndPass.get(1));
        WebElement passwordButton =  driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/form/div[2]/div/div[3]/div/div/div[1]/div/button/span"));
        passwordButton.click();
        return this;
    }

    public boolean writeLetter(String body) {
        if (body.isEmpty()) {
            log.info("Empty body not allowed!");
            return false;
        } else {
            bodyToCheck = body;
        }
        WebElement writeButton = driver.findElement(By.className("compose-button__wrapper"));
        writeButton.click();
        WebElement elementAddress = driver.findElement(By.className("container--zU301"));
        elementAddress.sendKeys("test@test.ru");
        WebElement elementText = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div/div[2]/div[3]/div[5]/div/div/div[2]/div[1]/div[1]"));
        elementText.sendKeys(body);
        WebElement sendButton = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div/div[3]/div[1]/div[1]/div/button/span"));
        sendButton.click();
        WebElement closeButton = driver.findElement(By.xpath("/html/body/div[10]/div/div/div[2]/div[2]/div/div/div[1]/span"));
        closeButton.click();
        return true;
    }

    public boolean checkSentLetter() {
        WebElement sentButton = driver.findElement(By.xpath("//*[@id=\"sideBarContent\"]/div/nav/a[6]"));
        sentButton.click();
        WebElement elementBody = driver.findElement(By.xpath("//*[@id=\"app-canvas\"]/div/div[1]/div[1]/div/div[2]/span/div[2]/div/div/div/div/div[1]/div/div/div/div[1]/div/div/a[1]/div[4]/div/div[3]/span[2]/div/span"));
        log.info("Email body: "+elementBody.getText());
        return elementBody.getText().contains(bodyToCheck);
    }

}
