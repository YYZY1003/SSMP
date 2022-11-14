package com.YY.mp;

import com.YY.mp.mapper.UserMapper;
import com.YY.mp.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserMapper {
    @Test
    public void testInsert() {
        for (int i = 0; i < 5; i++) {

            User user = new User();
            user.setAge(20 + i);
            user.setMail("test@itcast.cn");
            user.setName("曹操" + i);
            user.setUserName("caocao");
            user.setPassword("123456");

            int insert = this.userMapper.insert(user);  //数据库受影响的行数
            System.out.println("insert = " + insert);

            System.out.println("user.getId() = " + user.getId());//自增长id会回填到语句中

        }
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectById() {
        User user = this.userMapper.selectById(1L);
        System.out.println("user = " + user);
    }

    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(1L);
        user.setAge(11);

        int i = this.userMapper.updateById(user);

        System.out.println("i = " + i);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setAge(11);
        user.setPassword("9999");

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        QueryWrapper<User> userQueryWrapper = wrapper.eq("user_name", "zhangsan");//匹配name=zhangsan

        //根据条件做更新
        int i = this.userMapper.update(user, userQueryWrapper);

        System.out.println("i = " + i);
    }

    @Test
    public void testUpdate2() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        UpdateWrapper<User> set = wrapper.set("age", 21).set("password", "8888888"). //更新的字段
                eq("user_name", "zhangsan");  //更新的条件

        int i = this.userMapper.update(null, set);

        System.out.println("i = " + i);
    }

    //删除操作
    @Test
    public void testDeleteById() {
        int delete = this.userMapper.deleteById(1591507866319380483L);

        System.out.println("delete = " + delete);
    }

    @Test
    public void testDeleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_name", "caocao");
        map.put("password", "123456");

        //根据map删除数据 多条件时and关系
        int result = this.userMapper.deleteByMap(map);
        System.out.println("result = " + result);
    }

    @Test
    public void testDelete() {

//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        QueryWrapper<User> eq = wrapper.eq("user_name", "caocao").eq("password", "123456"); //用法一
        //根据条件包装删除
//        int delete = this.userMapper.delete(eq);
//        System.out.println("delete = " + delete);

        User user = new User();
        user.setPassword("123456");
        user.setUserName("caocao");

        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        int delete = this.userMapper.delete(wrapper);
        System.out.println("delete = " + delete);
    }

    //查询
    @Test
    public void testSelectBatchIds() {
        int result = this.userMapper.deleteBatchIds(Arrays.asList(1591507866319380491L, 1591507866319380492L, 1591507866319380493L));
        System.out.println("result = " + result);
    }

    @Test
    public void testSelectOne() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        QueryWrapper<User> eq = wrapper.eq("user_name", "lisi");

        //但结果超出异常会报错
        User user = this.userMapper.selectOne(eq);

        System.out.println(user);
    }

    @Test
    public void testSelectCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.gt("age", 20);//岁数大于20

        Integer selectCount = this.userMapper.selectCount(wrapper);
        System.out.println("selectCount = " + selectCount);
    }

    @Test
    public void testSelectList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //设置查询条件
        wrapper.like("user_name", "caocao");

        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }

    //分页查询
    @Test
    public void testSelectPage() {
        Page<User> page = new Page<>(1, 1);//查询第一页第一条数据

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("user_name", "caocao");
        IPage<User> iPage = this.userMapper.selectPage(page, wrapper);
        System.out.println("iPage.getTotal() = " + iPage.getTotal());
        System.out.println("iPage.getPages() = " + iPage.getPages());

        List<User> records = iPage.getRecords();
        for (User record : records) {
            System.out.println("record = " + record);
        }
    }

    //自定义的方法
    @Test
    public void testFindById() {
        User user = this.userMapper.findById(1L);
        System.out.println("user = " + user);
    }

    //allEq
    @Test
    public void testAllEq() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "曹操");
        params.put("age", "20");
        params.put("password", null);


        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.allEq(params);
//        wrapper.allEq(params,false);
        wrapper.allEq((k, v) -> (k.equals("age") || k.equals("id")||k.equals("name")), params);

        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }

    @Test
    public void testEq() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//SELECT id,user_name,password,name,age,email FROM tb_user WHERE password = ? AND age >= ? AND name IN (?,?,?)
        wrapper.eq("password", "123456")
                .ge("age", 20)
                .in("name", "李四", "王五", "赵六");
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testLike() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //SELECT id,user_name,password,name,age,email FROM tb_user WHERE name LIKE ?
        //Parameters: %曹%(String)
        wrapper.like("name", "曹");
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    //排序
    @Test
    public void testOrder() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //SELECT id,user_name,password,name,age,email FROM tb_user ORDER BY age DESC
        wrapper.orderByDesc("age");
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    //or
    @Test
    public void testOr() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //SELECT id,user_name,password,name,age,email FROM tb_user WHERE name = ? OR age = ?
        wrapper.eq("name","李四").or().eq("age", 24);
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    //指定查询字段
    @Test
    public void testSelect() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //SELECT id,name,age FROM tb_user WHERE name = ? OR age = ?
        wrapper.eq("name","李四").or().eq("age", 24).select("id","name","age");
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }
}
