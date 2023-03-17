package com.seeleaf.controller;

import com.seeleaf.common.BaseResponse;
import com.seeleaf.common.ResultUtils;
import com.seeleaf.model.domain.Tag;
import com.seeleaf.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @GetMapping("/list")
    public BaseResponse<List<Tag>> getAllTags(){
        List<Tag> list = tagService.list();
        return ResultUtils.success(list);
    }

}
