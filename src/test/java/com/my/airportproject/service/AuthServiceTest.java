package com.my.airportproject.service;

import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.dto.user.AdminChangeSomeUsernameDto;
import com.my.airportproject.model.dto.user.ChangeMyUsernameDto;
import com.my.airportproject.model.dto.user.UserRegisterDto;
import com.my.airportproject.model.entity.Role;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AuthServiceTest {
    private final String EXIST_COMPANY_NAME = "Company";
    private final String EXIST_USERNAME = "user";
    private final String EXIST_EMAIL = "email@email.com";
    private final String EXIST_PASSWORD = "password";
    private final Role role = new Role(EnumRoles.ADMIN);
    private final String NOT_EXIST_USERNAME = "NotExist";
    private final  String INVALID_EMAIL= "test@email.bg";
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleService mockRoleServiceTest;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @InjectMocks
    private AuthService authServiceToTest;


    @BeforeEach
    void setUp() {
        authServiceToTest = new AuthService(
                mockUserRepository,
                mockPasswordEncoder,
                mockRoleServiceTest
        );
    }

    @Test
    void testUserRegister_SaveInvoke() {
        //ARRANGE
        UserRegisterDto testUserRegister = new UserRegisterDto();
        testUserRegister.setCompanyName(EXIST_COMPANY_NAME);
        testUserRegister.setUsername(EXIST_USERNAME);
        testUserRegister.setEmail(EXIST_EMAIL);
        testUserRegister.setPassword(EXIST_PASSWORD);
        testUserRegister.setConfirmPassword(EXIST_PASSWORD);

        String ENCODED_PASSWORD = "CryptedPassword";
        when(mockPasswordEncoder.encode(testUserRegister.getPassword())).thenReturn(ENCODED_PASSWORD);

        //ACT
        authServiceToTest.register(testUserRegister);

        //ASSERT

        verify(mockUserRepository).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();
        assertEquals(testUserRegister.getUsername(), savedUser.getUsername());
        assertEquals(ENCODED_PASSWORD, savedUser.getPassword());
    }

    @Test
    void testGetUserByUsername() {
        assertNull(authServiceToTest.getUserByUsername(NOT_EXIST_USERNAME));
    }

    @Test
    void testGetAllUsers() {
        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(role));

        User testUser2 = new User();
        testUser2.setUsername(EXIST_USERNAME);
        testUser2.setEmail(EXIST_EMAIL);
        testUser2.setPassword(EXIST_PASSWORD);
        testUser2.setCompanyName(EXIST_COMPANY_NAME);
        testUser2.setRoles(List.of(role));

        when(mockUserRepository.findAll()).thenReturn(List.of(testUser,testUser2));
        User fromRepository = authServiceToTest.getAllUsers().get(1);
        int fromRepoSize = authServiceToTest.getAllUsers().size();

        assertEquals(testUser2,fromRepository);
        assertEquals(2, fromRepoSize );
    }
    @Test
    void testGetAllUsers_Repo_Is_Empty() {

        when(mockUserRepository.findAll()).thenReturn(List.of());

        int emptyRepo = authServiceToTest.getAllUsers().size();
        assertEquals(0, emptyRepo);
    }

    @Test
    void testGetByEmail(){
        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(role));

        when(mockUserRepository.findByEmail("email@email.com")).thenReturn(Optional.of(testUser));

        User user = authServiceToTest.getByEmail(EXIST_EMAIL);

        assertEquals(EXIST_EMAIL,user.getEmail());
        assertEquals(EXIST_USERNAME,user.getUsername());
        assertEquals(EXIST_COMPANY_NAME,user.getCompanyName());
        Assertions.assertNull(authServiceToTest.getByEmail(INVALID_EMAIL));
    }

    @Test
    void testIsValidCheckCompany(){
        String valid = "Company";
        String invalid = "";
        boolean resultIsValid = this.authServiceToTest.checkCompany(valid);
        boolean resultIsInvalid = this.authServiceToTest.checkCompany(invalid);
        Assertions.assertTrue(resultIsValid);
        Assertions.assertFalse(resultIsInvalid);

    }

    @Test
    void testChangeRole(){
        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(role));


        when(this.mockUserRepository.findByEmail(EXIST_EMAIL)).thenReturn(Optional.of(testUser));
        ChangeRoleDto validResult = new ChangeRoleDto(EXIST_EMAIL, EnumRoles.USER);
        ChangeRoleDto invalidResult = new ChangeRoleDto(INVALID_EMAIL, EnumRoles.ADMIN);

        boolean valid = this.authServiceToTest.changeRole(validResult);
        boolean invalid = this.authServiceToTest.changeRole(invalidResult);
        Assertions.assertTrue(valid);
        Assertions.assertFalse(invalid);
    }

    @Test
    void  testDeleteRow(){
        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(role));
        Long VALID_ID = 1L;
        Long INVALID_ID = 2L;
        when(this.mockUserRepository.findById(1L)).thenReturn(Optional.of(testUser));


        boolean validResult = this.authServiceToTest.deleteRow(VALID_ID);
        boolean invalidResult = this.authServiceToTest.deleteRow(INVALID_ID);

        Assertions.assertTrue(validResult);
        Assertions.assertFalse(invalidResult);
    }


    @Test
    void testChangeSomeUsername(){
        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(role));
        when(this.mockUserRepository.findByUsername(EXIST_USERNAME)).thenReturn(Optional.of(testUser));

        AdminChangeSomeUsernameDto validDto = new AdminChangeSomeUsernameDto(
                EXIST_USERNAME,
                "Pesho"
        );
        AdminChangeSomeUsernameDto invalidDto = new AdminChangeSomeUsernameDto(
                NOT_EXIST_USERNAME,
                EXIST_USERNAME
        );

        boolean validResult = this.authServiceToTest.changeSomeUsername(validDto);
        boolean invalidResult = this.authServiceToTest.changeSomeUsername(invalidDto);

        Assertions.assertTrue(validResult);
        Assertions.assertFalse(invalidResult);
    }

    @Test
    void testChangeMyUsername(){
        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(role));
        when(this.mockUserRepository.findByEmail(EXIST_EMAIL)).thenReturn(Optional.of(testUser));

        ChangeMyUsernameDto validDto = new ChangeMyUsernameDto(
                EXIST_USERNAME,
                "Pesho"
        );
        ChangeMyUsernameDto invalidDto = new ChangeMyUsernameDto(
                NOT_EXIST_USERNAME,
                EXIST_USERNAME
        );

        User validResult = this.authServiceToTest.changeMyUsername(EXIST_EMAIL,validDto);
        User invalidResult = this.authServiceToTest.changeMyUsername(INVALID_EMAIL,invalidDto);

        Assertions.assertEquals(testUser,validResult);
        Assertions.assertNull(invalidResult);
    }



}
