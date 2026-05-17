package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceFollowedByTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @ParameterizedTest
    @CsvSource({
            "ram",
            "shyam",
            "vipul"
    })
//    @ArgumentsSource(UserArgumentProvider.class)
    public void testSaveNewUser(String name){
            assertNotNull(userRepository.findByUserName(name),"failed for "+name);
    }
    @Disabled
    @ParameterizedTest
    @CsvSource({
        "1,2,3",
            "2,3,5",
            "3,2,5"
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
