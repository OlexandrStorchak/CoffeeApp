package com.example.st.coffeeapp.menuFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.st.coffeeapp.R;
import com.example.st.coffeeapp.discountFragment.DiscountFragment;

import static com.example.st.coffeeapp.Const.DISCOUNT_FRAGMENT_TAG;


public class menuFragment extends Fragment implements View.OnClickListener {


    public menuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, viewGroup, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton btnCall = view.findViewById(R.id.btn_callphone);
        ImageButton btnDiscount = view.findViewById(R.id.btn_discount);
        btnCall.setOnClickListener(this);
        btnDiscount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_callphone:
                Toast.makeText(view.getContext(), "Call button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_discount:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,
                        new DiscountFragment(), DISCOUNT_FRAGMENT_TAG).addToBackStack(null).commit();
                break;
        }

    }
}
