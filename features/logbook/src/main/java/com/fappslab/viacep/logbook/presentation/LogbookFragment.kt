package com.fappslab.viacep.logbook.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.fappslab.viacep.arch.adapter.GenericAdapter
import com.fappslab.viacep.arch.viewbinding.viewBinding
import com.fappslab.viacep.arch.viewmodel.onViewState
import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.logbook.R
import com.fappslab.viacep.logbook.databinding.LogbookAddressItemBinding
import com.fappslab.viacep.logbook.databinding.LogbookFragmentBinding
import com.fappslab.viacep.logbook.presentation.extension.showEditorBottomSheet
import com.fappslab.viacep.logbook.presentation.viewmodel.LogbookViewModel
import com.fappslab.viacep.logbook.presentation.viewmodel.LogbookViewState
import com.fappslab.viacep.navigation.FormNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class LogbookFragment : Fragment(R.layout.logbook_fragment) {

    private val binding: LogbookFragmentBinding by viewBinding()
    private val viewModel: LogbookViewModel by sharedViewModel()
    private val formNavigation: FormNavigation by inject()
    private val logbookAdapter by lazy {
        GenericAdapter<Address>(::inflate) { viewBinding, item ->
            (viewBinding as LogbookAddressItemBinding).run { bind(item) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupObservables()
        setupList()
    }

    private fun setupRecycler() {
        binding.recyclerLogbook.run {
            adapter = logbookAdapter
            setHasFixedSize(true)
            itemAnimator = null
        }
    }

    private fun setupObservables() {
        onViewState(viewModel) { state ->
            state.submitListState()
            state.showEditorBottomSheetState()
        }
    }

    private fun setupList() {
        viewModel.onGetLocalAddresses()
    }

    private fun LogbookViewState.submitListState() {
        logbookAdapter.submitList(addresses)
    }

    private fun LogbookViewState.showEditorBottomSheetState() {
        showEditorBottomSheet(shouldShowEditorBottomSheet, zipcode, formNavigation) {
            viewModel.onCloseEditorBottomSheet()
        }
    }

    private fun LogbookAddressItemBinding.bind(address: Address) {
        textZipcode.text = getString(R.string.logbook_zipcode, address.zipcode).parseAsHtml()
        textStreet.text = getString(R.string.logbook_street, address.street).parseAsHtml()
        textCity.text = getString(R.string.logbook_city, address.city, address.state).parseAsHtml()
        buttonDelete.setOnClickListener { viewModel.onDeleteLocalAddress(address.zipcode) }
        cardAddress.setOnClickListener { viewModel.onCardItem(address.zipcode) }
    }

    private fun inflate(parent: ViewGroup): ViewBinding = LayoutInflater.from(parent.context)
        .let { LogbookAddressItemBinding.inflate(it, parent, false) }
}
