package it.unisa.smartrestaurantapp.gestioneordini;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.ToastMatcher;
import it.unisa.smartrestaurantapp.activity.LoginActivity;
import it.unisa.smartrestaurantapp.activity.TvActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

public class RimuoverePiattoDaOrdineTC601 {
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
        Espresso.onView(withId(R.id.username)).perform(typeText("Tavolo1"));
        //password
        Espresso.onView(withId(R.id.password)).perform(typeText("pass"));
        Espresso.onView(withId(R.id.bt_login)).perform(click());
        Thread.sleep(1500);

        //Controllo che venga caricata l'activity del tavolo
        intended(hasComponent(TvActivity.class.getName()));

        Espresso.onData(anything()).inAdapterView(allOf(withId(R.id.gv_piatti), isCompletelyDisplayed())).atPosition(0).perform(click());
        Espresso.onView(withId(R.id.btn_aggiungi)).perform(click());
        Espresso.onView(withId(R.id.btn_carrello)).perform(click());
        Espresso.onView(withId(R.id.iv_remove)).perform(click());
        Espresso.onView(withText("Piatto rimosso")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }
}
