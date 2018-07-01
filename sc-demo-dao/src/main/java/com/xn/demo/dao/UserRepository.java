package com.xn.demo.dao;

import com.xn.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Janle qq:645905201
 * @date 2018/5/30 20:23
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
