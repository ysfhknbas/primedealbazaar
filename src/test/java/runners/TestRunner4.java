package runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "rerun:target/failedRerun.txt",
        },
        monochrome = true,
        features = "./src/test/resources/features",
        glue = "stepdef",
        dryRun = false, // (true) gives only missing steps - (false) default
        tags = "@seller4"// run only this

)
public class TestRunner4 {
}
