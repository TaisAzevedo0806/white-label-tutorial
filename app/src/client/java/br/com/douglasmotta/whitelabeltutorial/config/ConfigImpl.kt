package br.com.douglasmotta.whitelabeltutorial.config

import android.view.View
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

class ConfigImpl @Inject constructor() : Config {

    override val addButtonVisibility: Int = View.GONE

}