package com.csye6225.demo.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenerateID {

    public String getID(){
        UUID id = UUID.randomUUID();

        return id.toString();
    }

}
