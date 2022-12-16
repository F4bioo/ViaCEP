package com.fappslab.viacep.design.dsmodal

import androidx.lifecycle.LifecycleOwner

@Suppress("unused")
fun LifecycleOwner.dsModal(
    block: DsModal.() -> Unit
): DsModal = DsModal().apply(block)
