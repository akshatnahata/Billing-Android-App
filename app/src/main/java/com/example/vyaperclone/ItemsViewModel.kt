package com.example.vyaperclone

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val repository: ItemsRepository
) : ViewModel() {

    init {
        getAllItems()
        getAllTransactions()
//         addItem(ItemsEntity("cola", "sale", "33", "as", 332, 231, 0.6f, 30))
//         addItem(ItemsEntity("car", "sale", "33", "as", 332, 231, 0.6f, 30))
//         addItem(ItemsEntity("book", "sale", "33", "as", 332, 231, 0.6f, 30))
//
//        addParty(PartyEntity("batman", "454", "343", 100))
//        addTransaction(
//             TransactionEntity(
//                 54,
//                 Constants.PURCHASE,
//                 "batman",
//                 "cola,car",
//                 "10,15",
//                 1000,
//                 500,
//                 1000
//             )
//         )
    }

    val selectedType: MutableState<Int> = mutableStateOf(0)

    var items: MutableState<List<ItemsEntity>> = mutableStateOf(listOf())
    var transcations: MutableState<List<TransactionEntity>> = mutableStateOf(listOf())
    var parties: MutableState<List<TransactionEntity>> = mutableStateOf(listOf())
    var totalToGet: MutableState<Long> = mutableStateOf(0)
    var totalToGetItem: MutableState<Long> = mutableStateOf(0)
    var totalSale: MutableState<Long> = mutableStateOf(0)
    var totalPurchase: MutableState<Long> = mutableStateOf(0)
    var totalToGive: MutableState<Long> = mutableStateOf(0)
    var totalToGiveItem: MutableState<Long> = mutableStateOf(0)
    var totalExpenses: MutableState<Long> = mutableStateOf(0)
    var searchQuery: MutableState<String> = mutableStateOf("")
    val updatedTransaction = MutableLiveData<TransactionEntity?>()
    val deletedTransactionBillNo = MutableLiveData<Int?>()
    private val _purchaseList = MutableLiveData<List<AddItems>>()
    val purchaseList: LiveData<List<AddItems>> = _purchaseList

    fun getAllItems() {
        repository.getAllItems().observeForever() {
            items.value = it
        }
    }

    fun getAllTransactions() {
        repository.getAllTransactions().observeForever() {
            transcations.value = it
            var sale: Long = 0;
            var pruchase: Long = 0;
            var toget: Long = 0;
            var togive: Long = 0;

            for (transaction in it) {
                if (transaction.total != null && transaction.type == Constants.SALE) {
                    sale += transaction.total!!
                    toget += (transaction.total ?: 0) - (transaction.received ?: 0)
                } else if (transaction.total != null  && transaction.type == Constants.PURCHASE) {
                    pruchase += transaction.total!!
                    togive += (transaction.total ?: 0) - (transaction.paidAmt ?: 0)
                }else if (transaction.total!= null && transaction.type == "Receive" || transaction.type == Constants.LENDIN){
                    toget += (transaction.total ?: 0)
                }
                else if (transaction.total!= null && transaction.type == "Pay"){
                    togive += (transaction.total ?: 0)
                }
            }

            totalToGive.value = togive
            totalToGet.value = toget
            totalSale.value = sale
            totalPurchase.value = pruchase
        }
    }

    fun getAllParties() {
        repository.getAllParties().observeForever() {
            transcations.value = it
        }

    }

    fun updateTransaction(transactionEntity: TransactionEntity) {


        viewModelScope.launch {
            repository.updateTransaction(transactionEntity)
        }
    }

    fun deleteTransactionByBillNo(billNo: Int) {
        viewModelScope.launch {
            repository.deleteTransactionByBillNo(billNo)
        }
    }


    fun addItem(itemsEntity: ItemsEntity) {
        viewModelScope.launch {
            repository.addItem(itemsEntity)
        }
    }

    fun addParty(partyEntity: TransactionEntity) {
        viewModelScope.launch {
            repository.addParty(partyEntity)
        }
    }

    fun changeSelectedType(type: Int) {
        selectedType.value = type
    }

    fun searchQueryChange(data: String) {
        searchQuery.value = data
    }

}