package com.chaddy.mareu.service;

import com.chaddy.mareu.R;
import com.chaddy.mareu.model.Reunion;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public abstract class ReunionGenerator {


    public static List<Reunion> DEFAULT_REUNIONS = Arrays.asList(
            new Reunion(1, "Reunion A", "18/05/2020", "16:45",
                    "PEACH", "hello@limzone.fr", R.drawable.circle),


            new Reunion(2, "Reunion B", "20/06/2020", "18:00",
                    "MARIO", "Jeje@lamzone.fr, Hello@lamzone.fr", R.drawable.circle),


            new Reunion(3, "Reunion C", "24/06/2020", "19:00",
                    "LEAP", "Jeje@lamzone.fr, Hello@lamzone.fr", R.drawable.circle),

            new Reunion(4, "Reunion A", "26/05/2020", "16:45",
                    "PEACH", "hello@limzone.fr",R.drawable.circle )


    );


    static List<Reunion> generateReunions() {
        return new ArrayList<>(DEFAULT_REUNIONS);
    }

    static List<Reunion> generateFilterList() {
        return new ArrayList<>(DEFAULT_REUNIONS);
    }

    static List<Reunion> generateFilteredList() {
        return new ArrayList<>();
    }



}
