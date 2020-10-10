package it.unisa.smartrestaurantapp.gestioneordini;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import it.unisa.smartrestaurantapp.FragmentMatcher;
import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.ECActivity;
import it.unisa.smartrestaurantapp.activity.LoginActivity;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class ConfermarePreparazionePiattoTC702 {
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
        //### Login
        //username
        Espresso.onView(withId(R.id.username)).perform(typeText("exchef"));
        //password
        Espresso.onView(withId(R.id.password)).perform(typeText("pass"));
        Espresso.onView(withId(R.id.bt_login)).perform(click());
        Thread.sleep(1500);

        //Controllo che venga caricata l'activity del tavolo
        intended(hasComponent(ECActivity.class.getName()));
        Espresso.onView(allOf(FragmentMatcher.withIndex(withId(R.id.btn_positive), 0))).perform(click());
        Espresso.onView(withText("Si")).perform(click());
        Thread.sleep(1500);
    }
}
