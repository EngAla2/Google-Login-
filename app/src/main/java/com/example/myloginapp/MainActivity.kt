package com.example.myloginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*


const val GOOGLE_RC_SIGN_IN = 123

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var callbackManager = CallbackManager.Factory.create();
        try {
            facebook.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        println("pass")
                    }

                    override fun onCancel() {
                        println("cancel")
                    }

                    override fun onError(exception: FacebookException) {
                        println("error")
                    }
                })
        }catch (e:Exception){
            println("--------------------------$e-----------------------")
        }



            logout.setOnClickListener { signOut() }
            facebook.setOnClickListener { }


        }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){

        }

    }

    private fun handleSignInResultFacebook(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully

            val googleId = account?.id ?: ""
            id_textview.text = googleId

            val googleFirstName = account?.givenName ?: ""
            first_name_textview.text = googleFirstName

            val googleLastName = account?.familyName ?: ""
            last_name_textview.text = googleLastName

            val googleEmail = account?.email ?: ""
            email_textview.text = googleEmail

            val googleProfilePicURL = account?.photoUrl.toString()
            profile_picture_url_textview.text = googleProfilePicURL

            val googleIdToken = account?.idToken ?: ""
            access_token_textview.text = googleIdToken

            mainContainer.visibility = View.GONE
            userInfo.visibility = View.VISIBLE
        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
            // The ApiException status code indicates the detailed failure reason.
            println("------------------------------------------------------------------")
            println(e.toString())
            println("------------------------------------------------------------------")

        }
    }
    private fun signOut() {

    }
}