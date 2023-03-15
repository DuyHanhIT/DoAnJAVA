package com.example.shopsneaker.adapter;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.shopsneaker.Fragment.SaleFragment;
import com.example.shopsneaker.model.Sales;

import java.util.List;

public class SalePagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
    static List<Sales> salesList;

    public SalePagerAdapter(@androidx.annotation.NonNull FragmentActivity fragmentActivity, List<Sales> salesList) {
        super(fragmentActivity);
        this.salesList = salesList;
    }

    @androidx.annotation.NonNull
    @Override
    public androidx.fragment.app.Fragment createFragment(int position) {
        Sales sales = salesList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objectSale",sales);
        SaleFragment saleFragment= new SaleFragment();
        saleFragment.setArguments(bundle);
        return saleFragment;
    }

    @Override
    public int getItemCount() {
        if (salesList != null){
            return salesList.size();
        }
        return 0;
    }

}
