package pl.java.kamil.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.java.kamil.dao.user.UserDao;
import pl.java.kamil.entity.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dao.xml" })
@Transactional
public class BootstrapTest {
    private static Logger logger = LoggerFactory.getLogger(BootstrapTest.class); 

    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private UserDao userDao;
    
    @Test
    public void init() {
        Assert.assertTrue(true);
    }
    
    @Test
    public void shouldBeanLoaded() {
        String [] beans = applicationContext.getBeanDefinitionNames();
        logger.info("+++      {}",Arrays.toString(beans));
        Assert.assertNotNull(jdbcTemplate);    

    }
    @Test
    public void shouldRetrieveSomeData() {
      logger.info("+++        count users  {}", userDao.countAll());   
    }
    @Test
    public void shouldRetrieveAllUser() {
        //given
        long userCount= userDao.countAll();
        //when
        List<User> users =  userDao.getUsers();
        logger.info("+++       users {}",users);
        //then
        Assert.assertEquals(userCount, users.size());
    }
    @Test
    public void shouldPersistProperly() {
        //given
        long userCount= userDao.countAll();
        User newUser = new User("bak");
        //when
        long id = userDao.saveOrUpdate(newUser);
        logger.info("+++            persistenced id {}" , id );
        long userCountAfterInsert = userDao.countAll();
        Assert.assertEquals(userCount+1,userCountAfterInsert);
    }
    @Test
    public void shouldRemoveTest() {
        //given
        long userCount= userDao.countAll();
        
        User userToRemove = new User(2l,"kamil");
        
        userDao.remove(userToRemove);
        long userCountAfterRemove= userDao.countAll();
        Assert.assertEquals(userCount-1,userCountAfterRemove);
        
    }
}
