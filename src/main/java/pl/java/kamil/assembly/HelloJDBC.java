package pl.java.kamil.assembly;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pl.java.kamil.dao.user.UserDao;
//mvn clean compile  assembly:single - zrobienie paczki 
//mvn clean - wyczyszczenie projektu
//mvn package - zbudowanie projektu 
//mvn compile - kompilacja kodu
//mvn install - wrzucenie do lokalnego repo

//http://przewidywalna-java.blogspot.com/2014/01/maven.html  - opis narzędzia na moim blogu
//oraz http://przewidywalna-java.blogspot.com/2014/02/maven-best-practices-enforce-plugin.html

/**
 * @author Sławomir Borowiec 
 * Module name : jdbcEngine
 * Creating time :  11 mar 2014 14:52:08
 
 */
public class HelloJDBC {
public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("dao.xml");  
    System.err.println(Arrays.toString(context.getBeanDefinitionNames()));
    UserDao userDao = context.getBean("userDao", UserDao.class);
    System.err.println("Hello, mister Kamil. Now is  time to show spring jdbc features  : " +userDao.countAll());
    
}
}
