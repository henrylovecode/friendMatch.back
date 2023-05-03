package com.henry.usercenter.service;

import com.henry.usercenter.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henry.usercenter.model.domain.User;
import com.henry.usercenter.model.dto.TeamQuery;
import com.henry.usercenter.model.request.TeamJoinRequest;
import com.henry.usercenter.model.request.TeamQuitRequest;
import com.henry.usercenter.model.request.TeamUpdateRequest;
import com.henry.usercenter.model.vo.TeamUserVo;

import java.util.List;

/**
* @author lenovo
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2023-04-18 19:44:43
*/
public interface TeamService extends IService<Team> {
    /**
     * 创建队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team , User loginUser);



    /**
     * 搜索队伍
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVo> listTeams(TeamQuery teamQuery ,  boolean isAdmin);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest , User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest , User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);


    /**
     * 删除队伍
     * @param id
     * @return
     */
    boolean deleteTeam(long id,User loginUser);
}
