package demo.dao;

import demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/30 20:23
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
