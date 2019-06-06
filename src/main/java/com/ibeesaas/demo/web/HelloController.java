package com.ibeesaas.demo.web;

import com.ibeesaas.demo.pojo.User;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class HelloController {
    @Value("${environments.dev.name}")
    private String env;
    @Value("${author.desc}")
    private String desc;
    @Value("${author.age}")
    private Integer age;

//    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private Registration registration;

    @ApiOperation(value = "hello测试")
    @RequestMapping("/hello")
    public Object index() {
        return new HashMap<String, String>() {{
            put(env, desc + "  " + age);
        }};
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