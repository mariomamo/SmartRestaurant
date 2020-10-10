package it.unisa.smartrestaurantapp.gestionesala.modifca;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.LoginActivity;
import it.unisa.smartrestaurantapp.activity.PrActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;

public class ModificareUnTavoloTC2210 {

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

        //Inserisco i dati
        Espresso.onView(withId(R.id.password)).perform(replaceText("pass"));
        Espresso.onView(withId(R.id.nome)).perform(replaceText("Crystal2"));
        Espresso.onView(withId(R.id.posti)).perform(replaceText("6"));
        Espresso.onView(withId(R.id.stato)).perform(click());

        //Clicco il tasto "Salva" e aspetto che le modifiche vengano eseguite
        Espresso.onView(isRoot()).perform(ViewActions.pressBack());
        Espresso.onView(withId(R.id.salva)).perform(click());
        Thread.sleep(3000);

        //Clicco sull'elemento aggiunto
        Espresso.onData(anything()).inAdapterView(allOf(withId(R.id.list_tavoli), isCompletelyDisplayed())).atPosition(0).perform(click());

        //Controllo che i valori siano corretti
        Espresso.onView(withId(R.id.password)).check(matches(withText(containsString("pass"))));
        Espresso.onView(withId(R.id.nome)).check(matches(withText(containsString("Crystal2"))));
        Espresso.onView(withId(R.id.posti)).check(matches(withText(containsString("6"))));
        Espresso.onView(withId(R.id.stato)).check(matches(isChecked()));
    }
}
