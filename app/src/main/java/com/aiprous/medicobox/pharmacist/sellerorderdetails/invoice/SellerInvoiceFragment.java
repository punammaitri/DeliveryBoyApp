package com.aiprous.medicobox.pharmacist.sellerorderdetails.invoice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiprous.medicobox.R;

import butterknife.ButterKnife;


public class SellerInvoiceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller_invoice, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

    }
}