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
class PurchaseSharedViewModel @Inject constructor(
    private val repository: ItemsRepository
) : ViewModel() {


    var listOfPurchase: MutableState<ArrayList<AddItems>> = mutableStateOf(arrayListOf())

    fun addTransaction(transactionEntity: TransactionEntity) {
        viewModelScope.launch {
            repository.addTransaction(transactionEntity)
        }
    }
    fun getReport(): LiveData<List<TransactionEntity>> {
        return repository.getAllTransactions()
    }

    fun addPurchase(id: Int, name: String, quantity: Int, unit: String, rate: Double, tax: Double) {
        val purchase = AddItems(id,name, quantity, unit,rate,tax)
        listOfPurchase.value.add(purchase)
    }

}