package com.example.win22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.constraintlayout.widget.ConstraintLayout

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_splash_screen)
        GetBackgroundImage.setImage(constraintLayout, this)

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}
