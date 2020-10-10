package it.unisa.smartrestaurantapp;

import android.os.IBinder;
import android.view.WindowManager;
import androidx.test.espresso.Root;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {
    /*
    * Usage:
    * Espresso.onView(withText("Password errata")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    * Espresso.onView(withText("Password errata")).inRoot(new ToastMatcher()).check(matches(not(isDisplayed())));
    * Espresso.onView(withText(R.string.message)).inRoot(new ToastMatcher()).check(matches(withText("Invalid Name"));
    * */
    @Override public void describeTo(Description description) {
        description.appendText("is toast");
    }

    @Override public boolean matchesSafely(Root root) {
        int type = root.getWindowLayoutParams().get().type;
        if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
            IBinder windowToken = root.getDecorView().getWindowToken();
            IBinder appToken = root.getDecorView().getApplicationWindowToken();
            if (windowToken == appToken) {
                return true;
            }
        }
        return false;
    }
}