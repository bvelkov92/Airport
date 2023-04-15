package com.my.airportproject.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewMyPlane {

   private String  planeNumber;

   public ViewMyPlane(String planeNumber) {
      this.planeNumber=planeNumber;
   }
}
