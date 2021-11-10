package br.com.douglasmotta.whitelabeltutorial.domain.usecase

import android.net.Uri
import br.com.douglasmotta.whitelabeltutorial.data.ProductRepository
import br.com.douglasmotta.whitelabeltutorial.domain.model.Product
import java.util.*

class CreateProductUseCaseImpl(
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