package com.chaddy.mareu.service;

import com.chaddy.mareu.model.Reunion;


import java.util.List;


/**
 * Neighbour API client
 */
public interface ReunionApiServiceInterface {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
   List<Reunion> getReunion();





    /**
     * Deletes a neighbour
     * @param reunion
     */
    void deleteReunion(Reunion reunion);

    /**
     * Create a reunion
     * @param reunion
     */
    void createReunion(Reunion reunion);





}
