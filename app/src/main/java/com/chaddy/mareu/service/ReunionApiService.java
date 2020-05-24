package com.chaddy.mareu.service;

import com.chaddy.mareu.model.Reunion;


import java.util.List;

/**
 * Dummy mock for the Api
 */
public class ReunionApiService implements ReunionApiServiceInterface {

    private List<Reunion> reunions = ReunionGenerator.generateReunions();





    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reunion> getReunion() {
        return reunions;
    }










    /**
     * {@inheritDoc}
     * @param reunion
     */
    @Override
   public void deleteReunion(Reunion reunion) { reunions.remove(reunion);
    }

    /**
     * {@inheritDoc}
     * @param reunion
     */
   @Override
    public void createReunion(Reunion reunion) {
        reunions.add(reunion);
    }


}
