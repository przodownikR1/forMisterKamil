package pl.java.kamil.dao.user;

import java.util.List;

import pl.java.kamil.entity.User;

/**
 * @author Sławomir Borowiec 
 * Module name : jdbcEngine
 * Creating time :  11 mar 2014 14:52:00
 
 */
public interface UserDao {

    User findUserByName(String name);

    List<User> findUserByNameLike(String namePattern);

    User getUserById(Long id);

    long saveOrUpdate(User user);

    int remove(User user);

    List<User> getUsers();

    int countAll();
}
