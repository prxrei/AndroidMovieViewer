package it2161.assignment2.movieviewerparta_starter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login_screen.LoginNameInput
import kotlinx.android.synthetic.main.activity_login_screen.PasswordInput
import kotlinx.android.synthetic.main.activity_registration_screen.AdminNumInput
import kotlinx.android.synthetic.main.activity_registration_screen.EmailInput
import kotlinx.android.synthetic.main.activity_registration_screen.PemGrpInput
import kotlinx.android.synthetic.main.activity_registration_screen.RegisterBtn

class RegistrationScreen : AppCompatActivity() {

    private lateinit var registerbutton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_screen)

        registerbutton = RegisterBtn

        registerbutton.setOnClickListener{
            if (LoginNameInput.text.toString().isNotEmpty() && PasswordInput.text.toString().isNotEmpty() && EmailInput.text.toString().isNotEmpty() && AdminNumInput.text.toString().isNotEmpty() && PemGrpInput.text.toString().isNotEmpty()) {
                val myintent = Intent(this, VerificationCodeScreen::class.java)
                startActivity(myintent)
            }
            if (LoginNameInput.text.toString().isEmpty())
                LoginNameInput.error = "Field cannot be empty"
            if (PasswordInput.text.toString().isEmpty())
                PasswordInput.error = "Field cannot be empty"
            if (EmailInput.text.toString().isEmpty())
                EmailInput.error = "Field cannot be empty"
            if (AdminNumInput.text.toString().isEmpty())
                AdminNumInput.error = "Field cannot be empty"
            if (PemGrpInput.text.toString().isEmpty())
                PemGrpInput.error = "Field cannot be empty"
        }
    }
}