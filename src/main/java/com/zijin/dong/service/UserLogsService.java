package com.zijin.dong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijin.dong.entity.UserLogs;
import com.zijin.dong.entity.base.Pages;
import com.zijin.dong.entity.base.Paging;

/**
* @author Reminis
* @description 针对表【user_logs】的数据库操作Service
* @createDate 2022-05-28 16:37:12
*/
public interface UserLogsService extends IService<UserLogs> {

    Pages<UserLogs> getAllLogs(Paging paging);
}
