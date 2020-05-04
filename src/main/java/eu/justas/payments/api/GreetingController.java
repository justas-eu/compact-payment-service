package eu.justas.payments.api;

import eu.justas.payments.domain.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = "/greeting", method = GET)
	public @ResponseBody Greeting greeting(
			@RequestParam(value="name", required=false, defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
