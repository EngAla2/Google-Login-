package com.example.myloginapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_info.*
class UserInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)


        val Id = intent.getStringExtra("id")
        val FirstName = intent.getStringExtra("first_name")
        val LastName = intent.getStringExtra("last_name")
        val Picture = intent.getStringExtra("picture")
        val Email = intent.getStringExtra("email")
        val AccessToken = intent.getStringExtra("access_token")


        id_textview.text = Id
        first_name_textview.text = FirstName
        last_name_textview.text = LastName
        profile_picture_url_textview.text = Picture
        email_textview.text = Email
        access_token_textview.text = AccessToken
    }
}