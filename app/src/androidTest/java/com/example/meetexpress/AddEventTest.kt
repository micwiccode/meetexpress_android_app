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
class AddEventTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MenuActivity::class.java)

    @Test
    fun addEventTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.menu_create), withContentDescription("Create"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.input_text_name),
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
        textInputEditText.perform(replaceText("Wydarzenie "), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.input_text_members),
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
        textInputEditText2.perform(replaceText("123"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.input_text_place),
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
        textInputEditText3.perform(replaceText("Sosnowiec"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.input_text_place), withText("Sosnowiec"),
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
        textInputEditText4.perform(pressImeActionButton())

        val linearLayout = onView(
            allOf(
                withId(R.id.date_box),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        val materialButton1 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    allOf(
                        withClassName(`is`("com.android.internal.widget.ButtonBarLayout")),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            3
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton1.perform(click())

        val linearLayout2 = onView(
            allOf(
                withId(R.id.time_box),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        linearLayout2.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    allOf(
                        withClassName(`is`("com.android.internal.widget.ButtonBarLayout")),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            3
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())


        val materialButton3 = onView(
            allOf(
                withId(R.id.btn_category),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scroll),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val materialRadioButton = onView(
            allOf(
                withId(R.id.rb_education), withText("Education"),
                childAtPosition(
                    allOf(
                        withId(R.id.radio_group),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialRadioButton.perform(click())

        Thread.sleep(2000)

        val materialButton4 = onView(
            allOf(
                withId(R.id.btn_create), withText("Create event"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scroll),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(20000)

        val textView = onView(
            allOf(
                withId(R.id.name), withText("Wydarzenie "),
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
        textView.check(matches(withText("Wydarzenie ")))

        Thread.sleep(2000)


        val textView2 = onView(
            allOf(
                withId(R.id.category), withText("Education"),
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
        textView2.check(matches(withText("Education")))

        Thread.sleep(2000)

        val textView5 = onView(
            allOf(
                withId(R.id.address), withText("Sosnowiec"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Sosnowiec")))

        val textView6 = onView(
            allOf(
                withId(R.id.membersMax), withText("123"),
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
        textView6.check(matches(withText("123")))
    }

    @Test
    fun addEventNoInputsTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.menu_create), withContentDescription("Create"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.input_text_name),
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
        textInputEditText.perform(pressImeActionButton())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.input_text_members),
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
        textInputEditText2.perform(pressImeActionButton())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.input_text_place),
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
        textInputEditText3.perform(pressImeActionButton())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btn_category),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scroll),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val materialRadioButton = onView(
            allOf(
                withId(R.id.rb_culture), withText("Culture"),
                childAtPosition(
                    allOf(
                        withId(R.id.radio_group),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialRadioButton.perform(click())


        Thread.sleep(2000)

        val materialButton = onView(
            allOf(
                withId(R.id.btn_create), withText("Create event"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scroll),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        Thread.sleep(2000)

        val textView = onView(
            allOf(
                withId(R.id.textinput_error), withText("Name is required"),
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
        textView.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.textinput_error), withText("Name is required"),
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
        textView2.check(matches(withText("Name is required")))

        val textView3 = onView(
            allOf(
                withId(R.id.textinput_error), withText("Number of people is required"),
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
        textView3.check(matches(isDisplayed()))

        val textView4 = onView(
            allOf(
                withId(R.id.textinput_error), withText("Number of people is required"),
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
        textView4.check(matches(withText("Number of people is required")))

        val textView5 = onView(
            allOf(
                withId(R.id.textinput_error), withText("Place is required"),
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
        textView5.check(matches(isDisplayed()))

        val textView6 = onView(
            allOf(
                withId(R.id.textinput_error), withText("Place is required"),
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
        textView6.check(matches(withText("Place is required")))
    }

    @Test
    fun addEventNoNameTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.menu_create), withContentDescription("Create"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.input_text_name),
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
        textInputEditText.perform(pressImeActionButton())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.input_text_members),
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
        textInputEditText2.perform(replaceText("25"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.input_text_place),
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
        textInputEditText3.perform(replaceText("Koszalin"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.input_text_place), withText("Koszalin"),
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
        textInputEditText4.perform(pressImeActionButton())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btn_category),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scroll),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val materialRadioButton = onView(
            allOf(
                withId(R.id.rb_sport), withText("Sport"),
                childAtPosition(
                    allOf(
                        withId(R.id.radio_group),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialRadioButton.perform(click())

        Thread.sleep(2000)

        val materialButton = onView(
            allOf(
                withId(R.id.btn_create), withText("Create event"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scroll),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        Thread.sleep(2000)

        val textView = onView(
            allOf(
                withId(R.id.textinput_error), withText("Name is required"),
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
        textView.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.textinput_error), withText("Name is required"),
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
        textView2.check(matches(withText("Name is required")))
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
