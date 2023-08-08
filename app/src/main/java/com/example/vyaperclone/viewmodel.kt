package com.example.vyaperclone

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class viewmodel @Inject constructor(
    database: ItemsRepository
) : ViewModel() {



}