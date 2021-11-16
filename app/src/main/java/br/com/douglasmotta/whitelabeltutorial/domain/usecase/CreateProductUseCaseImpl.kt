package br.com.douglasmotta.whitelabeltutorial.domain.usecase

import android.net.Uri
import br.com.douglasmotta.whitelabeltutorial.data.ProductRepository
import br.com.douglasmotta.whitelabeltutorial.domain.model.Product
import java.util.*
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

class CreateProductUseCaseImpl @Inject constructor(
    private val uploadProductImageUseCase: UploadProductImageUseCase,
    private val productRepository: ProductRepository
) : CreateProductUseCase {

    override suspend fun invoke(description: String, price: Double, imageUri: Uri): Product {
        return try {

            val imageUrl = uploadProductImageUseCase(imageUri)

            // Using try with return, will always return the last line of the code's block
            productRepository.createProduct(Product(UUID.randomUUID().toString(), description, price, imageUrl))

        } catch (e: Exception) {
            throw e
        }
    }

}