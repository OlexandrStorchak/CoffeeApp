package com.example.st.coffeeapp.mainActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.st.coffeeapp.R;
import com.example.st.coffeeapp.menuFragment.menuFragment;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    public static final String MENU_FRAGMENT_TAG = "menu";
    public static final String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showMenuFragment();


    }

    @Override
    public void showMenuFragment() {
        if (getSupportFragmentManager().findFragmentByTag(MENU_FRAGMENT_TAG) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainframe, new menuFragment(), MENU_FRAGMENT_TAG).commit();

        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainframe, getSupportFragmentManager()
                            .findFragmentByTag(MENU_FRAGMENT_TAG)).commit();

        }
    }
}
