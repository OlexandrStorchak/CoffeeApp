package com.example.st.coffeeapp.discountFragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.st.coffeeapp.QRGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;


public class GenerateQRAsync extends AsyncTask {
    private DiscountFragment discountFragment;
    private Bitmap bitmap = null;

    GenerateQRAsync(DiscountFragment discountFragment) {
        this.discountFragment = discountFragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        discountFragment.setProgressBar();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        QRGenerator qrGenerator = new QRGenerator(discountFragment.getContext());
        try {
            bitmap = qrGenerator.generateQr(firebaseAuth.getCurrentUser().getUid());
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return objects;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        discountFragment.setQR(bitmap);
    }
}
