package com.example.myloginapp

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.internal.BaseGmsClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.access_token_textview
import kotlinx.android.synthetic.main.activity_main.email_textview
import kotlinx.android.synthetic.main.activity_main.first_name_textview
import kotlinx.android.synthetic.main.activity_main.id_textview
import kotlinx.android.synthetic.main.activity_main.last_name_textview
import kotlinx.android.synthetic.main.activity_main.logout
import kotlinx.android.synthetic.main.activity_main.profile_picture_url_textview
import kotlinx.android.synthetic.main.activity_user_info.*


const val GOOGLE_RC_SIGN_IN = 123

class MainActivity : AppCompatActivity() {
    private var googleSignInClient : GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        google.setOnClickListener {
            val signInIntent = googleSignInClient?.signInIntent
            startActivityForResult(signInIntent, GOOGLE_RC_SIGN_IN)
        }
        logout.setOnClickListener { signOut() }
//        twitter.setOnClickListener { }
//        `in`.setOnClickListener { }
//        facebook.setOnClickListener { }

//        val acct = GoogleSignIn.getLastSignedInAccount(this)
//        if (acct != null) {
//            goToUserInfoActivity(acct.toString())
//        }
    }

    private fun goToUserInfoActivity(acct: String) {
        println("------------------------------------------------------------------")
        println(acct)
        println("------------------------------------------------------------------")
        val myIntent = Intent(this, UserInfo::class.java)
        startActivity(myIntent)
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        when(requestCode){
            GOOGLE_RC_SIGN_IN -> {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResultGoogle(task)
            }
        }

    }

    private fun handleSignInResultGoogle(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                    ApiException::class.java
            )
            val myIntent = Intent(this, UserInfo::class.java)
//            myIntent.putExtra("facebook_name", name)
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
//            startActivity(myIntent)
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

        googleSignInClient?.signOut()
                ?.addOnCompleteListener(this, OnCompleteListener<Void?> {
                    mainContainer.visibility = View.VISIBLE
                    userInfo.visibility = View.GONE
                })
    }
}