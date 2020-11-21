package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressModel {
    String addressLine;
    String city;
    String country;
    String pOBox;
    String street;
    String community;
    String numberOfBuilding;

    public String toString(){
        return addressLine+" "+city+" "+country;
    }
}
