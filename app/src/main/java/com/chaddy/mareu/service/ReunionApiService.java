package com.chaddy.mareu.service;

import com.chaddy.mareu.model.Reunion;


import java.util.ArrayList;
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

    @Override
    public List<Reunion> filterByRoom(String Room) {

        List<Reunion> listFiltered = new ArrayList<>();

        if (Room == null || Room.length() == 0) {

            listFiltered.clear();
            listFiltered.addAll(getReunionForFilter());
        } else {

            String filterPattern = Room.toString().toLowerCase().trim();

            for (Reunion reunionList : getReunionForFilter()) {

                if (reunionList.getSalle().toLowerCase().contains(filterPattern)) {

                    listFiltered.add(reunionList);

                }

            }

        }
        return listFiltered;

    }


    public List<Reunion> filterByDate(String Date) {

        List<Reunion> listFiltered = new ArrayList<>();

        if (Date == null || Date.length() == 0) {

            listFiltered.clear();
            listFiltered.addAll(getReunionForFilter());
        } else {

            String filterPattern = Date.toString().toLowerCase().trim();

            for (Reunion reunionList : getReunionForFilter()) {

                if (reunionList.getDate().toLowerCase().contains(filterPattern)) {

                    listFiltered.add(reunionList);

                }

            }

        }
        return listFiltered;

    }


}
