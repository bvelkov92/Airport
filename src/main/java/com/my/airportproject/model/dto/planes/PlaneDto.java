package com.my.airportproject.model.dto.planes;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PlaneDto {
 @NotNull
 @Size(min = 1)
 private String planeNumber;

}
