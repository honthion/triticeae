package com.ibeesaas.demo.web;

import com.ibeesaas.demo.pojo.User;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Random;

@RestController
public class HelloController {
    @Value("${environments.dev.name}")
    private String env;
    @Value("${author.desc}")
    private String desc;
    @Value("${author.age}")
    private Integer age;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Registration registration;

    @ApiOperation(value = "hello测试")
    @RequestMapping("/hello")
    public Object index() throws Exception {
        int sleepTime = new Random().nextInt(3000);
        logger.info(" sleepTime:" + sleepTime);
        Thread.sleep(sleepTime);
        logger.info("/ hello, host:" + registration.getHost() + ", service_id:" + registration.getServiceId());
        return "Hello World";
    }

    @ApiOperation(value = "获取User")
    @RequestMapping("/user")
    public Object getUser(@RequestParam String name) {
        return new User() {{
            setAge(18);
            setName(name);
        }};
    }

    @ApiOperation(value = "查询自身的服务id")
    @GetMapping(value = "/info/local", produces = "application/json;charset=UTF-8")
    public String getInfoLocal() {
        JSONObject jsonTemp = new JSONObject();
        jsonTemp.put("ServiceId", registration.getServiceId());
        jsonTemp.put("ServiceUri", registration.getUri());
        jsonTemp.put("ServiceHost", registration.getHost());
        jsonTemp.put("ServicePort", registration.getPort());
        jsonTemp.put("ServiceMetadata", registration.getMetadata());
        return jsonTemp.toString();
    }
}