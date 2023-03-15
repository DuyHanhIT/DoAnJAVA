package com.example.shopsneaker.adapter;


public class ShoesPagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
    public ShoesPagerAdapter(@androidx.annotation.NonNull androidx.fragment.app.FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }






    @androidx.annotation.NonNull
    @Override
    public androidx.fragment.app.Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new com.example.shopsneaker.Fragment.ShoesFragment(1);

            case 1:
                return new com.example.shopsneaker.Fragment.ShoesFragment(2);
            case 2:
                return new com.example.shopsneaker.Fragment.ShoesFragment(3);
            case 3:
                return new com.example.shopsneaker.Fragment.ShoesFragment(4);

            default:
                return new com.example.shopsneaker.Fragment.ShoesFragment(1);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
