package com.restapi.webservices.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

    @GetMapping("/hello-world")
    public String helloWorld(){
        return "Hello, World!!";
    }
    
    /*
    @GetMapping("/basicauth")
    public String basicAuthCheck() {
    	return "Success.";
    }
    */

    @GetMapping("/hello-world-bean/{name}")
    public HelloWorld helloWorldBean(@PathVariable String name){
        return new HelloWorld(String.format("Hello, %s", name));
    }
    
    @GetMapping("/hello-world/international")
    public String helloWorldInternational(){
    	
    	Locale locale = LocaleContextHolder.getLocale();
    	
    	return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
    }
    
    @PostMapping("/hello/{name}")
    public String greeting(@PathVariable String name) {
    	return "Hello, " + name;
    }
}
