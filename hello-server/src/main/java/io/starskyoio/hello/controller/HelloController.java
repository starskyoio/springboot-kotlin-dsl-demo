package io.starskyoio.hello.controller;

import io.starskyoio.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public Result<String> hello() {
        Result<String> result = new Result<>();
        result.setCode(0);
        result.setMsg("success");
        return result;
    }
}
