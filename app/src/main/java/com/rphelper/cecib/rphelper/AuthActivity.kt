package com.rphelper.cecib.rphelper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.util.ExtraConstants
import com.google.android.gms.common.Scopes
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class AuthActivity: FragmentActivity() {
    private val RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        startActivityForResult(buildSignInIntent( /*link=*/null), RC_SIGN_IN)
    }

    fun buildSignInIntent(link: String?): Intent? {
        val builder = AuthUI.getInstance().createSignInIntentBuilder()
                .setTheme(com.firebase.ui.auth.R.style.FirebaseUI)
                .setLogo(R.mipmap.ic_launcher)
                .setAvailableProviders(getProviders())
                .setIsSmartLockEnabled(false)

            val customLayout = AuthMethodPickerLayout.Builder(R.layout.auth_custom_layout)
                    .setGoogleButtonId(R.id.custom_google_signin_button)
                    .setEmailButtonId(R.id.custom_email_signin_button)
                   // .setTosAndPrivacyPolicyId(R.id.custom_tos_pp)
                    .build()
            builder.setTheme(R.style.AppTheme)
            builder.setAuthMethodPickerLayout(customLayout)

        if (link != null) {
            builder.setEmailLink(link)
        }
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null && auth.currentUser!!.isAnonymous) {
            builder.enableAnonymousUsersAutoUpgrade()
        }
        return builder.build()
    }

    private fun getProviders(): List<IdpConfig> {
        val selectedProviders: MutableList<IdpConfig> = ArrayList()
        //GoogleProvider
        selectedProviders.add(GoogleBuilder().setScopes(ArrayList()).build())


        //EmailProvider
        selectedProviders.add(EmailBuilder()
                    .setRequireName(false)
                    .setAllowNewAccounts(true)
                    .build())
        return selectedProviders
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data)
        }
    }

    private fun handleSignInResponse(resultCode: Int, data: Intent?) {
        val response = IdpResponse.fromResultIntent(data)

        // Successfully signed in
        if (resultCode == Activity.RESULT_OK) {
            startActivity(MainActivity.createIntent(this, response))
            finish()
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                Snackbar.make(currentFocus!!, "annulé", Snackbar.LENGTH_SHORT).show()
                return
            }
            if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                Snackbar.make(currentFocus!!, "pas de connection", Snackbar.LENGTH_SHORT).show()
                return
            }
            if (response.error!!.errorCode == ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT) {
                Snackbar.make(currentFocus!!, "ANONYMOUS_UPGRADE_MERGE_CONFLICT", Snackbar.LENGTH_SHORT).show()
            }
            if (response.error!!.errorCode == ErrorCodes.ERROR_USER_DISABLED) {
                Snackbar.make(currentFocus!!, "ERROR_USER_DISABLED", Snackbar.LENGTH_SHORT).show()
                return
            }
            Snackbar.make(currentFocus!!, "erreur inconnue", Snackbar.LENGTH_SHORT).show()
            Log.e("error", "Sign-in error: ", response.error)
        }
    }

    companion object{
        @JvmStatic
        fun createIntent(context: Context): Intent? {
            return Intent(context, AuthActivity::class.java)
        }
    }

}