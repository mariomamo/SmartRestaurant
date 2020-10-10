package it.unisa.smartrestaurantapp.gestionesala.notifiche;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.HasBackgroundMatcher;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.LoginActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4ClassRunner.class)
public class VisualizzaNotificaTC505 {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Before
    public void setUp() {
    }

    @After
    public void cleanUp() {
    }

    @Test
    public void useAppContext() throws InterruptedException {
        Espresso.onView(withId(R.id.username)).perform(typeText("ccameriere"));
        //password
        Espresso.onView(withId(R.id.password)).perform(typeText("pass"));
        Espresso.onView(withId(R.id.bt_login)).perform(click());
        Thread.sleep(2500);

        Espresso.onData(anything()).inAdapterView(allOf(withId(R.id.lv_notifiche), isCompletelyDisplayed())).atPosition(0).perform(click());
    }
}
