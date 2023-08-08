package com.example.vyaperclone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    fun addExpense(expenseEntity: ExpenseEntity) {
        viewModelScope.launch {
            itemsRepository.addExpense(expenseEntity)
        }
    }

}