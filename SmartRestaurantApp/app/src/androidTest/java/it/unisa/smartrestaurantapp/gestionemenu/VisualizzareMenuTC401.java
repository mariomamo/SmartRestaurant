package it.unisa.smartrestaurantapp.gestionemenu;

import android.content.Intent;

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
import it.unisa.smartrestaurantapp.activity.TvActivity;
import it.unisa.smartrestaurantapp.entity.Account;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.Fragment.TvDishFragment;
import it.unisa.smartrestaurantapp.item.Fragment.TvLeftMenuFragment;
import it.unisa.smartrestaurantapp.service.LoginServerListener;
import it.unisa.smartrestaurantapp.service.LoginService;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class VisualizzareMenuTC401 {
    @Rule
    public ActivityTestRule<TvActivity> tvActivityActivityTestRule = new ActivityTestRule<TvActivity>(TvActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("account", new Tavolo("tavolo1", "pass", "Tavolo 1", 4, false, false));
            return intent;
        }
    };

    @Before
    public void setUp() {
    }

    @After
    public void cleanUp() {
    }

    @Test
    public void useAppContext() throws InterruptedException {
        Espresso.onView(withId(R.id.menu)).perform(click());
        Espresso.onView(withId(R.id.centerViewContainer)).check(matches(isDisplayed()));
    }
}
