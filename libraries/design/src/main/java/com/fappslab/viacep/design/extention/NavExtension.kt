package com.fappslab.viacep.design.extention

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.fappslab.viacep.design.R

private val animations = NavOptions.Builder()
    .setEnterAnim(R.anim.ds_enter_anim)
    .setExitAnim(R.anim.ds_exit_anim)
    .setPopEnterAnim(R.anim.ds_pop_enter_anim)
    .setPopExitAnim(R.anim.ds_pop_exit_anim)
    .build()

fun NavController.navigateWithAnimations(
    destinationId: Int,
    animation: NavOptions = animations
) {
    navigate(destinationId, null, animation)
}

fun NavController.navigateWithAnimations(
    directions: NavDirections,
    animation: NavOptions = animations
) {
    navigate(directions, animation)
}
