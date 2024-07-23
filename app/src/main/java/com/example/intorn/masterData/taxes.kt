package com.example.intorn.masterData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentGroupBinding
import com.example.intorn.databinding.FragmentTaxesBinding

class taxes : Fragment() {
    private lateinit var binding: FragmentTaxesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var taxesAdapter: Taxes_Adapter

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
    }

    private fun initClickListener(){
        binding.addTaxesLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addTaxes)

        }
    }

}