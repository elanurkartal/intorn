package com.example.intorn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var homeAdapter: HomeAdapter
    private var totalText = ""
    private var selectedPaymentOption = "Bar" // Varsayılan olarak "Bar" seçeneği

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.products)

        setupNumberClickListeners()
        loadProductsData()

        binding.textviewDelete.setOnClickListener {
            if (totalText.isNotEmpty()){
                totalText = totalText.substring(0,totalText.length -1 )
                binding.textViewTotal.text = totalText
            }
        }

        binding.textviewPay.setOnClickListener {
            // Varsayılan olarak seçili seçeneği göster
            handlePaymentOption(selectedPaymentOption)
        }

        binding.textviewPay.setOnLongClickListener {
            showPaymentOptions()
            true // LongClickListener'ı tüketmek için true döndürüyoruz
        }
    }

    private fun setupNumberClickListeners() {
        // Burada tıklanabilir textview'leri tanımlayın ve onClickListener'larını ayarlayın
        val numberTextViews = arrayOf(
            binding.textview0, binding.textview1, binding.textview2, binding.textview3,
            binding.textview4, binding.textview5, binding.textview6, binding.textview7,
            binding.textview8, binding.textview9, binding.textviewC, binding.textviewComma
        )

        for (textView in numberTextViews) {
            textView.setOnClickListener {
                val number = textView.text.toString()
                if (textView.id == R.id.textviewC) {
                    totalText = ""
                } else {
                    totalText += number
                }
                binding.textViewTotal.text = totalText
            }
        }
    }

    private fun loadProductsData() {
        val items = databaseHelper.getProducts()
        homeAdapter = HomeAdapter(items)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }
    }

    private fun showPaymentOptions() {
        val options = arrayOf("Bar", "Card", "Other")
        var selectedOption = ""

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Payment Option")
            .setSingleChoiceItems(options, options.indexOf(selectedPaymentOption)) { dialog, which ->
                selectedOption = options[which]
            }
            .setPositiveButton("Select") { dialog, which ->
                if (selectedOption.isNotEmpty()) {
                    selectedPaymentOption = selectedOption
                    handlePaymentOption(selectedOption)
                }
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun handlePaymentOption(option: String) {
        // Seçilen ödeme seçeneğini işleyin (örneğin, bir şey yapabilirsiniz)
        when (option) {
            "Bar" -> {
                // 'Bar' seçeneğine tıklanınca yapılacak işlemler
                // Örneğin, "Bar" seçeneğine tıklandığında yapılacak işlemler buraya yazılabilir.
            }
            "Card" -> {
                // 'Card' seçeneğine tıklanınca yapılacak işlemler
                // Örneğin, "Card" seçeneğine tıklandığında yapılacak işlemler buraya yazılabilir.
            }
            "Other" -> {
                // 'Other' seçeneğine tıklanınca yapılacak işlemler
                // Örneğin, "Other" seçeneğine tıklandığında yapılacak işlemler buraya yazılabilir.
            }
        }

        // Seçilen ödeme seçeneğini görsel olarak da güncelleyebilirsiniz
        binding.textViewTotal.text = "Selected Payment: $option"
    }
}
