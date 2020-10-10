package it.unisa.smartrestaurantapp.gestionesala;

import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.CCActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4ClassRunner.class)
public class LiberaTavoloTC503 {
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
        Espresso.onData(anything()).inAdapterView(allOf(withId(R.id.menu), isCompletelyDisplayed())).atPosition(2).perform(click());

        Thread.sleep(2000);
        Espresso.onData(anything()).inAdapterView(allOf(withId(R.id.list_tavoli), isCompletelyDisplayed())).atPosition(0).perform(click());
        EditText et = ccActivityActivityTestRule.getActivity().findViewById(R.id.username);
        String edit = et.getText().toString();

        Espresso.onView(withId(R.id.bt_azione)).perform(click());
        Thread.sleep(2000);
        Espresso.onView(allOf(withId(R.id.list_tavoli), not(withText(edit))));
    }
}
