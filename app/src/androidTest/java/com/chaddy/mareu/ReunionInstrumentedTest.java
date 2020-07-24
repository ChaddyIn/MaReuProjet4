package com.chaddy.mareu;

import android.content.Context;
import android.service.autofill.FieldClassification;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;


import com.chaddy.mareu.di.DI;
import com.chaddy.mareu.reunion_list.ListReunionActivity;
import com.chaddy.mareu.service.ReunionApiService;
import com.chaddy.mareu.utils.DeleteViewAction;
import com.chaddy.mareu.utils.SearchViewUtils;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.PickerActions.setDate;
import static android.support.test.espresso.contrib.PickerActions.setTime;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.chaddy.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class ReunionInstrumentedTest {


    private ListReunionActivity mActivity;

    private static int ITEMS_COUNT = 4;

    @Rule
    public ActivityTestRule<ListReunionActivity> mActivityRule =
            new ActivityTestRule(ListReunionActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void myReunionsList_shouldNotBeEmpty() {
        onView(withId(R.id.list_reunion))
                .check(matches(hasMinimumChildCount(1)));
    }



    @Test
    public void filterByDate_Display_FilteredList(){


        //Open filter
           onView(withId(R.id.filtreParDate)).perform(click());
           onView(withClassName(is("android.support.v7.widget.SearchView"))).perform(SearchViewUtils.typeSearchViewText("24"));
        onView(withId(R.id.list_reunion))
                .check(matches(hasMinimumChildCount(1)));
        onView(withId(R.id.item_list_meeting)).check(matches(withText(containsString("24"))));



    }


    @Test
    public void filterByRoom_Display_FilteredList(){


        //Open filter
        onView(withId(R.id.filtreParSalle)).perform(click());
        onView(withClassName(is("android.support.v7.widget.SearchView"))).perform(SearchViewUtils.typeSearchViewText("LEA"));
        onView(withId(R.id.list_reunion))
                .check(matches(hasMinimumChildCount(1)));
        onView(withId(R.id.item_list_meeting)).check(matches(withText(containsString("LEA"))));
        onView(withId(R.id.item_list_meeting)).check(matches(withText(containsString("LEA"))));


    }

    @Test
    public void myReunionList_deleteAction_shouldRemoveItem() {

        onView((withId(R.id.list_reunion))).check(matches(isDisplayed()));
        onView(withId(R.id.list_reunion))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(withId(R.id.list_reunion))
                .check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void myReunionList_add_reunion(){
        onView(withId(R.id.add_reunion)).perform(click());
        onView(withId(R.id.sujet)).perform(typeText("Info"));
        onView(withId(R.id.sujet)).perform(closeSoftKeyboard());
        onView(withId(R.id.participantsMeeting)).perform(typeText("jojo@lamzone.com"));
        onView(withId(R.id.sujet)).perform(closeSoftKeyboard());

        // Show the date picker
        onView(withId(R.id.datePicker)).perform(click());
// Sets a date on the date picker widget
        onView(isAssignableFrom(DatePicker.class)).perform(setDate(2020, 9, 30));
// Confirm the selected date. This example uses a standard DatePickerDialog
// which uses
// android.R.id.button1 for the positive button id.
        onView(withId(android.R.id.button1)).perform(click());
// Check if the selected date is correct and is displayed in the Ui.

// Sets a time in a view picker widget
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(8, 30));
// Confirm the time
        onView(withId(android.R.id.button1)).perform(click());
// Check if the date result is displayed.
        onView(withId(R.id.timePicker)).check(matches(allOf(withText("08:30"),
                isDisplayed())));
        onView(withId(R.id.datePicker)).check(matches(allOf(withText("30/09/2020"),
                isDisplayed())));
        onView(withId(R.id.timePicker)).perform(closeSoftKeyboard());

        onView(withId(R.id.salle)).perform(typeText("B"));
        onView(withId(R.id.salle)).perform(closeSoftKeyboard());

        onView(withId(R.id.create)).perform(click());
        onView(withId(R.id.list_reunion)).check(new ItemCount(ITEMS_COUNT + 1));

    }

    public static class ItemCount implements ViewAssertion {
        private final int expectedCount;

        public ItemCount(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

}
