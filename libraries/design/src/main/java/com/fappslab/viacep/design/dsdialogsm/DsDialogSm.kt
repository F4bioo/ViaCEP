package com.fappslab.viacep.design.dsdialogsm

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.fappslab.viacep.design.databinding.LayoutDsdialogSmBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class DsDialogSm : DialogFragment() {

    private lateinit var binding: LayoutDsdialogSmBinding

    @StringRes
    var titleRes: Int? = null
    var titleText: String? = null

    @StringRes
    var messageRes: Int? = null
    var messageText: String? = null

    var gravityBottom: Boolean = true

    var dismissAction: () -> Unit? = {}
    var buttonClose: () -> Unit? = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutDsdialogSmBinding
        .inflate(inflater, container, false)
        .root

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = LayoutDsdialogSmBinding.inflate(layoutInflater)
        return setupDialog(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitle()
        setupMessage()
        setupCloseButton()
        setExpanded()
    }

    private fun setupDialog(view: View): Dialog {
        AlertDialog.Builder(view.context).run {
            setView(view)
            val builder = create()
            builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            builder.window?.gravityBottom(gravityBottom)

            return builder
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissAction.invoke()
    }

    private fun setExpanded() {
        (dialog as? BottomSheetDialog)?.behavior?.state =
            BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setupTitle() {
        binding.dsTextTitle.apply {
            text = getText(titleRes, titleText)
            handleVisibility()
        }
    }

    private fun setupMessage() {
        binding.dsTextMessage.apply {
            text = getText(messageRes, messageText)
            movementMethod = ScrollingMovementMethod()
            handleVisibility()
        }
    }

    private fun setupCloseButton() {
        binding.dsButtonClose.setOnClickListener {
            buttonClose.invoke()
            dismiss()
        }
    }

    private fun TextView.handleVisibility() {
        isVisible = text.isNotEmpty()
    }

    private fun getText(contentRes: Int?, content: String?): String {
        contentRes?.let { return getString(it) }
        return content.orEmpty()
    }

    private fun Window?.gravityBottom(isGravityBottom: Boolean) {
        if (isGravityBottom) this?.setGravity(Gravity.BOTTOM)
    }
}
