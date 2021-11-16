package br.com.douglasmotta.whitelabeltutorial.domain.usecase

import br.com.douglasmotta.whitelabeltutorial.data.ProductRepository
import br.com.douglasmotta.whitelabeltutorial.domain.model.Product
import javax.inject.Inject

/**
 * @Inject constructor
 *
 * Teach Dagger how to build the dependency for the injection of this class.
 *
 * If one of the constructor parameters implements a interface, a DomainModule must be created to tell to Dagger which
 * implementation of that interface must be used on dependency injection.
 *
 * If the parameter does not implement a interface, just use @Injection constructor.
 *
 */


class GetProductsUseCaseImpl @Inject constructor(private val productRepository: ProductRepository) : GetProductsUseCase {

    override suspend fun invoke(): List<Product> {
        return productRepository.getProducts()
    }

}