package com.example.shopsneaker.Fragment;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.shopsneaker.R.layout.fragment_shoessale;

import android.view.View;

import androidx.appcompat.widget.SearchView;

import com.example.shopsneaker.activity.SaleShoesActivity;
import com.example.shopsneaker.model.Sales;
import com.example.shopsneaker.retrofit.ApiService;

public class ShoesSalesFragment extends androidx.fragment.app.Fragment{
    int i;
    androidx.recyclerview.widget.RecyclerView recyclerView;
    ApiService apiBanGiay;
    io.reactivex.rxjava3.disposables.CompositeDisposable compositeDisposable = new io.reactivex.rxjava3.disposables.CompositeDisposable();
    java.util.List<com.example.shopsneaker.model.Shoes> shoesList;
    SearchView searchView;
    int sort,saleid;
    android.widget.ProgressBar progressBarShoesSale;

    private com.example.shopsneaker.adapter.ShoesAdapter shoesAdapter;
    com.example.shopsneaker.model.SearchHistoryModel searchHistoryModel;
    android.widget.TextView txtcountsp, txtNotifyShoesSale;

    public ShoesSalesFragment(int sort) {
        this.sort = sort;
    }


    public ShoesSalesFragment() {
    }
    @Override
    public void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull android.view.LayoutInflater inflater, @androidx.annotation.Nullable android.view.ViewGroup container, @androidx.annotation.Nullable android.os.Bundle savedInstanceState) {

            return inflater.inflate(fragment_shoessale,container,false);

        //android.widget.Toast.makeText(getContext(), String.valueOf(saleid), android.widget.Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiBanGiay = com.example.shopsneaker.retrofit.RetrofitClient.getInstance(com.example.shopsneaker.utils.Utils.BASE_URL).create(ApiService.class);

        android.os.Bundle bundle = getArguments();
        if(bundle != null) {
            Sales sv = (Sales)
                    bundle.getSerializable("object_sale");
            saleid = sv.getSalesid();

        }

       Init(view);

        getData();
    }
    @Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull android.view.Menu menu, @androidx.annotation.NonNull android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(com.example.shopsneaker.R.menu.menu,menu);
//       searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
//       searchView.setIconified(true);
        android.app.SearchManager searchManager = (android.app.SearchManager) getActivity().getSystemService(android.content.Context.SEARCH_SERVICE);
        searchView = (androidx.appcompat.widget.SearchView) menu.findItem(com.example.shopsneaker.R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                shoesAdapter.getFilter().filter(query);
                int countSP= shoesAdapter.getItemCount();
                txtcountsp.setText("("+countSP+" sản phẩm)");
                String keyword = query.toString();
                if (shoesAdapter.getItemCount()>0) {
                    int accountid = com.example.shopsneaker.utils.Utils.user_current.getAccountid();
                    compositeDisposable.add(apiBanGiay.SearchHistory(accountid,keyword).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribe(searchHistoryModel -> {

                                    },
                                    throwable -> {
                                        android.widget.Toast.makeText(getContext(),searchHistoryModel.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
                                    }

                            ));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                shoesAdapter.getFilter().filter(newText);
                int countSP= shoesAdapter.getItemCount();
                txtcountsp.setText("("+countSP+" sản phẩm)");
                return false;
            }
        });

    }
    public void getData() {
        txtNotifyShoesSale.setVisibility(INVISIBLE);
        progressBarShoesSale.setVisibility(VISIBLE);
        compositeDisposable.add(apiBanGiay.getSaleShoes(i)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        shoesModel -> {
                            if (shoesModel.isSuccess()){
                                shoesList = shoesModel.getResult();
                                if (!shoesList.isEmpty()){
                                    txtNotifyShoesSale.setVisibility(INVISIBLE);
                                    switch (sort){
                                        case 1:
                                            java.util.Collections.sort(shoesList, (o1, o2) -> o2.getPurchased()-o1.getPurchased());
                                            break;
                                        case 2:
                                            java.util.Collections.sort(shoesList, (o1, o2) -> o2.getShoesNew()-o1.getShoesNew());
                                            break;
                                        case 3:
                                            java.util.Collections.sort(shoesList, (o1, o2) -> {
                                                if (o1.getSaleprice() != 0 && o2.getSaleprice() != 0) {
                                                    return o1.getSaleprice() - o2.getSaleprice();
                                                } else if (o1.getSaleprice() == 0 && o2.getSaleprice() != 0) {
                                                    return o1.getPrice() - o2.getSaleprice();
                                                } else if (o1.getSaleprice() != 0 && o2.getSaleprice() == 0) {
                                                    return o1.getSaleprice() - o2.getPrice();
                                                } else {
                                                    return o1.getPrice() - o2.getPrice();
                                                }
                                            });
                                            break;

                                    }
                                    shoesAdapter = new com.example.shopsneaker.adapter.ShoesAdapter(getContext(),shoesList);
                                    recyclerView.setAdapter(shoesAdapter);
                                }else {
                                    txtNotifyShoesSale.setVisibility(VISIBLE);
                                }
                                progressBarShoesSale.setVisibility(INVISIBLE);
                                int countSP= shoesList.size();
                                txtcountsp.setText("("+countSP+" sản phẩm)");
                            }else {
                                progressBarShoesSale.setVisibility(INVISIBLE);
                                int countSP= shoesList.size();
                                txtcountsp.setText("("+countSP+" sản phẩm)");
                            }
                        },
                        throwable -> {
                            progressBarShoesSale.setVisibility(INVISIBLE);
                            android.widget.Toast.makeText(getContext(),"Load sale shoes fail", android.widget.Toast.LENGTH_LONG).show();

                        }
                ));

    }
    private void Init(View view) {
        txtNotifyShoesSale= view.findViewById(com.example.shopsneaker.R.id.txtNotifyShoesSale);
        progressBarShoesSale= view.findViewById(com.example.shopsneaker.R.id.progessbarShoesSale);
        recyclerView=view.findViewById(com.example.shopsneaker.R.id.recycleviewShoesSale);
        //
        androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager = new androidx.recyclerview.widget.GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        shoesList = new java.util.ArrayList<>();
        //saleid= com.example.shopsneaker.activity.MainActivity.id;
//        txt_sortBanChay=view.findViewById(id.banchay);
//        txt_sortMoiNhat=view.findViewById(id.moinhat);
//        txt_sortGia=view.findViewById(id.giathapdencao);
        //linearLayoutSort = view.findViewById(id.linearlayoutSort);
        txtcountsp= view.findViewById(com.example.shopsneaker.R.id.txtcountspsale);
        i= SaleShoesActivity.saleid;
    }
}
