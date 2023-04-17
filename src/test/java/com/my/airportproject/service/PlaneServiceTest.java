package com.my.airportproject.service;

import com.my.airportproject.model.dto.planes.PlaneDto;
import com.my.airportproject.model.entity.Plane;
import com.my.airportproject.model.entity.Role;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.repository.PlaneRepository;
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
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PlaneServiceTest {

    private final String EXIST_COMPANY_NAME = "Company";
    private final String EXIST_USERNAME = "user";
    private final String EXIST_EMAIL = "email@email.com";
    private final String EXIST_PASSWORD = "password";
    private final Role role = new Role(EnumRoles.ADMIN);
    private final String PLANE_NUMBER = "Plane Number";
    private final  String INVALID_EMAIL= "test@email.bg";

    @Captor
    private ArgumentCaptor<Plane> planeArgumentCaptor;

    @Mock
    private PlaneRepository mockPlaneRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private PlaneService mockPlaneService;

    @BeforeEach
    void setUp(){
        mockPlaneService = new PlaneService(
                mockPlaneRepository,
                mockUserRepository,
                mockModelMapper
        );
    }

    @Test
    void  testAddPlane(){

        String loggedUser = EXIST_EMAIL;

        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(role));

        Plane testPlane = new Plane();
        testPlane.setPlaneOwnerFirm(testUser);
        testPlane.setPlaneNumber(PLANE_NUMBER);

        PlaneDto ExistingPlane = new PlaneDto();
        ExistingPlane.setPlaneNumber(PLANE_NUMBER);

        PlaneDto unexistingdPlane = new PlaneDto();
            unexistingdPlane.setPlaneNumber("Not Existing Plane NUmber");


        when(this.mockUserRepository.findByEmail(EXIST_EMAIL)).thenReturn(Optional.of(testUser));
        when(this.mockPlaneRepository.findByPlaneNumber(ExistingPlane.getPlaneNumber())).thenReturn(Optional.of(testPlane));

        boolean invalidResult = this.mockPlaneService.addPlane(loggedUser, ExistingPlane);
        boolean validResult = this.mockPlaneService.addPlane(loggedUser, unexistingdPlane);

        Assertions.assertTrue(validResult);
        Assertions.assertFalse(invalidResult);
    }

        @Test
        void testIsFoundedAndNotFoundedPlane(){

            User testUser = new User();
            testUser.setUsername(EXIST_USERNAME);
            testUser.setEmail(EXIST_EMAIL);
            testUser.setPassword(EXIST_PASSWORD);
            testUser.setCompanyName(EXIST_COMPANY_NAME);
            testUser.setRoles(List.of(role));

            Plane testPlane = new Plane();
            testPlane.setPlaneOwnerFirm(testUser);
            testPlane.setPlaneNumber(PLANE_NUMBER);

            when(this.mockPlaneRepository.findByPlaneNumber(PLANE_NUMBER)).thenReturn(Optional.of(testPlane));

            boolean validIsFounded = this.mockPlaneService.isFoundedPlane(PLANE_NUMBER);
            boolean invalidIsFounded = this.mockPlaneService.isFoundedPlane("UNEXISTING PLANE");

            boolean invalidIsNotFounded = this.mockPlaneService.isNotFounded(PLANE_NUMBER);
            boolean validIsNotFounded = this.mockPlaneService.isNotFounded("UNEXISTING PLANE");

            //IsFound Method
            Assertions.assertTrue(validIsFounded);
            Assertions.assertFalse(invalidIsFounded);

            //Is Not Found Method
            Assertions.assertTrue(validIsNotFounded);
            Assertions.assertFalse(invalidIsNotFounded);

    }

    @Test
    void testGetMyPlanes(){

        String user = EXIST_EMAIL;

        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(role));

        Plane testPlane = new Plane();
        testPlane.setPlaneOwnerFirm(testUser);
        testPlane.setPlaneNumber(PLANE_NUMBER);

        List<Plane> testPlaneList = List.of(testPlane);

        when(this.mockUserRepository.findByEmail(user))
                .thenReturn(Optional.of(testUser));

        when(this.mockPlaneRepository.findAllByPlaneOwnerFirm(testUser)).thenReturn(testPlaneList);
        List<Plane> myPlanes = this.mockPlaneService.getMyPlanes(user);

        Assertions.assertEquals(testPlaneList,myPlanes);
        Assertions.assertEquals(1,myPlanes.size());

    }


}
