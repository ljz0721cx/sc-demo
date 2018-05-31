package demo.controller;

import com.xn.demo.dao.UserRepository;
import com.xn.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/30 20:24
 */
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return new User();
    }
}
