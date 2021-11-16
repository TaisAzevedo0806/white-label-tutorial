package br.com.douglasmotta.whitelabeltutorial.data

import android.net.Uri
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

class ProductRepository @Inject constructor(private val dataSource: ProductDataSource) {

    suspend fun getProducts(): List<Product> = dataSource.getProducts()

    suspend fun uploadProductImage(imageUri: Uri): String = dataSource.uploadProductImage(imageUri)

    suspend fun createProduct(product: Product): Product = dataSource.createProduct(product)

}