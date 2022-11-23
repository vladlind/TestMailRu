package stepdefinitions;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyStepdefs {

    SeleniumBase seleniumBase = new SeleniumBase();

    @Дано("Пользователь зайдет с аккаунтом из файла {string} в свой почтовый ящик Mail.ru")
    public void пользовательЗалогинилсяВСвойПочтовыйЯщикMailRu(String fileUri) throws InterruptedException {
        seleniumBase.callPage("login")
                .login(fileUri)
                .pause();
        assertTrue(SeleniumBase.driver.getPageSource().contains("Написать письмо"));
    }

    @Когда("пользователь отправит письмо с сообщением: {string}")
    public void пользовательСоздастПисьмоСНепустымТелом(String body) {
        boolean isLetterSent = seleniumBase.writeLetter(body);
        assertTrue(isLetterSent);
    }

    @Тогда("в папке отправленные появится сообщение об отправке этого письма")
    public void вПапкеОтправленныеПоявитсяСообщениеОбОтправкеЭтогоПисьма() {
        boolean isBodyPresent = seleniumBase.checkSentLetter();
        assertTrue(isBodyPresent);
    }
}
