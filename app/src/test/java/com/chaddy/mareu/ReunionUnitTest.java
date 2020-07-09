package com.chaddy.mareu;

import android.support.v7.widget.RecyclerView;
import android.widget.Filter;

import com.chaddy.mareu.di.DI;
import com.chaddy.mareu.model.Reunion;
import com.chaddy.mareu.reunion_list.MyReunionRecyclerViewAdapter;
import com.chaddy.mareu.service.ReunionApiService;
import com.chaddy.mareu.service.ReunionGenerator;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class ReunionUnitTest {

    private ReunionApiService service;
    private MyReunionRecyclerViewAdapter mReunionRe;

    @Before
    public void setup() {

        service = DI.getNewInstanceApiService();
    }



    @Test
    public void getReunionsWithSuccess() {
        List<Reunion> reunions = service.getReunion();
        List<Reunion> expectedReunions = ReunionGenerator.DEFAULT_REUNIONS;
        assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedReunions.toArray()));
    }


    @Test
    public void deleteReunionWithSuccess() {
        Reunion reunionToDelete = service.getReunion().get(0);
        service.deleteReunion(reunionToDelete);
        assertFalse(service.getReunion().contains(reunionToDelete));
    }

    @Test
    public void addNewReunionWithSuccess() {

        Reunion newReunionToAdd = new Reunion(15, "TEST", "18/06/2022", "17:30",
                "BOBO",  "test@lamzone.com", R.drawable.circle_red);
        service.createReunion(newReunionToAdd);
        assertTrue(service.getReunion().contains(newReunionToAdd));

    }

    @Test
    public void getFilteredReunionByRoom(){



        Reunion reunionToFilter = service.getReunionForFilter().get(3);

       assertTrue(service.filterByRoom("PEACH").contains(reunionToFilter));

    }

    @Test
    public void getFilteredReunionByDate(){

        Reunion reunionToFilter = service.getReunionForFilter().get(2);
        assertTrue(service.filterByDate("24/06").contains(reunionToFilter));
    }

}



