package pl.java.kamil.dao.user.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import pl.java.kamil.dao.user.UserDao;
import pl.java.kamil.entity.User;

/**
 * @author Sławomir Borowiec 
 * Module name : jdbcEngine
 * Creating time :  11 mar 2014 14:51:56
 
 */
@Repository(value="userDao")
public class UserDaoImpl implements UserDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        super();
        this.insertUser = new SimpleJdbcInsert(dataSource).withTableName("users").usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public User findUserByName(String name) {
        final List<User> users = this.jdbcTemplate.query("SELECT id, username FROM users WHERE username = ?", new ParameterizedRowMapper<User>() {

            public User mapRow(ResultSet rs, int row) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUserName(rs.getString("namename"));

                return user;
            }
        }, name);
        if (!users.isEmpty()) { return users.get(0); }

        return null;
    }

    private MapSqlParameterSource createTagParameterSource(User user) {
        return new MapSqlParameterSource().addValue("id", user.getId()).addValue("username", user.getUserName());

    }

    public long saveOrUpdate(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.getId() == null) {
            Number newKey = this.insertUser.executeAndReturnKey(createTagParameterSource(user));
            user.setId(new Long(newKey.intValue()));
            return user.getId();
        }
        return new Long(this.namedParameterJdbcTemplate.update("UPDATE user SET username=:username  WHERE id=:id", parameterSource));
    }

    public int remove(User user) {
        return this.jdbcTemplate.update("DELETE FROM users WHERE id=?", user.getId());
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        users.addAll(this.jdbcTemplate.query("SELECT id, username FROM users ORDER BY username", ParameterizedBeanPropertyRowMapper.newInstance(User.class)));
        return users;
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM users";
        int count = jdbcTemplate.queryForInt(sql);
        return count;
    }

    public User getUserById(Long id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        User user = this.namedParameterJdbcTemplate.queryForObject(
                "SELECT id, login, firstName,lastName,password,active,email,sex,birthDate,photoFROM User WHERE id=:id", params,
                new ParameterizedRowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setUserName(rs.getString("username"));
                        return user;
                    }

                });
        return user;
    }

    public User findByUsername(String username) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        User user = this.namedParameterJdbcTemplate.queryForObject("SELECT id, username FROM User WHERE username=:username", params,
                new ParameterizedRowMapper<User>() {
   
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setUserName(rs.getString("username"));

                        return user;
                    }

                });
        return user;
    }

    public List<User> findUserByNameLike(String namePattern) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", namePattern + "%");
        List<User> users = this.namedParameterJdbcTemplate.query("SELECT id, username FROM User WHERE username like :username", params,
                ParameterizedBeanPropertyRowMapper.newInstance(User.class));
        return users;
    }

}
