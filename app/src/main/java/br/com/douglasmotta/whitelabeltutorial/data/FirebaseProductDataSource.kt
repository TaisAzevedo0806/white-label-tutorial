package br.com.douglasmotta.whitelabeltutorial.data

import android.net.Uri
import br.com.douglasmotta.whitelabeltutorial.BuildConfig
import br.com.douglasmotta.whitelabeltutorial.domain.model.Product
import br.com.douglasmotta.whitelabeltutorial.util.COLLECTION_PRODUCTS
import br.com.douglasmotta.whitelabeltutorial.util.COLLECTION_ROOT
import br.com.douglasmotta.whitelabeltutorial.util.STORAGE_IMAGES
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

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

class FirebaseProductDataSource @Inject constructor(
    firebaseFirestore: FirebaseFirestore,
    firebaseStorage: FirebaseStorage
) : ProductDataSource {

    /**
     * Firestore's database structure:
     *
     * COLLECTION/DOCUMENT/COLLECTION/DOCUMENT/OBJECT
     *
     * data/car/products/timestamp/productA
     * data/bike/products/timestamp/productB
     *
     */

    private val documentReference = firebaseFirestore.document(
        "$COLLECTION_ROOT/${BuildConfig.FIREBASE_FLAVOR_COLLECTION}/"
    )

    private val storageReference = firebaseStorage.reference

    override suspend fun getProducts(): List<Product> {

        return suspendCoroutine { continuation ->

            val productsReference = documentReference.collection(COLLECTION_PRODUCTS)

            productsReference.get()
                .addOnSuccessListener { documents ->

                    val products = mutableListOf<Product>()

                    for (document in documents) {
                        document.toObject(Product::class.java).run {
                            products.add(this)
                        }
                    }

                    continuation.resumeWith(Result.success(products))

                }
                .addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }

    /**
     * Firebase's storage structure:
     *
     * images/car/randomKey
     * images/bike/randomKey
     *
     */

    override suspend fun uploadProductImage(imageUri: Uri): String {
        return suspendCoroutine { continuation ->

            val randomKey = UUID.randomUUID()
            val childReference =
                storageReference.child("$STORAGE_IMAGES/${BuildConfig.FIREBASE_FLAVOR_COLLECTION}/$randomKey")

            childReference.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->

                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        continuation.resumeWith(Result.success(uri.toString()))
                    }

                }
                .addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }

        }
    }

    override suspend fun createProduct(product: Product): Product {
        return suspendCoroutine { continuation ->
            documentReference
                .collection(COLLECTION_PRODUCTS)
                .document(System.currentTimeMillis().toString())
                .set(product)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(product))
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }
}