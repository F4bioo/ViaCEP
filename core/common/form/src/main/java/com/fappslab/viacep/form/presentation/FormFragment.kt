package com.fappslab.viacep.form.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.fappslab.viacep.arch.args.putArguments
import com.fappslab.viacep.arch.args.viewArgs
import com.fappslab.viacep.arch.args.withArgs
import com.fappslab.viacep.arch.extension.moveCursorTodEnd
import com.fappslab.viacep.arch.extension.setOnBackPressedDispatcher
import com.fappslab.viacep.arch.viewbinding.viewBinding
import com.fappslab.viacep.arch.viewmodel.onViewAction
import com.fappslab.viacep.arch.viewmodel.onViewState
import com.fappslab.viacep.form.R
import com.fappslab.viacep.form.databinding.FormFragmentBinding
import com.fappslab.viacep.form.presentation.extension.clear
import com.fappslab.viacep.form.presentation.extension.errorState
import com.fappslab.viacep.form.presentation.extension.showErrorDialog
import com.fappslab.viacep.form.presentation.viewmodel.FormViewAction
import com.fappslab.viacep.form.presentation.viewmodel.FormViewModel
import com.fappslab.viacep.form.presentation.viewmodel.FormViewState
import com.fappslab.viacep.navigation.ZipcodeArgs
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class FormFragment : Fragment(R.layout.form_fragment) {

    private val binding: FormFragmentBinding by viewBinding()
    private val viewModel: FormViewModel by sharedViewModel()
    private val args: ZipcodeArgs by viewArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        setupListeners()
        setupDataLocal()
    }

    private fun setupObservables() {
        onViewState(viewModel) { state ->
            state.inputErrorState()
            state.inputResultState()
            state.showErrorState()
            state.loadingState()
        }

        onViewAction(viewModel) { action ->
            when (action) {
                FormViewAction.FinishView -> activity?.finish()
                FormViewAction.ClearForm -> clearFormAction()
            }
        }
    }

    private fun setupListeners() = binding.run {
        setOnBackPressedDispatcher { viewModel.onFinishView() }
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
        viewModel.onGetLocalAddress(args.zipcode)
    }

    private fun FormViewState.loadingState() {
        binding.loading.isInvisible = shouldShowLoading.not()
    }

    private fun FormViewState.showErrorState() {
        showErrorDialog(shouldShowError, errorMessage) { viewModel.onCloseError() }
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
    }

    companion object {
        fun newInstance(args: ZipcodeArgs): Fragment =
            FormFragment().withArgs { putArguments(args) }
    }
}
