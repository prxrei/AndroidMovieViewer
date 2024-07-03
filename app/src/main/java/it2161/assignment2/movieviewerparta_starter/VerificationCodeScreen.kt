package it2161.assignment2.movieviewerparta_starter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_verification_code_screen.VerificationCodeInput
import kotlinx.android.synthetic.main.activity_verification_code_screen.VerifyBtn

class VerificationCodeScreen : AppCompatActivity() {

    private lateinit var verifybutton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code_screen)

        val actionbar = supportActionBar

        actionbar?.title = "MovieViewerPartA_235008L"

        actionbar?.setDisplayHomeAsUpEnabled(true)

        verifybutton = VerifyBtn

        verifybutton.setOnClickListener {
            if (VerificationCodeInput.text.toString() == "1234") {
                val myintent = Intent(this, LoginScreen::class.java)
                showToast("Code Verified")
                startActivity(myintent)
            }
            if (VerificationCodeInput.text.toString().isEmpty())
                VerificationCodeInput.error = "Field cannot be empty"
            if (VerificationCodeInput.text.toString().isNotEmpty() && VerificationCodeInput.text.toString() != "1234")
                showToast("Code Error")
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed(){
        super.onBackPressed()
        finish()
    }

}