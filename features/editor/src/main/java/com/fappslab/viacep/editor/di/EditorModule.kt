package com.fappslab.viacep.editor.di

import com.fappslab.viacep.arch.koin.KoinLoad
import com.fappslab.viacep.editor.navigation.EditorNavigationImpl
import com.fappslab.viacep.navigation.EditorNavigation
import org.koin.dsl.module

object EditorModule : KoinLoad() {

    override val additionalModule = module {
        factory<EditorNavigation> { EditorNavigationImpl() }
    }
}
