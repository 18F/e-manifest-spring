package gov.epa.emanifest.web;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HelloWorldControllerTest {

  private HelloWorldController fixture;

  @Before
  public void setUp() {
		fixture = new HelloWorldController();
  }
  
  @Test
  public void testSayHello() {
    HelloMessage actual = fixture.sayHello();

    assertEquals(actual.getMessage(), "Hello, World!");
  }
}
