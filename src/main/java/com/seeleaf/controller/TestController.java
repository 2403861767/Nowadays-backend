package com.seeleaf.controller;

import com.seeleaf.utils.ToImgUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class TestController {
    @PostMapping("/test")
    @ApiOperation("测试接口")
    public String test(@RequestBody HashMap<String, String> map, HttpServletRequest request){
        System.out.println(map);
        System.out.println(request);
        return "xxx";
    }
    @GetMapping("/upload")
    public String upload(String base){
        ToImgUtil toImgUtil = ToImgUtil.ToImg(base);
        return "ok";
    }


}
