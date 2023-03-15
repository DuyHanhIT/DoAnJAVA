package com.example.shopsneaker.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.model.Sales;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SaleFragment extends Fragment {
    private View view;
    private android.widget.TextView txtSaleName,txtContentSale,txtpercentSale,txtBuyNow;
    private ImageView imageView;
    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull android.view.LayoutInflater inflater, @androidx.annotation.Nullable android.view.ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {

        view = inflater.inflate(com.example.shopsneaker.R.layout.fragment_sale,container,false);
        Init();
        Bundle bundle = getArguments();
        Sales sales = (Sales) bundle.get("objectSale");
        txtSaleName.setText(sales.getSalesname());
        txtContentSale.setText(sales.getContent());
        txtpercentSale.setText(sales.getPercent().toString()+" %");
        List<String> givenList = Arrays.asList("https://res.cloudinary.com/du7sfuuey/image/upload/v1671445707/BannerSale/2_xlwse1.png",
                                                "https://res.cloudinary.com/du7sfuuey/image/upload/v1671445707/BannerSale/1_txxnrp.png",
                                                "https://res.cloudinary.com/du7sfuuey/image/upload/v1671445706/BannerSale/3_ikf2tx.png",
                                                "https://res.cloudinary.com/du7sfuuey/image/upload/v1671445612/BannerSale/1_qkzkc8.png",
                                                "https://res.cloudinary.com/du7sfuuey/image/upload/v1671445536/BannerSale/1_w9sy9s.png");
        Random rand = new Random();

        Glide.with(this).load(givenList.get(rand.nextInt(givenList.size()))).into(imageView);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        txtBuyNow.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                android.content.Intent intent = new android.content.Intent(getActivity().getBaseContext(), com.example.shopsneaker.activity.SaleShoesActivity.class);
                intent.putExtra("saleid",sales.getSalesid());
                intent.putExtra("salename", sales.getSalesname());
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    private void Init() {
        txtSaleName= view.findViewById(com.example.shopsneaker.R.id.txtSaleName);
        txtContentSale= view.findViewById(com.example.shopsneaker.R.id.txtContentSale);
        txtpercentSale= view.findViewById(com.example.shopsneaker.R.id.txtpercentSale);
        imageView= view.findViewById(com.example.shopsneaker.R.id.imgSaless);
        txtBuyNow= view.findViewById(com.example.shopsneaker.R.id.btnBuyNow);
    }
}
