package com.my.airportproject.model.dto.flights;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddFlightDto {

    @NotNull
    private String flightFrom;
    @NotNull
    private String flightTo;
    @NotNull
    private Double price;
    @NotNull
    private String planeNumber;
    @NotNull
    private String time;


}
