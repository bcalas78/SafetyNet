package com.openclassrooms.safetynet.model;

import lombok.Data;

@Data
public class Address {
    private String address;
    private String city;
    private String zip;

    //@Override
    //public String toString() {
        //return "Address{" +
                //"address='" + address + '\'' +
                //", city='" + city + '\'' +
                //", zip=" + zip +
                //'}';
    //
    // }
}
