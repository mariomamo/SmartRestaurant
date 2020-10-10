package it.unisa.smartrestaurantapp.gestionesala;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.CCActivity;
import it.unisa.smartrestaurantapp.activity.LoginActivity;
import it.unisa.smartrestaurantapp.activity.PrActivity;
import it.unisa.smartrestaurantapp.activity.TvActivity;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.Fragment.CCTavoliFragment;
import it.unisa.smartrestaurantapp.item.Fragment.CCTavoliViewGenerale;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4ClassRunner.class)
public class OccupaTavoloTC502 {
    @Rule
    public ActivityTestRule<CCActivity> ccActivityActivityTestRule = new ActivityTestRule<CCActivity>(CCActivity.class);

    @Before
    public void setUp() {
    }

    @After
    public void cleanUp() {
    }

    @Test
    public void useAppContext() throws InterruptedException {
        Espresso.onData(anything()).inAdapterView(allOf(withId(R.id.menu), isCompletelyDisplayed())).atPosition(1).perform(click());

        Thread.sleep(2000);
        Espresso.onData(anything()).inAdapterView(allOf(withId(R.id.list_tavoli), isCompletelyDisplayed())).atPosition(0).perform(click());
        EditText et = ccActivityActivityTestRule.getActivity().findViewById(R.id.username);
        String edit = et.getText().toString();

        Espresso.onView(withId(R.id.bt_azione)).perform(click());
        Thread.sleep(2000);
        Espresso.onView(allOf(withId(R.id.list_tavoli), not(withText(edit))));
    }
}
