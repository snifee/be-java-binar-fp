package com.kostserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_ratings")
public class Rating extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;

    private String reviewText;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Room room;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "room_rating",
//            joinColumns = @JoinColumn(name = "rating_id"),
//            inverseJoinColumns = @JoinColumn(name = "room_id")
//    )
//    private List<Room> roomId;
}
