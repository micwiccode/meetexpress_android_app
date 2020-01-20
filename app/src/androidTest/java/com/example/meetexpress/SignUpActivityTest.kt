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
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SignUpActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(StartActivity::class.java)

    @Test
    fun signUpActivityTestSamePasswords() {

        Thread.sleep(7000)

        val appCompatTextView = onView(
            allOf(
                withId(R.id.btn_signup), withText("Don't have an account. Sign up"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())


        Thread.sleep(7000)

        val textInputEditText = onView(
            allOf(
                withId(R.id.emailText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emailLayout),
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
                withId(R.id.emailText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emailLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("maciek@xxd.com"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.emailText), withText("maciek@xxd.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emailLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(pressImeActionButton())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.passwordText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.passwordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("Password1"), closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.passwordText), withText("Password1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.passwordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(pressImeActionButton())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.repeatPasswordText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.repeatPasswordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText("password"), closeSoftKeyboard())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.repeatPasswordText), withText("password"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.repeatPasswordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(pressImeActionButton())

        val materialButton = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.textinput_error), withText("Passwords are not the same"),
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
        textView.check(matches(withText("Passwords are not the same")))

    }

    @Test
    fun signUpActivityTestTooShortPassword() {

        Thread.sleep(7000)

        val appCompatTextView = onView(
            allOf(
                withId(R.id.btn_signup), withText("Don't have an account. Sign up"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())


        Thread.sleep(7000)

        val textInputEditText = onView(
            allOf(
                withId(R.id.emailText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emailLayout),
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
                withId(R.id.emailText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emailLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("maciek@xxd.com"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.emailText), withText("maciek@xxd.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emailLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(pressImeActionButton())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.passwordText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.passwordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("pass"), closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.passwordText), withText("pass"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.passwordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(pressImeActionButton())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.repeatPasswordText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.repeatPasswordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText("pass1"), closeSoftKeyboard())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.repeatPasswordText), withText("pass1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.repeatPasswordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(pressImeActionButton())

        val materialButton = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.textinput_error), withText("Please enter password (at least 6 characters)"),
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
        textView.check(matches(withText("Please enter password (at least 6 characters)")))

    }

    @Test
    fun signUpActivityTestIncorrectEmail() {

        Thread.sleep(7000)

        val appCompatTextView = onView(
            allOf(
                withId(R.id.btn_signup), withText("Don't have an account. Sign up"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())


        Thread.sleep(7000)

        val textInputEditText = onView(
            allOf(
                withId(R.id.emailText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emailLayout),
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
                withId(R.id.emailText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emailLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("maciek"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.emailText), withText("maciek"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emailLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(pressImeActionButton())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.passwordText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.passwordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("password"), closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.passwordText), withText("password"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.passwordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(pressImeActionButton())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.repeatPasswordText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.repeatPasswordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText("password"), closeSoftKeyboard())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.repeatPasswordText), withText("password"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.repeatPasswordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(pressImeActionButton())

        val materialButton = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.textinput_error), withText("Please enter valid email"),
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
        textView.check(matches(withText("Please enter valid email")))

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
