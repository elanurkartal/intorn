package com.example.intorn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.databinding.FragmentHomeBinding
import com.example.intorn.databinding.ReceiptAlertDialogBinding
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var articleDataArrayList: ArrayList<HomeModel>
    private lateinit var articleDataArrayListTemp: ArrayList<HomeModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var billRecyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var sellingProcessAdapter: SellingProcessAdapter

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
        billRecyclerView = view.findViewById(R.id.recyclerView)
        articleDataArrayListTemp = ArrayList()
        articleDataArrayList = databaseHelper.getProducts() as ArrayList<HomeModel>
        setupNumberClickListeners()
        loadProductsData()
        loadSellingList()
        productOnClick()


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

    private fun loadSellingList(){
        val items = databaseHelper.getSellingProcess()
        sellingProcessAdapter = SellingProcessAdapter(items)
        billRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sellingProcessAdapter
        }
    }

    private fun productOnClick() {
        homeAdapter.setOnClickListener(object : HomeAdapter.OnClickListener{
            override fun onClick(position: Int, model: HomeModel) {
                val productId = databaseHelper.getProductId(model.name).toString()
                if (databaseHelper.readSellingProcess(productId)){
                    databaseHelper.updateSellingProcess(productId,model.price)
                }
                else{
                    databaseHelper.insertSellingProcess(model.name,model.price,model.stock,productId)

                }
                loadSellingList()
            }
        })

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
        val newList = articleDataArrayList.sortedWith(compareBy { it.name})
        val newArrayList = java.util.ArrayList<HomeModel>()
        newArrayList.addAll(newList)
        homeAdapter = HomeAdapter(newArrayList)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }
        binding.layoutSearchview.setOnClickListener {
            binding.searchArticle.onActionViewExpanded()
        }
        binding.searchArticle.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(p0: String?): Boolean {
                    articleDataArrayListTemp.clear()
                    val searchText = p0!!.lowercase(Locale.getDefault())
                    if (searchText.isNotEmpty()) {
                        articleDataArrayList.forEach {
                            if (it.name.lowercase(Locale.getDefault()).contains(searchText)) {
                                articleDataArrayListTemp.add(it)
                            }
                        }
                       homeAdapter.updateItems(articleDataArrayListTemp)
                    } else {
                        articleDataArrayListTemp.clear()
                        articleDataArrayListTemp.addAll(articleDataArrayList)
                        homeAdapter.updateItems(articleDataArrayList)
                    }
                    return false
                }
            })
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
                showAlertDialog()
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

    fun showAlertDialog(){
        val customLayout =
            layoutInflater.inflate(R.layout.receipt_alert_dialog, binding.root, false)
        val alertBinding = ReceiptAlertDialogBinding.bind(customLayout)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(alertBinding.root)
        builder.show()
    }



}
