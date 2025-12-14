package com.example.scm.ui.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.scm.R
import com.example.scm.adapter.TaskStatusAdapter
import com.example.scm.data.model.MenuItems
import com.example.scm.databinding.FragmentTasklistBinding
import com.example.scm.utils.helper.ArrayHelper

class TaskListFragment : Fragment() {

    private lateinit var taskListViewModel: TaskListViewModel
    private var _binding: FragmentTasklistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskListViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(TaskListViewModel::class.java)

        _binding = FragmentTasklistBinding.inflate(inflater, container, false)
        val root: View = binding.root

       /* val textView: TextView = binding.textDashboard
        taskListViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        initview()
        return root
    }

    private fun initview() {
        val adapter =
            TaskStatusAdapter(requireActivity(), ArrayHelper.getTaskStatusArray(), object : TaskStatusAdapter.ClickCat {
                override fun onClick(catList: MenuItems, vararg views: View) {
               /*     val bundle = Bundle()
                    //     detailIntent.putExtra(ProductDetail.DATA, catList)
                    bundle.putString("title", catList.itemName)

                    context.findNavController(R.id.nav_host_fragment)
                        .navigate(R.id.navigation_home, bundle)*/
                }
            })
        _binding!!.itemRv.adapter = adapter
        _binding!!.itemRv.isNestedScrollingEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}