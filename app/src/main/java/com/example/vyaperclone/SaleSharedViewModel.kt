package com.example.vyaperclone

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaleSharedViewModel @Inject constructor(
    private val repository: ItemsRepository
) : ViewModel() {


    var listOfSale: MutableState<ArrayList<AddItems>> = mutableStateOf(arrayListOf())

    fun addTransaction(transactionEntity: TransactionEntity) {
        viewModelScope.launch {
            repository.addTransaction(transactionEntity)
        }
        Log.d("salesshared", transactionEntity.billedItemNames!!)
        Log.d("salesshared", transactionEntity.billedItemQuantity!!)
    }

    fun getReport(): LiveData<List<TransactionEntity>> {
        return repository.getAllTransactions()
    }


}