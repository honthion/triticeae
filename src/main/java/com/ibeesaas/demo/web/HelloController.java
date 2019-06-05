package com.ibeesaas.demo.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HelloController {
    @Value("${environments.dev.name}")
    private String env;
    @Value("${author.desc}")
    private String desc;
    @Value("${author.age}")
    private Integer age;

    @RequestMapping("/hello")
    public Object index() {

        return new HashMap<String, String>() {{
            put(env, desc + "  " + age);
        }};
    }
}