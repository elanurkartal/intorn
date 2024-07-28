package com.example.intorn.masterData.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentArticleBinding
import com.example.intorn.masterData.Group_adapter
import java.util.Locale

class article : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleArrayListTmp: ArrayList<ArticleModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentArticleBinding.inflate(inflater, container, false)
        initClickListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.article_recyclerview)
        articleArrayListTmp = ArrayList()

        loadArticleData()

        // Grup silme işlemi için onClickDeleteItem dinleyicisi
        articleAdapter.setOnClickDeleteItem { articleData ->
            databaseHelper.deleteArticle(articleData.name)
            // Veriyi güncelledikten sonra RecyclerView'yi yeniden yükle
            loadArticleData()
        }

        // Click listener için fonksiyonu çağır
        initClickListener()
    }
    private fun loadArticleData() {
        val items = databaseHelper.getArticles()
        articleAdapter = ArticleAdapter(items)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = articleAdapter
        }
        binding.searchViewArticle.onActionViewExpanded()
        binding.searchViewArticle.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    articleArrayListTmp.clear()
                    val searchText = p0!!.lowercase(Locale.getDefault())
                    if (searchText.isNotEmpty()) {
                        items.forEach {
                            if (it.name.lowercase(Locale.getDefault()).contains(searchText)) {
                                articleArrayListTmp.add(it)
                            }
                        }
                        articleAdapter.updateItems(articleArrayListTmp)
                    } else {
                        articleArrayListTmp.clear()
                        articleArrayListTmp.addAll(items)
                        articleAdapter.updateItems(items)
                    }
                    return false
                }
            })
    }

    private fun initClickListener(){
        binding.addArticleLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addArticle)
        }
    }
}
