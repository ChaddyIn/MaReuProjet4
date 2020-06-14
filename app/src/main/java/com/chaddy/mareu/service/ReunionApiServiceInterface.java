package com.chaddy.mareu.service;

import com.chaddy.mareu.model.Reunion;


import java.util.List;


/**
 * Neighbour API client
 */
public interface ReunionApiServiceInterface {

    /**
     * Get all my Neighbours
     *
     * @return {@link List}
     */
    List<Reunion> getReunion();

    List<Reunion> getReunionForFilter();

    List<Reunion> getFilteredList();

    Boolean setBoolForFilter(Boolean boolForFilter);

    void deleteReunion(Reunion reunion);

    /**
     * Create a reunion
     *
     * @param reunion
     */
    void createReunion(Reunion reunion);


}
