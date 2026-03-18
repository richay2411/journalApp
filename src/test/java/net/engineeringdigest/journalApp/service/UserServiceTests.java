package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @Test
    public void testAdd(){
        assertEquals(4, 2+2);
    }
    @Disabled
    @ParameterizedTest
//    @ValueSource(strings = {
//            "ram",
//            "shyam",
//            "richa"
//    })
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user){
        assertTrue(userService.saveNewUser(user));//        assertEquals(4, 2+2);

//        assertNotNull(userRepository.findByUserName(name), "failed for :  " + name);
//       User user =  user Repository.findByUserName("ram");
//        assertTrue(!user.getJournalEntries().isEmpty());
//        assertTrue( 5 > 3);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1, 1, 2",
            "2, 10, 12",
            "3, 3, 9"
    })
     void test(int a, int b, int expected){
        assertEquals(expected, a + b);
    }
}
