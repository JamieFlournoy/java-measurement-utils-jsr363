package com.pervasivecode.utils.measure.test.cucumber.steps;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.truth.Truth.assertThat;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.pervasivecode.utils.measure.examples.BitsPerSecondExample;
import com.pervasivecode.utils.measure.examples.BytesPerSecondExample;
import com.pervasivecode.utils.measure.examples.DurationExample;
import com.pervasivecode.utils.measure.examples.ExampleApplication;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CodeExampleSteps {
  private String commandOutput = "";
  private ExampleApplication codeExample = null;

  @Given("^I am running the Bytes Per Second Example$")
  public void iAmRunningTheBytesPerSecondExample() {
    iAmRunningTheExample(new BytesPerSecondExample());
  }

  @Given("^I am running the Bits Per Second Example$")
  public void iAmRunningTheBitsPerSecondExample() {
    iAmRunningTheExample(new BitsPerSecondExample());
  }

  @Given("^I am running the Duration Example$")
  public void iAmRunningTheDurationExample() {
    iAmRunningTheExample(new DurationExample());
  }

  private void iAmRunningTheExample(ExampleApplication exampleClass) {
    this.codeExample = exampleClass;
    this.commandOutput = "";
  }

  @When("^I run the program$")
  public void iRunTheProgram() {
    checkNotNull(this.codeExample, "did you forget an 'I am running the' example step?");
    StringWriter sw = new StringWriter();
    this.codeExample.runExample(new PrintWriter(sw, true));
    commandOutput = commandOutput.concat(sw.toString());
  }

  @Then("^I should see the output$")
  public void iShouldSeeTheOutput(String expected) {
    assertThat(this.commandOutput).isEqualTo(expected);
  }
}
