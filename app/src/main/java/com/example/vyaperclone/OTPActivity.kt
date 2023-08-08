package com.example.vyaperclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.vyaperclone.databinding.ActivityOtpactivityBinding
import com.example.vyaperclone.databinding.FragmentMenuItemsBinding

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var number: String = "964"

        if (intent != null && intent.extras != null) {
            val n1 = intent.getStringExtra("number")
            number = n1.toString()
        }
        binding.tvEnterOtp.text = "Otp sent to $number."

        otpGenerator()

        binding.btnSubmit.setOnClickListener {
            if (isDataValid()) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun otpGenerator() {
        Handler().postDelayed({
            binding.etEnterOtp.setText("1940")
        }, 3000)
    }

    private fun isDataValid(): Boolean {

        if (binding.etEnterOtp.text.toString().length != 4) {
            binding.etEnterOtp.error = "Enter valid OTP"
            return false
        }
        return true
    }
}