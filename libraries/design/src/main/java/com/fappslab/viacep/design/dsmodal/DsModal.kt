package com.fappslab.viacep.design.dsmodal

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams.MATCH_PARENT
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.fappslab.viacep.arch.viewbinding.viewBinding
import com.fappslab.viacep.design.R
import com.fappslab.viacep.design.databinding.LayoutDsmodalBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.R as GM

class DsModal : BottomSheetDialogFragment() {

    private val binding: LayoutDsmodalBinding by viewBinding()

    var onFragment: () -> Fragment? = { null }
    var onCloseButton: () -> Unit? = { dismissAllowingStateLoss() }
    var onDismiss: () -> Unit? = { dismissAllowingStateLoss() }
    var shouldBlock: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutDsmodalBinding.inflate(inflater, container, false).root

    override fun getTheme(): Int = R.style.DsBottomSheet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHostHeight()
        setupBehavior()
        setupChild()
        setupCloseButton()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss()
    }

    private fun setupHostHeight() {
        (dialog?.findViewById(GM.id.design_bottom_sheet) as? FrameLayout)?.apply {
            layoutParams.height = MATCH_PARENT
        }
    }

    private fun setupBehavior() {
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            if (shouldBlock) {
                isHideable = true
                isCancelable = false
                isDraggable = false
            }
        }
    }

    private fun setupCloseButton() = binding.run {
        closeButton.setOnClickListener { onCloseButton() }
    }

    private fun setupChild() = binding.run {
        onFragment()?.let { fragment ->
            childFragmentManager.commit { replace(containerFragment.id, fragment) }
        }
    }
}
