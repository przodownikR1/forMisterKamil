package pl.java.kamil.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import pl.java.kamil.entity.User;

/**
 * @author Sławomir Borowiec 
 * Module name : jdbcEngine
 * Creating time :  11 mar 2014 14:52:04
 
 */
public class UserMapper implements RowMapper<User>{
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUserName(rs.getString("username"));
        return user;
    }
}
