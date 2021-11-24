package br.com.douglasmotta.whitelabeltutorial.config.di

import br.com.douglasmotta.whitelabeltutorial.config.Config
import br.com.douglasmotta.whitelabeltutorial.config.ConfigImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @Module
 *
 * Defines this class as a Dagger Module, to help Dagger to know which interface implementation to use for dependency injection.
 *
 *
 * @InstallIn(SingletonComponent::class)
 *
 * Defines that the instances created by this module must have the same lifecycle as its ViewModel.
 *
 * @Binds
 *
 * Annotate the function that provides the correct implementation of the interface for Dagger to inject.
 * If the interface has more than one implementation, just change the function parameter type.
 *
 * ConfigImpl
 *
 * We have two implementations for Config interface: client and admin. Dagger will use that related with the current build variant.
 *
 *
 */

@Module
@InstallIn(ViewModelComponent::class)
interface ConfigModule {

    @Binds
    fun bindConfig(config: ConfigImpl): Config

}