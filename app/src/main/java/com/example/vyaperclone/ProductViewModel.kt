package com.example.vyaperclone

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val itemsRepository: ItemsRepository) :
    ViewModel() {

    fun getItems(): LiveData<List<ItemsEntity>> {
        return itemsRepository.getAllItems()
    }
}