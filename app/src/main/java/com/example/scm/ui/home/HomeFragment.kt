package com.example.scm.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.scm.adapter.HomeAdapter
import com.example.scm.data.model.MenuItems
import com.example.scm.databinding.FragmentHomeBinding
import com.example.scm.utils.helper.ArrayHelper

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: HomeAdapter
    private lateinit var activity: Activity

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*    val textView: TextView = binding.textHome
            homeViewModel.text.observe(viewLifecycleOwner, Observer {
                textView.text = it
            })*/


        initview()
        return root
    }

    private fun initview() {
        adapter = HomeAdapter(
            activity,
            ArrayHelper.getHomeTaskArray(),
            object : HomeAdapter.ClickCat {
                override fun onClick(homeData: ArrayList<MenuItems>, str: String) {

                }
            })
        binding.rvlist.adapter = adapter
        binding.rvlist.visibility = View.VISIBLE
        binding.rvlist.isNestedScrollingEnabled = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            activity = requireActivity()
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}