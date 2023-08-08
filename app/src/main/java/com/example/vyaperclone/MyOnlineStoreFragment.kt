package com.example.vyaperclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vyaperclone.databinding.FragmentMenuItemsBinding
import com.example.vyaperclone.databinding.FragmentMyOnlineStoreBinding

class MyOnlineStoreFragment : Fragment() {

    private lateinit var binding: FragmentMyOnlineStoreBinding

    companion object {
        fun newInstance() = MyOnlineStoreFragment()
    }

    private lateinit var viewModel: MyOnlineStoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_online_store, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyOnlineStoreViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateOnlineStore.setOnClickListener {
            val action = MyOnlineStoreFragmentDirections.actionNavOnlineStoreToProductFragment()
            findNavController().navigate(action)
        }
    }

}