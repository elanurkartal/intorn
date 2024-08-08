package com.example.intorn

import SellingProcessAdapter
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.databinding.FragmentHomeBinding
import com.example.intorn.databinding.ReceiptAlertDialogBinding
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var articleDataArrayList: ArrayList<HomeModel>
    private lateinit var articleDataArrayListTemp: ArrayList<HomeModel>
    private lateinit var articleSellList: MutableList<SellingProcessModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var billRecyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var newList: ArrayList<BillAlertModel>
    private lateinit var sellingProcessAdapter: SellingProcessAdapter
    private var totalText = ""
    private var loginUser = ""
    private var selectedPaymentOption = "Bar" // Varsayılan olarak "Bar" seçeneği
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        sharedPreferences=requireContext().getSharedPreferences("UserInfo", MODE_PRIVATE)
        editor=sharedPreferences.edit()
        loginUser = sharedPreferences.getString("user_name","").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.products)
        billRecyclerView = view.findViewById(R.id.recyclerView)
        articleDataArrayListTemp = ArrayList()
        articleSellList = ArrayList()
        newList = ArrayList()
        articleDataArrayList = databaseHelper.getProducts() as ArrayList<HomeModel>

        setupNumberClickListeners()
        loadProductsData()
        loadSellingList()
        productOnClick()

        binding.textviewDelete.setOnClickListener {
            if (totalText.isNotEmpty()) {
                totalText = totalText.substring(0, totalText.length - 1)
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
        binding.textviewReturn.setOnClickListener {
            showReturnAlertDialog()
        }

        binding.textviewPlu.setOnClickListener{
            val string = binding.textViewTotal.text.toString()
            if(string.contains("X")) {
                val items = string.split("X")
                val quantity = items[0].toIntOrNull()
                if (quantity==null){
                    Toast.makeText(requireContext(),"error",Toast.LENGTH_LONG).show()
                }else{ //boyutuna bakarak yap.
                    val plu = items[1]
                    val stock = databaseHelper.getProductStock(plu)
                    val product = articleDataArrayList.find { it.name == databaseHelper.getProductName(plu) }
                    if (stock > 0 && stock > quantity) {
                        val article = articleSellList.find { it.name == databaseHelper.getProductName(plu) }
                        if (article != null) {
                            article.quantity += quantity
                        } else {
                            articleSellList.add(
                                SellingProcessModel(
                                    databaseHelper.getProductName(plu),
                                    databaseHelper.getProductPrice(plu).toDouble(),
                                    quantity,
                                    plu.toInt()
                                )
                            )
                        }
                        product!!.stock -= quantity
                        //articleDataArrayList[position] = homeModel
                        binding.textViewTotal.text = ""
                        totalText = ""

                        databaseHelper.updateProductStock(plu, stock)
                        homeAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(activity, "Not enough stock", Toast.LENGTH_SHORT).show()
                    }
                    loadSellingList()
                }
            }
        }
    }

    private fun loadSellingList() {
        val newList = articleSellList.map { SellingProcessDtoModel(it.name, it.price, it.quantity) }
        sellingProcessAdapter = SellingProcessAdapter(newList)
        billRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sellingProcessAdapter
        }

        sellingProcessAdapter.setOnClickListener { position, _ ->
            articleSellList[position].quantity -= 1
            articleDataArrayList.find { it.name == articleSellList[position].name }?. let { it.stock++ }
            homeAdapter.notifyDataSetChanged()
            if(articleSellList[position].quantity == 0){
                articleSellList.removeAt(position)
            }
            loadSellingList()
        }
    }

    private fun productOnClick() {
        homeAdapter.setOnClickListener(object : HomeAdapter.OnClickListener {
            override fun onClick(position: Int, model: HomeModel) {
                val productId = databaseHelper.getProductId(model.name).toString()
                val stock = databaseHelper.getProductStock(productId)
                val homeModel = articleDataArrayList[position]
                if (stock > 0 && homeModel.stock > 0) {
                    val article = articleSellList.find { it.name == homeModel.name }
                    if (article != null) {
                        article.quantity += 1
                    } else {
                        articleSellList.add(
                            SellingProcessModel(
                                homeModel.name,
                                homeModel.price.toDouble(),
                                1,
                                productId.toInt()
                            )
                        )
                    }
                    homeModel.stock -= 1
                    articleDataArrayList[position] = homeModel
                    databaseHelper.updateProductStock(productId, stock)
                    homeAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "Not enough stock", Toast.LENGTH_SHORT).show()
                }
                loadSellingList()
            }
        })
    }
    //bugunun ekmegi cıktıgı icin oldukca m

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
                } else if (textView.id == R.id.textview_comma) {
                    totalText += "X"
                }else {
                    totalText += number
                }
                binding.textViewTotal.text = totalText
            }
        }
        // EditText olan textViewTotal için KeyListener ayarlaması
        binding.textViewTotal.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Return (Enter) tuşuna basıldığında yapılacak işlemler
                showAlertDialog()
                return@OnKeyListener true // İşlemin tüketildiğini belirtmek için true döndür
            }
            false
        })
    }

    private fun loadProductsData() {
        homeAdapter = HomeAdapter(articleDataArrayList)
        recyclerView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 4, LinearLayoutManager.VERTICAL, false)
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
            .setSingleChoiceItems(
                options,
                options.indexOf(selectedPaymentOption)
            ) { _, which ->
                selectedOption = options[which]
            }
            .setPositiveButton("Select") { _, _ ->
                if (selectedOption.isNotEmpty()) {
                    selectedPaymentOption = selectedOption
                    handlePaymentOption(selectedOption)
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun handlePaymentOption(option: String) {
        when (option) {
            "Bar", "Card", "Other" -> {
                showAlertDialog()
            }
        }
        // Seçilen ödeme seçeneğini görsel olarak da güncelleyebilirsiniz
        binding.textViewTotal.text = "Selected Payment: $option"
        selectedPaymentOption = "Bar"
    }

    @SuppressLint("MissingInflatedId")
    private fun showAlertDialog() {
        if(articleSellList.isEmpty()){
            Toast.makeText(requireContext(),"Please Select Product", Toast.LENGTH_SHORT).show()
        }
        else{

            updateOrInsertSellingProcesses(selectedPaymentOption,loginUser)
            val customLayout =
                layoutInflater.inflate(R.layout.receipt_alert_dialog, binding.root, false)
            val alertBinding = ReceiptAlertDialogBinding.bind(customLayout)
            val billRecyclerView: RecyclerView = customLayout.findViewById(R.id.billAlert)
            newList = articleSellList.map {
                BillAlertModel(
                    it.name,
                    it.price * it.quantity,
                    (it.price / ((databaseHelper.getTaxesRate(
                        databaseHelper.getProductId(it.name).toString()
                    ) / 100) + 1)) * it.quantity
                )
            } as ArrayList<BillAlertModel>
            val billAlertAdapter = BillAlertAdapter(newList)
            billRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = billAlertAdapter
            }
            val totalGrossPrice: TextView = customLayout.findViewById(R.id.totalPrice)
            val totalPrice: TextView = customLayout.findViewById(R.id.bruttoPrice)
            val paymentOption: TextView = customLayout.findViewById(R.id.paymentOption)
            totalGrossPrice.text = "Total Gross Price: " + getTotalPrice()
            totalPrice.text =
                "Total Price: " + String.format(Locale.getDefault(), "%.1f", getTotalBruttoPrice())
            paymentOption.text = selectedPaymentOption
            updateProduct()
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setView(alertBinding.root)
                .setNegativeButton("Select") { dialog, which ->
                    dialog.dismiss()
                    articleSellList.clear()
                    sellingProcessAdapter.updateItems(arrayListOf())
                }

                .show()
        }
    }
    @SuppressLint("MissingInflatedId")
    private fun showReturnAlertDialog() {
        if(articleSellList.isEmpty()){
            Toast.makeText(requireContext(),"Please Select Product", Toast.LENGTH_SHORT).show()
        }
        else{

            updateOrInsertSellingProcesses(selectedPaymentOption,loginUser)
            val customLayout =
                layoutInflater.inflate(R.layout.receipt_alert_dialog, binding.root, false)
            val alertBinding = ReceiptAlertDialogBinding.bind(customLayout)
            val billRecyclerView: RecyclerView = customLayout.findViewById(R.id.billAlert)
            newList = articleSellList.map {
                BillAlertModel(
                    it.name,
                    -(it.price * it.quantity),
                    -((it.price / ((databaseHelper.getTaxesRate(
                        databaseHelper.getProductId(it.name).toString()
                    ) / 100) + 1)) * it.quantity)
                )
            } as ArrayList<BillAlertModel>
            val billAlertAdapter = BillAlertAdapter(newList)
            billRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = billAlertAdapter
            }
            val totalGrossPrice: TextView = customLayout.findViewById(R.id.totalPrice)
            val totalPrice: TextView = customLayout.findViewById(R.id.bruttoPrice)
            val paymentOption: TextView = customLayout.findViewById(R.id.paymentOption)
            totalGrossPrice.text = "Total Gross Price: " + getTotalPrice()
            totalPrice.text = "Total Price: " + String.format(Locale.getDefault(), "%.1f", getTotalBruttoPrice())
            paymentOption.text = selectedPaymentOption
            updateProductList()
            updateProduct()
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setView(alertBinding.root)
                .setNegativeButton("Select") { dialog, which ->
                    dialog.dismiss()
                    articleSellList.clear()
                    sellingProcessAdapter.updateItems(arrayListOf())
                }

                .show()
        }
    }

    private fun updateProductList(){
        for (product in articleSellList){
            articleDataArrayList.find { it.name == product.name}?. let { it.stock += product.quantity }
        }
        homeAdapter.notifyDataSetChanged()
    }

    private fun updateProduct(){
        for (product in articleDataArrayList){
            databaseHelper.updateProductStock(databaseHelper.getProductId(product.name).toString(),product.stock)
        }
    }

    private fun updateOrInsertSellingProcesses(selectedPaymentType: String,loginUserName: String) {
        for (article in articleSellList){
            databaseHelper.insertSellingProcess(
                selectedPaymentType,
                article.price.toString(),
                article.productId.toString(),
                article.quantity.toString(),
                databaseHelper.getUserIdFromUserName(loginUserName))
        }
        /*
        Log.v("adsgadsfasdfasdf",loginUserName)
        for (article in articleSellList) {
            if (databaseHelper.readSellingProcess(article.productId.toString(),
                    databaseHelper.getUserIdFromUserName(loginUserName).toString(),
                    selectedPaymentType
                )){
                databaseHelper.updateSellingProcess(
                    article.productId.toString(),
                    article.price.toString(),
                    databaseHelper.getUserIdFromUserName(loginUserName).toString(),
                    selectedPaymentType,
                    article.quantity
                )
            } else {
                databaseHelper.insertSellingProcess(
                    selectedPaymentType,
                    article.price.toString(),
                    article.productId.toString(),
                    article.quantity.toString(),
                   databaseHelper.getUserIdFromUserName(loginUserName)
                )
            }
        }
         */
    }

    private fun getTotalPrice(): Double {
        var total = 0.0
        for (article in newList) {
            total += article.amount
        }
        return total
    }

    private fun getTotalBruttoPrice(): Double {
        var brutto = 0.0
        for (article in newList) {
            brutto += article.brutto
        }
        return brutto
    }
}
 
 