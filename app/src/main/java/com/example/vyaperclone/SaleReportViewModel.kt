package com.example.vyaperclone

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SaleReportViewModel @Inject constructor(
    private var itemRepository: ItemsRepository
) : ViewModel() {

    fun getReport(): LiveData<List<TransactionEntity>> {
        return itemRepository.getAllTransactions()
    }

}