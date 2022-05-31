package com.zijin.dong.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijin.dong.entity.Users;
import com.zijin.dong.mapper.UsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Objects;

@Component
public class UserComponent {

    private final Jedis jedis;

    private final UsersMapper usersMapper;

    private final Logger logger = LoggerFactory.getLogger(UserComponent.class);

    public UserComponent(JedisPool jedisPool, UsersMapper usersMapper) {
        this.jedis = jedisPool.getResource();
        this.usersMapper = usersMapper;
    }

    /**
     * 根据id获取用户名称
     * 先从redis中获取，若未获取到，从数据库获取，并添加缓存
     * @param id 用户id
     * @return 用户名
     */
    public String getNameById(Long id) {
        String name = jedis.get(String.valueOf(id));
        // 若redis中无数据，则从数据库中查询,且添加redis缓存
        if (Objects.isNull(name) || name.length() <= 0) {
            logger.warn("redis中无数据，从数据库获取");
            QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            queryWrapper.select("username");
            Users user = usersMapper.selectOne(queryWrapper);
            name = user.getUsername();
            jedis.append(String.valueOf(id), name);
        }
        // 若数据库中无数据，则报错
        if (Objects.isNull(name) || name.length() <= 0) {
            logger.error("操作日志记录错误，未查询到该id");
            return null;
        }
        return name;
    }

}
