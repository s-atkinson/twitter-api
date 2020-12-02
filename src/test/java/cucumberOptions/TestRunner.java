package cucumberOptions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features/ReusableSteps.feature",
        glue = "stepDefinitions"
        //tags = "@test",
)
public class TestRunner {
}
