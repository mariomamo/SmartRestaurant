package it.unisa.smartrestaurantapp.gestioneaccount;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.LoginActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class LoginTC1102 {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void useAppContext() throws InterruptedException {
        //username
        Espresso.onView(withId(R.id.username)).perform(typeText("Tavolooooooooooooooooooooooooooooooooooooooooooooo1"));
        //password
        Espresso.onView(withId(R.id.password)).perform(typeText("password1234"));
        Espresso.onView(withId(R.id.bt_login)).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.error)).check(matches(isDisplayed()));
    }

}