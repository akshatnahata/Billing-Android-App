package com.example.vyaperclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class UnitsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_units, container, false)
    }

    companion object {
        fun newInstance() = UnitsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val unitsAdapter = UnitsAdapter()
//        unitsRecyclerView.adapter = unitsAdapter
//        unitsRecyclerView.layoutManager = LinearLayoutManager(context)
    }


    override fun onResume() {
        super.onResume()

    }
}