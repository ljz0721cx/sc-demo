package com.xn.demo.controller;

import com.xn.demo.dao.UserRepository;
import com.xn.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * @author Janle qq:645905201
 * @date 2018/5/30 20:24
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return new User();
    }

    @GetMapping("")
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @GetMapping("/add")
    public User add() {
        User user = new User();
        user.setAge(new Random().nextInt(20));
        user.setBlance(new BigDecimal(new Random().nextDouble()));
        user.setName(genString(3));
        user.setUsername(genString(7));
        userRepository.save(user);
        return user;
    }

    private String genString(int n) {
        String[] strings = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "g", "k"};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append(strings[new Random().nextInt(strings.length)]);
        }
        return sb.toString();
    }


}
