package com.fappslab.viacep.design.dsprogress

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.fappslab.viacep.design.databinding.LayoutDsprogressBinding

class DsProgress : DialogFragment() {

    private lateinit var binding: LayoutDsprogressBinding

    var buttonCancel: () -> Unit? = {}
    var shouldShowButtonCancel: Boolean = true
    var shouldShowMessage: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutDsprogressBinding
        .inflate(inflater, container, false)
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCancelButton()
        setupMessage()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = LayoutDsprogressBinding.inflate(layoutInflater)
        return setupDialog(binding.root)
    }

    private fun setupDialog(view: View): Dialog {
        AlertDialog.Builder(view.context).run {
            setView(view)
            val builder = create()
            builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            return builder
        }
    }

    override fun onResume() {
        isCancelable = false
        super.onResume()
    }

    private fun setupCancelButton() {
        binding.dsButtonCancel.apply {
            isVisible = shouldShowButtonCancel
        }.setOnClickListener {
            buttonCancel.invoke()
            dismiss()
        }
    }

    private fun setupMessage() {
        binding.dsTextLoading.isVisible = shouldShowMessage
    }
}
