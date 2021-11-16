package br.com.douglasmotta.whitelabeltutorial.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Module
 *
 * Defines this class as a Dagger Module. In this case, for instantiate Firebase dependencies.
 *
 *
 * @InstallIn(SingletonComponent::class)
 *
 * Defines that the instances created by this module must have the same lifecycle as the application. Will use the same instance
 * of the DataSource for all the application.
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

}