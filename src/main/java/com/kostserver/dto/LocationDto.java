package com.kostserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LocationDto{
    private String longitude;
    private String latitude;
    private String address;
    private String province;
    private String city;
    private String district;
    private String note;
}
