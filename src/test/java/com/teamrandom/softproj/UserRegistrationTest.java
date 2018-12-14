package com.teamrandom.softproj;

import com.teamrandom.softproj.businessObject.User;
import com.teamrandom.softproj.repository.RoleRepository;
import com.teamrandom.softproj.repository.UserRepository;
import com.teamrandom.softproj.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserRegistrationTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private UserService userServiceUnderTest;
    private User user;

    @Before
    public void setUp() {
        initMocks(this);
        userServiceUnderTest = new UserService(mockUserRepository,
                mockRoleRepository,
                mockBCryptPasswordEncoder);
        user = User.builder()
                .id(1)
                .name("Test")
                .username("testing")
                .build();

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);
        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);
    }

    @Test
    public void testFindUserByEmail() {
        // Setup
        final String username = "testing";

        // Run the test
        final User result = userServiceUnderTest.findUserByUsername(username);

        // Verify the results
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testSaveUser() {
        // Setup
        final String username = "testing";

        // Run the test
        User result = userServiceUnderTest.saveUser(User.builder().build());

        // Verify the results
        assertEquals(username, result.getUsername());
    }
}
