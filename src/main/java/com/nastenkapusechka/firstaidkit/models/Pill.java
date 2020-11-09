package com.nastenkapusechka.firstaidkit.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Comparator;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Pill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String form;
    private Date date;

    public Pill(String name, String description, String form, Date date) {
        this.name = name;
        this.description = description;
        this.form = form;
        this.date = date;
    }
}
