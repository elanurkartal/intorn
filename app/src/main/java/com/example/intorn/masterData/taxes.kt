package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentGroupBinding
import com.example.intorn.databinding.FragmentTaxesBinding
import java.util.Locale

class taxes : Fragment() {
    private lateinit var binding: FragmentTaxesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var taxesAdapter: Taxes_Adapter
    private lateinit var taxesArrayListTmp: ArrayList<Taxes_Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTaxesBinding.inflate(inflater, container, false)
        initClickListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.taxes_recyclerview)
        taxesArrayListTmp = ArrayList()

        // Verileri yükle ve RecyclerView'e bağla
        loadGroupData()

        // Grup silme işlemi için onClickDeleteItem dinleyicisi
        taxesAdapter.setOnClickDeleteItem { taxesData ->
            databaseHelper.deleteTaxes(taxesData.taxesName)
            // Veriyi güncelledikten sonra RecyclerView'yi yeniden yükle
            loadGroupData()
        }

        // Click listener için fonksiyonu çağır
        initClickListener()
    }

    private fun loadGroupData() {
        val items = databaseHelper.getTaxesName()
        taxesAdapter = Taxes_Adapter(items)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = taxesAdapter
        }
        binding.searchViewTaxes.onActionViewExpanded()
        binding.searchViewTaxes.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    taxesArrayListTmp.clear()
                    val searchText = p0!!.lowercase(Locale.getDefault())
                    if (searchText.isNotEmpty()) {
                        items.forEach {
                            if (it.taxesName.lowercase(Locale.getDefault()).contains(searchText)) {
                                taxesArrayListTmp.add(it)
                            }
                        }
                        taxesAdapter.updateItems(taxesArrayListTmp)
                    } else {
                        taxesArrayListTmp.clear()
                        taxesArrayListTmp.addAll(items)
                        taxesAdapter.updateItems(items)
                    }
                    return false
                }
            })
    }

    private fun initClickListener(){
        binding.addTaxesLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addTaxes)

        }
    }

}