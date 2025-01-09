package com.checkinn.checkinn.ServiceTests;

import com.checkinn.checkinn.Entities.User;
import com.checkinn.checkinn.Repositories.UserRepository;
import com.checkinn.checkinn.Services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    public void register_user_successfully() {
        User user = new User();

        user.setEmail("testuer@mail.com");
        user.setPassword("Password");
        user.setFirstName("Test");
        user.setLastName("User");

        authService.registerUser(user);
        assert(userRepository.findByEmail("testuer@mail.com").isPresent());
    }

    @Test
    public void register_user_with_existing_email() {


    }

    @Test
    public void login_user_successfully() {

    }

    @Test
    public void login_user_with_non_existing_email() {

    }

    @Test
    public void login_user_with_wrong_password() {

    }

}
