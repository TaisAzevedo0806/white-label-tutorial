package br.com.douglasmotta.whitelabeltutorial.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import br.com.douglasmotta.whitelabeltutorial.R
import br.com.douglasmotta.whitelabeltutorial.databinding.FragmentProductsBinding
import br.com.douglasmotta.whitelabeltutorial.domain.model.Product
import br.com.douglasmotta.whitelabeltutorial.util.PRODUCT_KEY
import dagger.hilt.android.AndroidEntryPoint

/**
 * @AndroidEntryPoint
 *
 * Informs Dagger that this Activity or Fragment will use DI (Dependency Injection).
 *
 *
 * by viewModels()
 *
 * Dagger will create a instance of the view model.
 *
 */

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels()

    private val productsAdapter = ProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setListeners()
        observeNavBackStack()
        observeVMEvents()

        getProducts()
    }

    private fun setRecyclerView() {
        binding.recyclerProducts.run {
            // All items has the same size
            setHasFixedSize(true)
            adapter = productsAdapter
        }
    }

    private fun setListeners() {
        with(binding) {
            binding.fabAdd.setOnClickListener { findNavController().navigate(R.id.action_productsFragment_to_addProductFragment) }
            swipeProducts.setOnRefreshListener { getProducts() }
        }

    }

    private fun getProducts() { viewModel.getProducts() }

    private fun observeNavBackStack() {

        findNavController().run {
            val navBackStackEntry = getBackStackEntry(R.id.productsFragment)
            val savedStateHandle = navBackStackEntry.savedStateHandle

            val observer = getOnResumeEventObserver(savedStateHandle)
            navBackStackEntry.lifecycle.addObserver(observer)

            // Removes the event observer to avoid memory leaks
            viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) navBackStackEntry.lifecycle.removeObserver(observer)
            })
        }

    }

    private fun getOnResumeEventObserver(savedStateHandle: SavedStateHandle) = LifecycleEventObserver { _, event ->

        if (event == Lifecycle.Event.ON_RESUME && savedStateHandle.contains(PRODUCT_KEY)) {
            val oldList = productsAdapter.currentList
            val newList = oldList.toMutableList().apply { add(savedStateHandle.get(PRODUCT_KEY)) }

            productsAdapter.submitList(newList)
            binding.recyclerProducts.smoothScrollToPosition(newList.size - 1)
            savedStateHandle.remove<Product>(PRODUCT_KEY)
        }

    }

    private fun observeVMEvents() {
        viewModel.productsData.observe(viewLifecycleOwner) { products ->
            binding.swipeProducts.isRefreshing = false
            productsAdapter.submitList(products)
        }

        viewModel.addButtonVisibilityData.observe(viewLifecycleOwner) { visibility -> binding.fabAdd.visibility = visibility }
    }

    override fun onDestroyView() {
        // Following the documentation, the _binding variable must be cleared when the view is destroyed
        _binding = null
        super.onDestroyView()
    }
}