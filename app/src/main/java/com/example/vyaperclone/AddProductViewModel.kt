package com.example.vyaperclone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: ItemsRepository
) : ViewModel() {

    fun addItem(itemsEntity: ItemsEntity) {
        viewModelScope.launch {
            repository.addItem(itemsEntity)
        }
    }


    fun updateItem(itemsEntity: ItemsEntity) {
        viewModelScope.launch {
            repository.updateItem(itemsEntity)
        }
    }


}