package com.application.mxm.soundtracks;

import android.support.design.widget.TextInputLayout;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class RepoOwnerInputTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        onView(withId(R.id.findButtonId)).perform(click());

    }
    @Test
    public void countryIsEmptyEditTextTest() {
        onView(withId(R.id.countryTextInputLayoutId))
                .check(matches(withText("")));
    }
    @Test
    public void countryHasErrorTextInputLayoutTest() {
        onView(withId(R.id.countryTextInputLayoutId))
                .check(matches(hasTextInputLayoutErrorText(mRule.getActivity().getString(R.string.no_input_data))));
    }

    @Test
    public void pageSizeIsEmptyEditTextTest() {
        onView(withId(R.id.pageSizeTextInputLayoutId))
                .check(matches(withText("")));
    }
    @Test
    public void pageSizeHasErrorTextInputLayoutTest() {
        onView(withId(R.id.pageSizeTextInputLayoutId))
                .check(matches(hasTextInputLayoutErrorText(mRule.getActivity().getString(R.string.no_input_data))));
    }

    /**
     *
     * @param expectedErrorText
     * @return
     */
    private static Matcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextInputLayout)) {
                    return false;
                }

                return ((TextInputLayout) view).getError() != null &&
                        expectedErrorText.equals(((TextInputLayout) view).getError().toString());
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }
}
