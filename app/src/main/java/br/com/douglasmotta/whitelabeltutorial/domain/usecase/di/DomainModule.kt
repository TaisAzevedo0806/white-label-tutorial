package br.com.douglasmotta.whitelabeltutorial.domain.usecase.di

import br.com.douglasmotta.whitelabeltutorial.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @Module
 *
 * Define this class as a Dagger Module, to help Dagger to know which interface implementation to use for dependency injection.
 *
 *
 * @InstallIn(ViewModelComponents::class)
 *
 * Defines that the dependencies created by this module must have the same lifecycle as its ViewModel.
 *
 *
 * @Binds
 *
 * Annotate the function that provides the correct implementation of the interface for Dagger to inject.
 * If the interface has more than one implementation, just change the function parameter type.
 *
 */

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindCreateProductUseCase(useCase: CreateProductUseCaseImpl): CreateProductUseCase

    @Binds
    fun bindGetProductsUseCase(useCase: GetProductsUseCaseImpl): GetProductsUseCase

    @Binds
    fun bindUploadProductImageUseCase(useCase: UploadProductImageUseCaseImpl): UploadProductImageUseCase

}