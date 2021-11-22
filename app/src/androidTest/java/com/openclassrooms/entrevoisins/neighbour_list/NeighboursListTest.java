
package com.openclassrooms.entrevoisins.neighbour_list;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    private Neighbour neighbourName;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * Quand on clique sur un neighbour, il ouvre bien ProfileNeighbourActivity
     */
    @Test
    public void myNeighboursList_clickOnNeighbour_shouldOpenProfile() {
        // Clique sur un neighbour dans la liste
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

        // Verifie que le layout detail est affiché
        onView(ViewMatchers.withId(R.id.detail_layout))
                .check(matches(isDisplayed()));
    }

    /**
     * Quand on clique sur un neighbour, son nom est bien affiché dans la Textview dans le profile
     */
    @Test
    public void myNeighboursList_clickOnNeighbour_shouldShowNeighbourNameInTextView() {
        // Clique sur un neighbour dans la liste
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

        // Renvoie le nom du neighbour dans la Textview prévue pour
        neighbourName = DI.getNeighbourApiService().getNeighbours().get(1);
        onView(withId(R.id.profile_name))
                .check(matches(withText(neighbourName.getName())));

    }

    /**
     * La liste des favoris montre bien seulement les neighbours ajoutés en favoris
     */
    @Test
    public void myFavoritesList_showOnlyFavoriteNeighbours() {
        // Clique sur un neighbour dans la liste
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

        // Clique sur le bouton d'ajout en favoris
        onView(withId(R.id.AddFavoriteButton))
                .perform(ViewActions.click());

        // Retourne en arrière
        onView(isRoot()).perform(ViewActions.pressBack());

        // Vérifie que le neighbour a bien été passé dans la liste des favoris
        onView(withId(R.id.list_favoriteneighbours))
            .check(withItemCount(1));
    }


}