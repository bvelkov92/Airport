package com.my.airportproject.service;

import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.model.entity.*;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.repository.FlightRepository;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.TicketRepository;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    private final String EXIST_COMPANY_NAME = "Company";
    private final String EXIST_USERNAME = "user";
    private final String EXIST_EMAIL = "email@email.com";
    private final String EXIST_PASSWORD = "password";
    private final String PLANE_NUMBER= "Test Plane Number";
    private final String FLIGHT_FROM = "Sofia";
    private final String FLIGHT_TO = "Burgas";
    private final Double TICKET_PRICE = 50.00;
    private final String DATE_TIME ="2023-04-16T19:33";
    private final Role ROLE_ADMIN = new Role(EnumRoles.ADMIN);
    private final Role ROLE_FIRM = new Role(EnumRoles.FIRM);
    private final Role ROLE_USER = new Role(EnumRoles.USER);

    @Mock
    private FlightRepository mockFlightRepository;
    @Mock
    private  ModelMapper mockModelMapper;
    @Mock
    private  PlaneRepository mockPlaneRepository;
    @Mock
    private  UserRepository mockUserRepository;

    @Captor
    private ArgumentCaptor<Flight> flightArgumentCaptor;
    @Mock
    private  AuthService mockAuthService;

    @Mock
    private TicketRepository mockTicketRepository;

    @InjectMocks
    private FlightService mockFlightService;

    @BeforeEach
    void setUp() {
        mockFlightService = new FlightService(
                mockFlightRepository,
                mockModelMapper,
                mockPlaneRepository,
                mockUserRepository,
                mockAuthService,
                mockTicketRepository
        );
    }

    @Test
    void testAddFlight(){

        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(ROLE_ADMIN));

       LocalDateTime dateTime = LocalDateTime.parse(DATE_TIME, DateTimeFormatter.ISO_LOCAL_DATE_TIME);


        Plane testPlane = new Plane();
        testPlane.setPlaneOwnerFirm(testUser);
        testPlane.setPlaneNumber(PLANE_NUMBER);

        AddFlightDto dto = new AddFlightDto();
        dto.setTime(DATE_TIME);
        dto.setFlightFrom(FLIGHT_FROM);
        dto.setFlightTo(FLIGHT_TO);
        dto.setPrice(TICKET_PRICE);
        dto.setPlaneNumber(testPlane.getPlaneNumber());

        Flight testFlight = new Flight(
                dto.getFlightFrom(),
                dto.getFlightTo(),
                dto.getPrice(),
                dateTime,
                testPlane
        );

        testFlight.setFirmOwner(testUser);

        when(this.mockPlaneRepository.findByPlaneNumber(PLANE_NUMBER)).thenReturn(Optional.of(testPlane));
        this.mockFlightService.addFlight(dto);

        verify(mockFlightRepository).save(flightArgumentCaptor.capture());
        Flight savedFlight = flightArgumentCaptor.getValue();
        assertEquals(dto.getFlightFrom(), savedFlight.getFlightFrom());
        assertEquals(dto.getFlightTo(), savedFlight.getFlightTo());
        assertEquals(DATE_TIME, savedFlight.getTimeOfFlight().toString());
        assertEquals(testPlane, savedFlight.getPlaneNumber());
    }

    @Test
    void testFindFlight(){
        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(ROLE_ADMIN));

        LocalDateTime dateTime = LocalDateTime.parse(DATE_TIME, DateTimeFormatter.ISO_LOCAL_DATE_TIME);


        Plane testPlane = new Plane();
        testPlane.setPlaneOwnerFirm(testUser);
        testPlane.setPlaneNumber(PLANE_NUMBER);

        AddFlightDto validDto = new AddFlightDto();
        validDto.setTime(DATE_TIME);
        validDto.setFlightFrom(FLIGHT_FROM);
        validDto.setFlightTo(FLIGHT_TO);
        validDto.setPrice(TICKET_PRICE);
        validDto.setPlaneNumber(testPlane.getPlaneNumber());

        AddFlightDto invalidDto = new AddFlightDto();
        invalidDto.setTime(DATE_TIME);
        invalidDto.setFlightFrom(FLIGHT_TO);
        invalidDto.setFlightTo(FLIGHT_FROM);
        invalidDto.setPrice(TICKET_PRICE);
        invalidDto.setPlaneNumber(testPlane.getPlaneNumber());

        Flight testFlight = new Flight();
                testFlight.setFlightFrom(FLIGHT_FROM);
                testFlight.setFlightTo(FLIGHT_TO);
                testFlight.setTimeOfFlight(dateTime);
                testFlight.setTicketPrice(TICKET_PRICE);
                testFlight.setPlaneNumber(testPlane);
                testFlight.setFirmOwner(testUser);

        when(this.mockFlightRepository
                .findByFlightFromAndFlightToAndTimeOfFlightAndPlaneNumber_PlaneNumber(
                        FLIGHT_FROM,
                        FLIGHT_TO,
                        dateTime,
                        PLANE_NUMBER
                )).thenReturn(Optional.of(testFlight));

        Flight validflight = this.mockFlightService.findFlight(validDto);
        Flight invalidFlight = this.mockFlightService.findFlight(invalidDto);

        Assertions.assertEquals(validflight, testFlight);
        Assertions.assertNull(invalidFlight);
    }

    @Test
    void testCheckTimeDifference(){

        String invalidTime = LocalDateTime.now().minusDays(5).toString();
        String validTime = LocalDateTime.now().plusDays(2).toString();
        boolean validResult = this.mockFlightService.checkTimeDifference(validTime);
        boolean invalidResult = this.mockFlightService.checkTimeDifference(invalidTime);

        Assertions.assertTrue(validResult);
        Assertions.assertFalse(invalidResult);
    }

    @Test
    void testGetMyTickets(){
        LocalDateTime dateTime = LocalDateTime.parse(DATE_TIME, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        User buyer = new User();
        buyer.setUsername(EXIST_USERNAME);
        buyer.setEmail(EXIST_EMAIL);
        buyer.setPassword(EXIST_PASSWORD);
        buyer.setCompanyName(EXIST_COMPANY_NAME);
        buyer.setRoles(List.of(ROLE_USER));

        User flightPoster = new User();
        flightPoster.setUsername("Pesho");
        flightPoster.setEmail("pesho@pesho.bg");
        flightPoster.setPassword("topSecret");
        flightPoster.setCompanyName("Test Company");
        flightPoster.setRoles(List.of(ROLE_FIRM));

        Plane testPlane = new Plane();
        testPlane.setPlaneOwnerFirm(flightPoster);
        testPlane.setPlaneNumber(PLANE_NUMBER);


        Flight testFlight = new Flight();
        testFlight.setFlightFrom(FLIGHT_FROM);
        testFlight.setFlightTo(FLIGHT_TO);
        testFlight.setTimeOfFlight(dateTime);
        testFlight.setTicketPrice(TICKET_PRICE);
        testFlight.setPlaneNumber(testPlane);
        testFlight.setFirmOwner(flightPoster);

        Flight testFlight2 = new Flight();
        testFlight.setFlightFrom(FLIGHT_FROM);
        testFlight.setFlightTo(FLIGHT_TO);
        testFlight.setTimeOfFlight(dateTime);
        testFlight.setTicketPrice(TICKET_PRICE);
        testFlight.setPlaneNumber(testPlane);
        testFlight.setFirmOwner(flightPoster);

        Ticket ticket = new Ticket(buyer,testFlight2);
        List <Ticket> ticketList =List.of(ticket);

        when(this.mockUserRepository.findByEmail(EXIST_EMAIL)).thenReturn(Optional.of(buyer));
        when(this.mockTicketRepository.findAllByUser_Email(buyer.getEmail())).thenReturn(List.of(ticket));

        List<Ticket> result = this.mockFlightService.getMyTickets(EXIST_EMAIL);

        Assertions.assertEquals(ticketList,result);
        Assertions.assertEquals(1,result.size());

    }

}
