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
import it.unisa.smartrestaurantapp.activity.CCActivity;
import it.unisa.smartrestaurantapp.activity.LoginActivity;
import it.unisa.smartrestaurantapp.activity.TvActivity;
import it.unisa.smartrestaurantapp.item.Fragment.CCPagamentoListInfoFragment;
import it.unisa.smartrestaurantapp.item.Fragment.CCTavoliFragment;
import it.unisa.smartrestaurantapp.item.Fragment.CCTavoliViewGenerale;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
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

public class ConfermareIlPagamentoTC504 {
    @Rule
    public ActivityTestRule<CCActivity> ccActivityActivityTestRule = new ActivityTestRule<>(CCActivity.class);

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
        Espresso.onData(anything()).inAdapterView(allOf(withId(R.id.menu), isCompletelyDisplayed())).atPosition(3).perform(click());

        Thread.sleep(3000);

        Espresso.onView(withId(R.id.bt_azione)).perform(click());
        Espresso.onView(withText("Pagamento confermato")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }
}
