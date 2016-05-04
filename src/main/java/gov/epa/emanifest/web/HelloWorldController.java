package gov.epa.emanifest.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
public class HelloWorldController {

	@RequestMapping(method = RequestMethod.GET)
	public HelloMessage sayHello() {
		return new HelloMessage();
	}
  
}
