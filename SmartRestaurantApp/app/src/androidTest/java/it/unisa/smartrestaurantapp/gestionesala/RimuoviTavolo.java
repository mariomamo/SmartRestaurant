package it.unisa.smartrestaurantapp.gestionesala;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.LoginActivity;
import it.unisa.smartrestaurantapp.activity.PrActivity;
import it.unisa.smartrestaurantapp.gestioneaccount.LoginProprietario;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

public class RimuoviTavolo {

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
        Espresso.onView(withId(R.id.username)).perform(typeText("Proprietario"));
        //password
        Espresso.onView(withId(R.id.password)).perform(typeText("pass"));
        Espresso.onView(withId(R.id.bt_login)).perform(click());
        Thread.sleep(1500);

        //Controllo che venga caricata l'activity del tavolo
        intended(hasComponent(PrActivity.class.getName()));
        Thread.sleep(1500);

        //### Modifico un tavolo
        //Clicco sul primo tavolo
        Espresso.onData(anything()).inAdapterView(allOf(withId(R.id.list_tavoli), isCompletelyDisplayed())).atPosition(0).perform(click());

        //Elimino il tavolo
        Espresso.onView(withId(R.id.elimina)).perform(click());
        Thread.sleep(1500);
    }
}
