package com.chaddy.mareu.service;

import com.chaddy.mareu.model.Reunion;


import java.util.List;

/**
 * Dummy mock for the Api
 */
public class ReunionApiService implements ReunionApiServiceInterface {

    private List<Reunion> reunions = ReunionGenerator.generateReunions();

    private List<Reunion> reunionsForFilter = ReunionGenerator.generateFilterList();

    private List<Reunion> reunionsFiltered = ReunionGenerator.generateFilteredList();

    Boolean boolFilter;





    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reunion> getReunion() {
        return reunions;
    }

    public List<Reunion> getReunionForFilter() { return reunionsForFilter; }

    public  List<Reunion> getFilteredList() {return  reunionsFiltered;}



    public  Boolean setBoolForFilter(Boolean boolForFilter){
        this.boolFilter = boolForFilter;
        return false;

    }



    /**
     * {@inheritDoc}
     * @param reunion
     */
    @Override
   public void deleteReunion(Reunion reunion) { reunions.remove(reunion);
   reunionsForFilter.remove(reunion);
   reunionsFiltered.remove(reunion);

    }

    /**
     * {@inheritDoc}
     * @param reunion
     */
   @Override
    public void createReunion(Reunion reunion) { reunions.add(reunion);
    reunionsForFilter.add(reunion);
    }


}
