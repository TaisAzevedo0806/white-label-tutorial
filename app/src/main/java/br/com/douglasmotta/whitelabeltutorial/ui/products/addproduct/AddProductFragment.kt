package br.com.douglasmotta.whitelabeltutorial.ui.products.addproduct

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import br.com.douglasmotta.whitelabeltutorial.databinding.AddProductFragmentBinding
import br.com.douglasmotta.whitelabeltutorial.util.CurrencyTextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddProductFragment : BottomSheetDialogFragment() {

    private var _binding: AddProductFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddProductViewModel

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
        setListeners()
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
    }

}