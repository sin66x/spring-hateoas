package com.mnordic.test.hateoas.representation;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mnordic.test.hateoas.controller.Greeting;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String TEMPLATE = "Hello, %s!";

    @RequestMapping("/greeting")
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());
        greeting.add(Link.of("http://www.google.com").withRel("Search"));
        greeting.add(linkTo(methodOn(GreetingController.class).bye()).withRel("Bye"));

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }

    @RequestMapping("/bye")
    public HttpEntity<Greeting> bye() {

        Greeting greeting = new Greeting("Bye!");
        greeting.add(linkTo(methodOn(GreetingController.class).bye()).withSelfRel());
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}