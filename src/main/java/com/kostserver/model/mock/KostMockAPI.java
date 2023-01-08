package com.kostserver.model.mock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KostMockAPI {
    private Integer id;
    private String kostName;
    private String createdDate;
    private String image;
    private Integer price;
    private Integer capacity;
    private String facility;
    private Float rating;
    private String location;
    private Map<String,Object> type;
    private Map<String,Object> categories;
}
