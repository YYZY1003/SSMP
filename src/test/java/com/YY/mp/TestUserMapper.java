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
        user.setMail("test@itcast.cn");
        user.setName("曹操");
        user.setUserName("caocao");
        user.setPassword("123456");

        int insert = this.userMapper.insert(user);  //数据库受影响的行数
        System.out.println("insert = " + insert);

        System.out.println("user.getId() = " + user.getId());//自增长id会回填到语句中
    }

    @Test
    public void testSelectById(){
        User user = this.userMapper.selectById(1L);
        System.out.println("user = " + user);
    }

    @Test
    public void testUpdateById(){
        User user = new User();
        user.setId(1L);
        user.setAge(11);

        int i = this.userMapper.updateById(user);

        System.out.println("i = " + i);
    }
}
