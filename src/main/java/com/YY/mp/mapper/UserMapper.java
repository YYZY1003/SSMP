package com.YY.mp.mapper;

import com.YY.mp.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.boot.SpringBootConfiguration;

public interface UserMapper extends BaseMapper<User> {

    public User findById(Long id);
}
