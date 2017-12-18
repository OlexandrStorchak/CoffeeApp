package com.example.st.coffeeapp.mainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.st.coffeeapp.R;
import com.example.st.coffeeapp.menuFragment.menuFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import static com.example.st.coffeeapp.Const.DISCOUNT_FRAGMENT_TAG;
import static com.example.st.coffeeapp.Const.MENU_FRAGMENT_TAG;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {



    public static final int RC_SIGN_IN = 13;
    private FirebaseAuth firebaseAuth;
    private boolean doubleBackToExitPressedOnce = false;
    ProgressBar mainProgressBar;
    Button buttonTryAgain;
    private android.support.v4.app.FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        firebaseAuth = FirebaseAuth.getInstance();
        mainProgressBar = findViewById(R.id.mainactivity_progress);
        buttonTryAgain = findViewById(R.id.mainactivity_button_tryagain);
        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonTryAgain.setVisibility(View.GONE);
                mainProgressBar.setVisibility(View.VISIBLE);
                signIn();
            }
        });

    }

    void showProgressBar(boolean show) {
        if (show) {
            mainProgressBar.setVisibility(View.VISIBLE);
        } else {
            mainProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            showMenuFragment();
            showProgressBar(false);
        } else {
            showProgressBar(true);
            signIn();
        }
    }

    void isLogin(boolean login) {
        if (login) {
            showMenuFragment();
            showProgressBar(false);
        } else {
            if (fragmentManager.findFragmentByTag(MENU_FRAGMENT_TAG) != null) {
                fragmentManager.beginTransaction().remove(fragmentManager
                        .findFragmentByTag(MENU_FRAGMENT_TAG)).commit();
                //Add button for reSignIn manually
            }
        }
    }

    @Override
    public void showMenuFragment() {
        if (fragmentManager.findFragmentByTag(MENU_FRAGMENT_TAG) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.mainframe, new menuFragment(), MENU_FRAGMENT_TAG).commit();

        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.mainframe, fragmentManager
                            .findFragmentByTag(MENU_FRAGMENT_TAG)).commit();

        }
    }

    void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        mainProgressBar.setVisibility(View.VISIBLE);
        buttonTryAgain.setVisibility(View.GONE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                signInGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, R.string.auth_failed_toast, Toast.LENGTH_SHORT).show();
                buttonTryAgain.setVisibility(View.VISIBLE);
                // ...
            }
        }
    }

    private void signInGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            isLogin(true);
                            buttonTryAgain.setVisibility(View.GONE);
                        } else {
                            // If sign in fails, display a message to the user.
                            mainProgressBar.setVisibility(View.GONE);
                            buttonTryAgain.setVisibility(View.VISIBLE);
                            isLogin(false);
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {

        if (fragmentManager.findFragmentByTag(DISCOUNT_FRAGMENT_TAG)!=null
                && fragmentManager.findFragmentByTag(DISCOUNT_FRAGMENT_TAG).isAdded()
                && !fragmentManager.findFragmentByTag(MENU_FRAGMENT_TAG).isAdded()) {
            //fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("discount")).commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainframe,fragmentManager.findFragmentByTag(MENU_FRAGMENT_TAG)).commit();

        }
            if (doubleBackToExitPressedOnce) {
                signOut();
                finish();
                return;
            }


        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.for_exit_click_again, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }


}
