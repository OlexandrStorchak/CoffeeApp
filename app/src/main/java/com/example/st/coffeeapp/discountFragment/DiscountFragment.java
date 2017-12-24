package com.example.st.coffeeapp.discountFragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.st.coffeeapp.R;


public class DiscountFragment extends Fragment {


    private ImageView imageQr;
    private ProgressBar progressBar;

    public DiscountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discount, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageQr = view.findViewById(R.id.imageQr);
        progressBar = view.findViewById(R.id.discountProgress);
        new GenerateQRAsync(this).execute();


    }

    protected void setQR(Bitmap bitmap) {
        imageQr.setImageBitmap(bitmap);
        imageQr.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    protected void setProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        imageQr.setVisibility(View.GONE);
    }


}
