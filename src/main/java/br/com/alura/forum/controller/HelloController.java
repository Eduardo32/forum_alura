package br.com.alura.forum.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @ApiOperation("bla")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de pessoa"),
    })

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }
}
