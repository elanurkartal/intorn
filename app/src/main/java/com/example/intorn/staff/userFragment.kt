package com.example.intorn.staff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.DatabaseHelper
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.databinding.FragmentUserBinding
import java.util.Locale

class userFragment : Fragment() {
    private lateinit var binding:FragmentUserBinding
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var usersArrayListTmp: ArrayList<UserModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentUserBinding.inflate(layoutInflater,container,false)
        binding.addStaffLinearLayout.setOnClickListener {
            MainActivity.navController.navigate(R.id.addUserFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper= DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.staff_recyclerview)
        usersArrayListTmp = ArrayList()

        loadUserData()

        // Grup silme işlemi için onClickDeleteItem dinleyicisi
        usersAdapter.setOnClickDeleteItem { userData ->
            databaseHelper.deleteUser(userData.userName)
            // Veriyi güncelledikten sonra RecyclerView'yi yeniden yükle
            loadUserData()
        }
        usersAdapter.setOnClickUpdateItem(object : UsersAdapter.OnClickListener{
            override fun onClick(position: Int, model: UserModel) {
                val action = userFragmentDirections.actionUserFragmentToUserUpdateFragment(model)
                findNavController().navigate(action)
                //MainActivity.navController.navigate(R.id.userUpdateFragment)
            }
        })
    }

    private fun loadUserData() {
        val items = databaseHelper.getUsers()
        usersAdapter = UsersAdapter(items)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }

        binding.searchViewStaff.onActionViewExpanded()
        binding.searchViewStaff.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    usersArrayListTmp.clear()
                    val searchText = p0!!.lowercase(Locale.getDefault())
                    if (searchText.isNotEmpty()) {
                        items.forEach {
                            if (it.userName.lowercase(Locale.getDefault()).contains(searchText)) {
                                usersArrayListTmp.add(it)
                            }
                        }
                        usersAdapter.updateItems(usersArrayListTmp)
                    } else {
                        usersArrayListTmp.clear()
                        usersArrayListTmp.addAll(items)
                        usersAdapter.updateItems(items)
                    }
                    return false
                }
            })
    }


}