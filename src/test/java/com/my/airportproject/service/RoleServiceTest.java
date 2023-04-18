package com.my.airportproject.service;

import com.my.airportproject.model.dto.roles.ChangeRoleDto;
import com.my.airportproject.model.entity.Role;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.repository.RoleRepository;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    private final EnumRoles ENUM_ROLE_ADMIN =EnumRoles.ADMIN;

    private final String COMPANY_NAME = "Test Company";

    private final String USERNAME = "testUsername";

    private final String PASSWORD = "password";

    private final String EMAIL = "email@email.bg";


    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Mock
    private RoleRepository mockRoleRepository;

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private RoleService mockRoleService;

    @BeforeEach
    void setUp(){
        mockRoleService = new RoleService(
                mockRoleRepository,
                mockUserRepository
        );
    }

    @Test
void testSetRoleAdmin(){
    Role roleAdmin = new Role(EnumRoles.ADMIN);
    User testUser = new User();
    testUser.setCompanyName(COMPANY_NAME);
    testUser.setUsername(USERNAME);
    testUser.setPassword(PASSWORD);
    testUser.setEmail(EMAIL);

    when(this.mockUserRepository.count()).thenReturn(1L);
    when(this.mockUserRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));
    this.mockRoleService.setRole(EMAIL);

    verify(mockUserRepository).save(userArgumentCaptor.capture());
    User savedRole = userArgumentCaptor.getValue();
    List<Role> roleList = List.of(roleAdmin);
    Assertions.assertEquals(testUser.getRoles().get(0), savedRole.getRoles().get(0));
    Assertions.assertEquals(roleList.size(),testUser.getRoles().size());
}

    @Test
    void testSetRoleUser(){
        Role roleUser =new Role(EnumRoles.USER);
        User testUser = new User();
        testUser.setCompanyName(COMPANY_NAME);
        testUser.setUsername(USERNAME);
        testUser.setPassword(PASSWORD);
        testUser.setEmail(EMAIL);

        when(this.mockUserRepository.count()).thenReturn(2L);
        when(this.mockUserRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));
        when(this.mockRoleRepository.findRoleByName(EnumRoles.USER)).thenReturn(Optional.of(roleUser));

        this.mockRoleService.setRole(EMAIL);

        Assertions.assertEquals(testUser.getRoles().get(0), roleUser);

  }

  @Test
    void  testSetNewRoleOnUser(){
      Role oldRoleAdmin = new Role(ENUM_ROLE_ADMIN);
      Role newRoleFirm = new Role(EnumRoles.FIRM);


      User testUser = new User();
      testUser.setCompanyName(COMPANY_NAME);
      testUser.setUsername(USERNAME);
      testUser.setPassword(PASSWORD);
      testUser.setEmail(EMAIL);
      testUser.setRoles(List.of(oldRoleAdmin));


      ChangeRoleDto testChangeRoleDtoValid = new ChangeRoleDto(
              EMAIL,
              EnumRoles.FIRM
      );

      ChangeRoleDto testChangeRoleDtoNotFoundUser = new ChangeRoleDto(
              "test@test.bg",
              ENUM_ROLE_ADMIN
      );

      ChangeRoleDto testChangeRoleDtoSameRole = new ChangeRoleDto(
              EMAIL,
              EnumRoles.ADMIN
      );

      List<Role> roleList = new ArrayList<>();

      when(this.mockUserRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));
      when(this.mockRoleRepository.findRoleByName(testChangeRoleDtoValid.getRole())).thenReturn(Optional.of(newRoleFirm));


      boolean valid = this.mockRoleService.setNewRoleOnUser(testChangeRoleDtoValid);
      boolean invalid = this.mockRoleService.setNewRoleOnUser(testChangeRoleDtoNotFoundUser);
      boolean invalid2 = this.mockRoleService.setNewRoleOnUser(testChangeRoleDtoSameRole);

      Assertions.assertTrue(valid);
      Assertions.assertFalse(invalid);
      Assertions.assertFalse(invalid2);
      Assertions.assertEquals(newRoleFirm,testUser.getRoles().get(0));
  }

}