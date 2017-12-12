package com.example.st.coffeeapp.mainActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.st.coffeeapp.R;
import com.example.st.coffeeapp.menuFragment.menuFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainframe, new menuFragment(), "menu").commit();


    }
}
