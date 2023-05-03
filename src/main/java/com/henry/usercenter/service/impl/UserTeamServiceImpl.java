package com.henry.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henry.usercenter.model.domain.UserTeam;
import com.henry.usercenter.service.UserTeamService;
import com.henry.usercenter.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2023-04-18 19:47:10
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




