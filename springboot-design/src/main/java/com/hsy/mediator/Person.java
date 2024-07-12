package com.hsy.mediator;

import lombok.Data;

import java.io.Serializable;
@Data
public class Person  {
    public Mediator mediator;

    public String name;

    public Person(String name, Mediator mediator) {
        this.mediator=mediator;
        this.name=name;
    }
}