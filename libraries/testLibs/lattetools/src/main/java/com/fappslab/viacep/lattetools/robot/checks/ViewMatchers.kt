package com.fappslab.viacep.lattetools.robot.checks

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withEndIcon(@DrawableRes iconRes: Int): Matcher<View> {
    return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with end icon drawable")
        }

        override fun matchesSafely(textInputLayout: TextInputLayout): Boolean {
            val drawable = ContextCompat.getDrawable(textInputLayout.context, iconRes)

            return drawable == textInputLayout.endIconDrawable
        }
    }
}

fun endIconTextInputLayoutDrawable(@DrawableRes iconRes: Int) =
    object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("is end icon drawable")
        }

        override fun matchesSafely(textInputLayout: TextInputLayout): Boolean {
            val drawableExpected =
                ContextCompat.getDrawable(textInputLayout.context, iconRes)?.toBitmap()
            return textInputLayout.endIconDrawable?.toBitmap()
                ?.sameAs(drawableExpected) == true
        }
    }

fun endIconTextInputLayoutClicked(@IdRes idRes: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return allOf(
                isDisplayed(),
                isAssignableFrom(TextInputLayout::class.java)
            )
        }

        override fun getDescription(): String {
            return "click end icon"
        }

        override fun perform(uiController: UiController, view: View) {
            val textInputLayout = view as TextInputLayout
            val endIcon = textInputLayout.findViewById<ImageView>(idRes)
            endIcon.performClick()
        }
    }
}
