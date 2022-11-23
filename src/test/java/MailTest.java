import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "stepdefinitions",
        tags = "@success",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class MailTest {

}