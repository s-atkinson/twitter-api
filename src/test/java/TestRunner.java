import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features/",
        glue = "stepDefinitions",
        tags = "@common or @twitter",
        plugin = {"json:target/cucumber.json", "pretty"}
)
public class TestRunner {
}
