package com.example.vyaperclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.vyaperclone.databinding.ActivityAddPurchaseBinding
import com.example.vyaperclone.databinding.ActivityHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddPurchaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPurchaseBinding


//    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_add_purchase)

        binding.btnSave.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            val view = LayoutInflater.from(applicationContext).inflate(
                R.layout.bottom_sheet, findViewById(R.id.llBottomConatainer)
            )
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.setCanceledOnTouchOutside(true)
            bottomSheetDialog.show()
        }
    }
}