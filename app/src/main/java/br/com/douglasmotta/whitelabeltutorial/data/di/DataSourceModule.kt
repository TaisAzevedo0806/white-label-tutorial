package br.com.douglasmotta.whitelabeltutorial.data.di

import br.com.douglasmotta.whitelabeltutorial.data.FirebaseProductDataSource
import br.com.douglasmotta.whitelabeltutorial.data.ProductDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Module
 *
 * Defines this class as a Dagger Module, to help Dagger to know which interface implementation to use for dependency injection.
 *
 *
 * @InstallIn(SingletonComponent::class)
 *
 * Defines that the instances created by this module must have the same lifecycle as the application. Will use the same instance
 * of the DataSource for all the application.
 *
 *
 * @Binds
 *
 * Annotate the function that provides the correct implementation of the interface for Dagger to inject.
 * If the interface has more than one implementation, just change the function parameter type.
 *
 */

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindProductDataSource(dataSource: FirebaseProductDataSource): ProductDataSource

}