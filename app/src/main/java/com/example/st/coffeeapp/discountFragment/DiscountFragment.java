package com.example.st.coffeeapp.discountFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.st.coffeeapp.QRGenerator;
import com.example.st.coffeeapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;


public class DiscountFragment extends Fragment {


    public DiscountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discount, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QRGenerator qrGenerator = new QRGenerator(getContext());
        ImageView imageQr = view.findViewById(R.id.imageQr);
        try {
            imageQr.setImageBitmap(qrGenerator.generateQr(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


}
