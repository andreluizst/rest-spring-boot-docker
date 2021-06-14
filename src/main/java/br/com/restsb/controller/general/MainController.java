package br.com.restsb.controller.general;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.restsb.data.model.Greeting;
import br.com.restsb.exception.UnsuportedMathOperationException;

@RestController
public class MainController {

	private static final String template = "Hello, %s!";
	
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="world") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@RequestMapping(value="/sum/{number1}/{number2}", method=RequestMethod.GET)
	public Double sum(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws Exception{
		Double result = 0.0;
		if (!isNumeric(number1) || !isNumeric(number2)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		result = convertToDouble(number1) + convertToDouble(number2);
		return result;
	}
	
	private boolean isNumeric(String number) {
		if (number == null)
			return false;
		String num = number.replaceAll(",", ".");
		return num.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
	private Double convertToDouble(String number) {
		if (number == null)
			return 0D;
		String num = number.replaceAll(",", ".");
		if (isNumeric(num))
			return Double.parseDouble(num);
		return 0D;
	}
}
