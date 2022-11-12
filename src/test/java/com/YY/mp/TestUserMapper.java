package com.YY.mp;

import com.YY.mp.mapper.UserMapper;
import com.YY.mp.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserMapper {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert(){
        User user = new User();
        user.setAge(20);
        user.setEmail("test@itcast.cn");
        user.setName("曹操");
        user.setUserName("caocao");
        user.setPassword("123456");

        int insert = this.userMapper.insert(user);  //数据库受影响的行数
        System.out.println("insert = " + insert);

        System.out.println("user.getId() = " + user.getId());//自增长id会回填到语句中
    }
}
