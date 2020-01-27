package com.example.meetexpress


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ModifyEventTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(StartActivity::class.java)

    @Test
    fun modifyEventTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val fu = onView(
            allOf(
                withText("Sign In"),
                childAtPosition(
                    allOf(
                        withId(R.id.btn_sign_up_google),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            5
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        fu.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(14000)

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.menu_review), withContentDescription("Review"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_nav_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val materialCardView = onView(
            allOf(
                withId(R.id.cardView),
                childAtPosition(
                    allOf(
                        withId(R.id.recycleView),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialCardView.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val materialButton = onView(
            allOf(
                withId(R.id.modify_btn), withText("Modify"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.event_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.input_layout_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(click())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.event_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.input_layout_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("123Test"))

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.event_name), withText("123Test"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.input_layout_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.event_members),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.input_layout_members),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("6"))

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.event_members), withText("6"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.input_layout_members),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(closeSoftKeyboard())

        val materialRadioButton = onView(
            allOf(
                withId(R.id.cb_party),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        6
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialRadioButton.perform(click())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.event_members), withText("6"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.input_layout_members),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(pressImeActionButton())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.event_place), withText("Pwr"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.input_layout_place),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(pressImeActionButton())
        onView(withId(R.id.swipeLayout)).perform(swipeUp())
        Thread.sleep(7000)
        val materialButton2 = onView(
            allOf(
                withId(R.id.save_btn), withText("Save"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.core.widget.NestedScrollView")),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val materialCardView2 = onView(
            allOf(
                withId(R.id.cardView),
                childAtPosition(
                    allOf(
                        withId(R.id.recycleView),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialCardView2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val textView = onView(
            allOf(
                withId(R.id.name), withText("123Test"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("123Test")))

        val textView2 = onView(
            allOf(
                withId(R.id.category), withText("Party"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Party")))

        val textView3 = onView(
            allOf(
                withId(R.id.membersMax), withText("6"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        3
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("6")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
