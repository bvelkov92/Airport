package com.my.airportproject.service;

import com.my.airportproject.model.dto.flights.AddFlightDto;
import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Plane;
import com.my.airportproject.model.entity.Role;
import com.my.airportproject.model.entity.User;
import com.my.airportproject.model.enums.EnumRoles;
import com.my.airportproject.repository.FlightRepository;
import com.my.airportproject.repository.PlaneRepository;
import com.my.airportproject.repository.TicketRepository;
import com.my.airportproject.repository.UserRepository;
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
    private final Role role = new Role(EnumRoles.ADMIN);

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
        testUser.setRoles(List.of(role));

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
        when(this.mockModelMapper.map(dto, Flight.class)).thenReturn(testFlight);
        Optional<Plane> testFoundPlane = this.mockPlaneRepository.findByPlaneNumber(dto.getPlaneNumber());

        this.mockFlightService.addFlight(dto);

        verify(mockFlightRepository).save(flightArgumentCaptor.capture());
        Flight savedFlight = flightArgumentCaptor.getValue();
        assertEquals(dto.getFlightFrom(), savedFlight.getFlightFrom());
        assertEquals(dto.getFlightTo(), savedFlight.getFlightTo());
        assertEquals(DATE_TIME, savedFlight.getTimeOfFlight().toString());
        assertEquals(testPlane, savedFlight.getPlaneNumber());

    }

}
