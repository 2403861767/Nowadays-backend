package com.seeleaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seeleaf.model.domain.Tag;
import com.seeleaf.service.TagService;
import com.seeleaf.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author 24038
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2023-03-13 20:15:42
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




