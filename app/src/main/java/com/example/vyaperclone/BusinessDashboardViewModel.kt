package com.example.vyaperclone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BusinessDashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Business Dashboard Fragment"
    }
    val text: LiveData<String> = _text
}