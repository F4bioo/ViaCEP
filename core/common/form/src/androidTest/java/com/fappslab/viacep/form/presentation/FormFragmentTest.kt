package com.fappslab.viacep.form.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.viacep.form.R
import com.fappslab.viacep.form.presentation.viewmodel.FormViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class FormFragmentTest {

    @get:Rule
    val formFragmentRobot = FormFragmentRobot()

    private val initialState = FormViewState()

    @Test
    fun checkIsDisplayed_get_loading_showing_When_invoke_shouldShowLoading_as_true() {
        val expectedState = initialState.copy(shouldShowLoading = true)

        formFragmentRobot
            .givenState { expectedState }
            .whenExecute()
            .thenCheck { checkLoadingIsDisplayed() }
    }

    @Test
    fun checkIsNotDisplayed_Should_get_loading_hidden_When_invoke_shouldShowLoading_as_false() {
        val expectedState = initialState.copy(shouldShowLoading = false)

        formFragmentRobot
            .givenState { expectedState }
            .whenExecute()
            .thenCheck { checkLoadingIsNotDisplayed() }
    }

    @Test
    fun checkIsEnabled_Should_get_input_enabled_When_invoke_shouldEnableZipcodeInput_as_true() {
        val expectedState = initialState.copy(shouldEnableZipcodeInput = true)

        formFragmentRobot
            .givenState { expectedState }
            .whenExecute()
            .thenCheck { checkZipcodeInputIsEnabled() }
    }

    @Test
    fun checkIsNotEnabled_Should_get_input_disabled_When_invoke_shouldEnableZipcodeInput_as_false() {
        val expectedState = initialState.copy(shouldEnableZipcodeInput = false)

        formFragmentRobot
            .givenState { expectedState }
            .whenExecute()
            .thenCheck { checkZipcodeInputIsNotEnabled() }
    }

    @Test
    fun checkInputTextHint_Should_get_all_inputs_populated_with_hint_When_show_screen() {
        formFragmentRobot
            .whenExecute()
            .thenCheck {
                checkInputTextHasExactlyTextHint(R.id.input_zipcode, expectedHint = "Zip code")
                checkInputTextHasExactlyTextHint(R.id.input_street, expectedHint = "Street")
                checkInputTextHasExactlyTextHint(R.id.input_district, expectedHint = "District")
                checkInputTextHasExactlyTextHint(R.id.input_city, expectedHint = "City")
                checkInputTextHasExactlyTextHint(R.id.input_state, expectedHint = "State")
                checkInputTextHasExactlyTextHint(R.id.input_area_code, expectedHint = "Area code")
            }
    }

    @Test
    fun checkButtonSaveClicked_Should_get_button_save_click_When_invoke_button_save() {
        formFragmentRobot
            .whenExecute()
            .thenCheck { checkButtonSaveClicked() }
    }
}
