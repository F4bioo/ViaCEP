package com.fappslab.viacep.editor.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.fappslab.viacep.arch.args.putArguments
import com.fappslab.viacep.arch.args.viewArgs
import com.fappslab.viacep.arch.args.withArgs
import com.fappslab.viacep.arch.viewbinding.viewBinding
import com.fappslab.viacep.editor.databinding.EditorBottomSheetBinding
import com.fappslab.viacep.navigation.FormNavigation
import com.fappslab.viacep.navigation.ZipcodeArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import com.fappslab.viacep.design.R as DS
import com.google.android.material.R as GM

internal class EditorBottomSheet : BottomSheetDialogFragment() {

    private val binding: EditorBottomSheetBinding by viewBinding()
    private val formNavigation: FormNavigation by inject()
    private val args: ZipcodeArgs by viewArgs()

    override fun getTheme(): Int = DS.style.DsBottomSheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = EditorBottomSheetBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.commit {
            replace(binding.containerFragment.id, formNavigation.newInstance(args))
        }

        binding.buttonClose.setOnClickListener { dismiss() }
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet = dialog?.findViewById(GM.id.design_bottom_sheet) as FrameLayout?
        bottomSheet?.let {
            BottomSheetBehavior.from<FrameLayout?>(it).state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheet.layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
        }
    }

    companion object {
        fun newInstance(
            host: FragmentActivity,
            args: ZipcodeArgs
        ): Unit = EditorBottomSheet()
            .withArgs { putArguments(args) }
            .show(host.supportFragmentManager, host::class.simpleName)

        fun newInstance(
            host: Fragment,
            args: ZipcodeArgs
        ): Unit = EditorBottomSheet()
            .withArgs { putArguments(args) }
            .show(host.childFragmentManager, host::class.simpleName)
    }
}
