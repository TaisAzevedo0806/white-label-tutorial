package br.com.douglasmotta.whitelabeltutorial.ui.addproduct

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.douglasmotta.whitelabeltutorial.databinding.AddProductFragmentBinding
import br.com.douglasmotta.whitelabeltutorial.util.CurrencyTextWatcher
import br.com.douglasmotta.whitelabeltutorial.util.PRODUCT_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
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
class AddProductFragment : BottomSheetDialogFragment() {

    private var _binding: AddProductFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddProductViewModel by viewModels()

    private var imageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
        binding.imageProduct.setImageURI(uri)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = AddProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeVMEvents()
        setListeners()
    }

    private fun observeVMEvents() {
        viewModel.imageUriErrorResId.observe(viewLifecycleOwner) { drawableResId -> binding.imageProduct.setBackgroundResource(drawableResId) }
        viewModel.descriptionFieldErrorResId.observe(viewLifecycleOwner) { stringResId -> binding.inputLayoutDescription.setError(stringResId) }
        viewModel.priceFieldErrorResId.observe(viewLifecycleOwner) { stringResId -> binding.inputLayoutPrice.setError(stringResId) }

        viewModel.productCreated.observe(viewLifecycleOwner) { product ->
            findNavController().run {
                previousBackStackEntry?.savedStateHandle?.set(PRODUCT_KEY, product)
                popBackStack()
            }
        }
    }

    private fun setListeners() {
        binding.imageProduct.setOnClickListener { pickImage() }
        binding.buttonAddProduct.setOnClickListener { addProduct() }
        binding.inputPrice.run { addTextChangedListener(CurrencyTextWatcher(this)) }
    }

    private fun pickImage() {
        getContent.launch("image/*")
    }

    private fun addProduct() {
        val description = binding.inputDescription.text.toString()
        val price = binding.inputPrice.text.toString()

        viewModel.createProduct(description, price, imageUri)
    }

    private fun TextInputLayout.setError(stringResId: Int?) {
        error = if (stringResId != null) getString(stringResId) else null
    }

}