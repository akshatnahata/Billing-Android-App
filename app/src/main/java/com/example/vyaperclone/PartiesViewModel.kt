package com.example.vyaperclone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartiesViewModel @Inject constructor(
    private val repository: ItemsRepository
) : ViewModel() {

    fun addParty(partyEntity: TransactionEntity){
        viewModelScope.launch {
            repository.addParty(partyEntity)
        }
    }

}