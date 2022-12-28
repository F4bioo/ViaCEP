package com.fappslab.viacep.lattetools.robot.checks

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers

fun checkIsDisplayed(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun checkIsNotDisplayed(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
}

fun checkIsEnabled(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
}

fun checkIsNotEnabled(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()))
}

fun checkEndIconTextInputLayoutHasDrawable(@IdRes resId: Int, @DrawableRes iconRes: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(withEndIcon(iconRes)))
}

fun checkEndIconTextInputLayoutIsClicked(@IdRes resId1: Int, @IdRes resId2: Int) {
    Espresso.onView(ViewMatchers.withId(resId1))
        .perform(endIconTextInputLayoutClicked(resId2))
}

fun checkInputTextHasText(@IdRes resId: Int, expectedText: String) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.withText(expectedText)))
}

fun checkInputTextIsEmpty(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.withText("")))
}

fun checkInputTextHasHint(@IdRes resId: Int, expectedText: String) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.withHint(expectedText)))
}

fun checkButtonClicked(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .perform(ViewActions.click())
}
