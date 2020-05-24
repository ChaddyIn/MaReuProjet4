package com.chaddy.mareu.service;

import com.chaddy.mareu.model.Reunion;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class ReunionGenerator implements Serializable {



    public static List<Reunion> DUMMY_NEIGHBOURS = Arrays.asList(
            new Reunion(1, "Reunion A", "18/05/2020", "16:45",
                    "PEACH",  "hello@limzone.fr"),

            new Reunion(2, "Reunion B", "20/06/2020", "18:00",
                                "MARIO",  "Jeje@lamzone.fr, Hello@lamzone.fr")




         );


    static List<Reunion> generateReunions() {
        return new ArrayList<>(DUMMY_NEIGHBOURS);
    }







}
