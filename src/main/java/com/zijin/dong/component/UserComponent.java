package com.zijin.dong.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijin.dong.entity.Users;
import com.zijin.dong.mapper.UsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserComponent {

    private final String hashName = "user-id-name";

    @Value("${spring.redis.switch}")
    private boolean redisSwitch;

    private final BoundHashOperations<String, String, String> hashOperations;
    private final UsersMapper usersMapper;

    private final Logger logger = LoggerFactory.getLogger(UserComponent.class);

    public UserComponent(UsersMapper usersMapper, RedisTemplate<String, String> redisTemplate) {
        this.usersMapper = usersMapper;
        this.hashOperations = redisTemplate.boundHashOps(hashName);
    }

    /**
     * 根据id获取用户名称
     * 先从redis中获取，若未获取到，从数据库获取，并添加缓存
     * @param id 用户id
     * @return 用户名
     */
    public String getNameById(Long id) {
        if (redisSwitch) {
            String name = hashOperations.get(String.valueOf(id));
            if (Objects.isNull(name) || name.length() == 0) {
                logger.warn("redis中无数据，从数据库获取");
                QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", id);
                queryWrapper.select("username");
                Users user = usersMapper.selectOne(queryWrapper);
                name = user.getUsername();
                hashOperations.put(String.valueOf(id), name);
                return name;
            } else {
                return name;
            }
        } else {
            QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            queryWrapper.select("username");
            Users user = usersMapper.selectOne(queryWrapper);
            return user.getUsername();
        }
    }

}
