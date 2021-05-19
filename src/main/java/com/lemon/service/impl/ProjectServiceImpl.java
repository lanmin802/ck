package com.lemon.service.impl;

import com.lemon.pojo.Project;
import com.lemon.mapper.ProjectMapper;
import com.lemon.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

}
