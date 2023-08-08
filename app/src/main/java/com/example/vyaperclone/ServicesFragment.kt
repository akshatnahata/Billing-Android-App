package com.example.vyaperclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ServicesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services, container, false)
    }

    companion object {
        fun newInstance() = ServicesFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val servicesAdapter = ServicesAdapter()
//        servicesRecyclerView.adapter = servicesAdapter
//        servicesRecyclerView.layoutManager = LinearLayoutManager(context)
    }


}