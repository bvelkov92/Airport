package com.my.airportproject.service;

import com.my.airportproject.model.dto.tickedDto.AddTickedDto;
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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    private final String EXIST_COMPANY_NAME = "Company";
    private final String EXIST_USERNAME = "user";
    private final String EXIST_EMAIL = "email@email.com";
    private final String EXIST_PASSWORD = "password";
    private final Role role = new Role(EnumRoles.ADMIN);


    private final String PLANE_NUMBER= "Test Plane Number";
    private final String FLIGHT_FROM = "Sofia";
    private final String FLIGHT_TO = "Burgas";
    private final Double TICKET_PRICE = 50.00;
    private final String DATE_TIME ="2023-04-16T19:33";


    LocalDateTime dateTime = LocalDateTime.parse(DATE_TIME, DateTimeFormatter.ISO_LOCAL_DATE_TIME);


    @Mock
    private TicketRepository mockTicketRepository;

    @Spy
    private ModelMapper mockModelMapper;

    @Mock
    private FlightRepository mockFlightRepository;

    @Captor
    private ArgumentCaptor<Ticket> ticketArgumentCaptor;

    @Mock
    private PlaneRepository mockPlaneRepository;

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private TicketService ticketServiceTest;

    @BeforeEach
    void setUp(){
        ticketServiceTest = new TicketService (
                mockTicketRepository,
                mockModelMapper,
                mockFlightRepository,
                mockPlaneRepository,
                mockUserRepository
        );
    }

    @Test
    void testBuyTicket(){
        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(role));

        Plane testPlane = new Plane();
        testPlane.setPlaneOwnerFirm(testUser);
        testPlane.setPlaneNumber(PLANE_NUMBER);

        Flight testFlight = new Flight();
        testFlight.setFlightFrom(FLIGHT_FROM);
        testFlight.setFlightTo(FLIGHT_TO);
        testFlight.setTimeOfFlight(dateTime);
        testFlight.setTicketPrice(TICKET_PRICE);
        testFlight.setPlaneNumber(testPlane);
        testFlight.setFirmOwner(testUser);

        Ticket testTicket = new Ticket(
                testUser,
                testFlight
        );

        Long id = 1L;
        Principal principal = testUser::getEmail;

        String email = principal.getName();

        AddTickedDto testTicketDto = new AddTickedDto(
                testFlight,
                testUser
        );

        when(this.mockUserRepository.findByEmail(email)).thenReturn(Optional.of(testUser));
        when(this.mockFlightRepository.findById(id)).thenReturn(Optional.of(testFlight));

        this.ticketServiceTest.buyTicket(id,email);

      verify(this.mockTicketRepository).save(ticketArgumentCaptor.capture());
        Ticket saved = this.ticketArgumentCaptor.getValue();
        Assertions.assertEquals(saved.getFlight(),testTicketDto.getFlight());
        Assertions.assertEquals(saved.getUser(),testTicket.getUser());
    }

    @Test
    void testDeleteRow(){
        User testUser = new User();
        testUser.setUsername(EXIST_USERNAME);
        testUser.setEmail(EXIST_EMAIL);
        testUser.setPassword(EXIST_PASSWORD);
        testUser.setCompanyName(EXIST_COMPANY_NAME);
        testUser.setRoles(List.of(role));

        Plane testPlane = new Plane();
        testPlane.setPlaneOwnerFirm(testUser);
        testPlane.setPlaneNumber(PLANE_NUMBER);

        Flight testFlight = new Flight();
        testFlight.setFlightFrom(FLIGHT_FROM);
        testFlight.setFlightTo(FLIGHT_TO);
        testFlight.setTimeOfFlight(dateTime);
        testFlight.setTicketPrice(TICKET_PRICE);
        testFlight.setPlaneNumber(testPlane);
        testFlight.setFirmOwner(testUser);

        Ticket testTicket = new Ticket(
                testUser,
                testFlight
        );

        Long id = 1L;

        when(this.mockTicketRepository.findById(id)).thenReturn(Optional.of(testTicket));

        boolean validResult = this.ticketServiceTest.deleteRow(id);
        boolean invalidResult = this.ticketServiceTest.deleteRow(2L);


        Assertions.assertTrue(validResult);
        Assertions.assertFalse(invalidResult);
    }

}
