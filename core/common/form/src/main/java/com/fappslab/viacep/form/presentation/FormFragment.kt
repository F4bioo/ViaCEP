package com.fappslab.viacep.form.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.fappslab.viacep.arch.extension.moveCursorTodEnd
import com.fappslab.viacep.arch.viewbinding.viewBinding
import com.fappslab.viacep.arch.viewmodel.onViewAction
import com.fappslab.viacep.arch.viewmodel.onViewState
import com.fappslab.viacep.form.R
import com.fappslab.viacep.form.databinding.FormFragmentBinding
import com.fappslab.viacep.form.presentation.extension.clear
import com.fappslab.viacep.form.presentation.extension.errorState
import com.fappslab.viacep.form.presentation.extension.showErrorDialog
import com.fappslab.viacep.form.presentation.extension.viewArgs
import com.fappslab.viacep.form.presentation.viewmodel.FormViewAction
import com.fappslab.viacep.form.presentation.viewmodel.FormViewModel
import com.fappslab.viacep.form.presentation.viewmodel.FormViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal const val KEY_ARGS = "FormFragment"

internal class FormFragment(
    private val onSaveBlock: () -> Unit = {}
) : Fragment(R.layout.form_fragment) {

    private val binding: FormFragmentBinding by viewBinding()
    private val viewModel: FormViewModel by viewModel { parametersOf(args) }
    private val args: String get() = viewArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        setupListeners()
        setupDataLocal()
    }

    private fun setupObservables() {
        onViewState(viewModel) { state ->
            state.inputZipcodeState()
            state.inputErrorState()
            state.inputResultState()
            state.showErrorState()
            state.loadingState()
        }

        onViewAction(viewModel) { action ->
            when (action) {
                FormViewAction.ClearForm -> clearFormAction()
            }
        }
    }

    private fun setupListeners() = binding.run {
        inputZipcode.addTextChangedListener { viewModel.onTextChangedZipcode(it.toString()) }
        inputStreet.addTextChangedListener { viewModel.onTextChangedStreet(it.toString()) }
        inputDistrict.addTextChangedListener { viewModel.onTextChangedDistrict(it.toString()) }
        inputCity.addTextChangedListener { viewModel.onTextChangedCity(it.toString()) }
        inputState.addTextChangedListener { viewModel.onTextChangedState(it.toString()) }
        inputAreaCode.addTextChangedListener { viewModel.onTextChangedAreaCode(it.toString()) }
        inputLayoutZipcode.setEndIconOnClickListener { viewModel.onGetRemoteAddress(inputZipcode.text.toString()) }
        buttonSave.setOnClickListener { viewModel.onSetLocalAddress() }
    }

    private fun setupDataLocal() {
        viewModel.onGetLocalAddress()
    }

    private fun FormViewState.loadingState() {
        binding.loading.isInvisible = shouldShowLoading.not()
    }

    private fun FormViewState.showErrorState() {
        showErrorDialog(shouldShowError, errorMessage) { viewModel.onCloseError() }
    }

    private fun FormViewState.inputZipcodeState() {
        binding.inputZipcode.apply {
            isEnabled = shouldEnableZipcodeInput
            isFocusable = shouldEnableZipcodeInput
        }
    }

    private fun FormViewState.inputErrorState() = binding.run {
        inputLayoutStreet.errorState(streetErrorRes)
        inputLayoutDistrict.errorState(districtErrorRes)
        inputLayoutCity.errorState(cityErrorRes)
        inputLayoutState.errorState(stateErrorRes)
        inputLayoutAreaCode.errorState(areaCodeErrorRes)
    }

    private fun FormViewState.inputResultState() = binding.run {
        inputZipcode.apply { setText(address.zipcode) }.moveCursorTodEnd()
        inputStreet.apply { setText(address.street) }.moveCursorTodEnd()
        inputDistrict.apply { setText(address.district) }.moveCursorTodEnd()
        inputCity.apply { setText(address.city) }.moveCursorTodEnd()
        inputState.apply { setText(address.state) }.moveCursorTodEnd()
        inputAreaCode.apply { setText(address.areaCode) }.moveCursorTodEnd()
    }

    private fun clearFormAction() = binding.run {
        inputZipcode.clear()
        inputStreet.clear()
        inputDistrict.clear()
        inputCity.clear()
        inputState.clear()
        inputAreaCode.clear()
        onSaveBlock()
    }

    companion object {
        fun newInstance(zipcode: String, onSaveBlock: () -> Unit): Fragment =
            FormFragment(onSaveBlock = onSaveBlock::invoke).apply {
                arguments = bundleOf(KEY_ARGS to zipcode)
            }
    }
}
