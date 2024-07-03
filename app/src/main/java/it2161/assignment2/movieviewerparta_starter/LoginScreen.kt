package it2161.assignment2.movieviewerparta_starter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import kotlinx.android.synthetic.main.activity_login_screen.LoginButton
import kotlinx.android.synthetic.main.activity_login_screen.RegisterButton
import kotlinx.android.synthetic.main.activity_login_screen.*

class LoginScreen : AppCompatActivity() {

    private lateinit var loginbutton : Button
    private lateinit var registerbutton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        loginbutton = LoginButton
        registerbutton = RegisterButton

        loginbutton.setOnClickListener{
            if (LoginNameInput.text.toString() == "testuser" && PasswordInput.text.toString() == "testuser") {
                val myintent = Intent(this, SimpleViewListOfMoviesActivity::class.java)
                startActivity(myintent)
            }
            if (LoginNameInput.text.toString().isEmpty())
                LoginNameInput.error = "Field cannot be empty"
            if (PasswordInput.text.toString().isEmpty())
                PasswordInput.error = "Field cannot be empty"
            //Bottom commented are error checks for wrong login details
            //if (LoginNameInput.text.toString().isNotEmpty() && LoginNameInput.text.toString() != "testuser")
                //LoginNameInput.error = "Wrong Login Name"
            //if (PasswordInput.text.toString().isNotEmpty() && PasswordInput.text.toString() != "testuser")
                //PasswordInput.error = "Wrong Password"
        }
        registerbutton.setOnClickListener{
            val myintent = Intent(this, RegistrationScreen::class.java)
            startActivity(myintent)
        }
    }
}