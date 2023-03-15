package com.example.shopsneaker.Fragment;

import static android.view.View.*;
import static com.example.shopsneaker.R.layout.fragment_shoes;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shopsneaker.R.id;
import com.example.shopsneaker.retrofit.ApiService;

public class ShoesFragment extends androidx.fragment.app.Fragment {
    androidx.recyclerview.widget.RecyclerView recyclerView;
    ApiService apiBanGiay;
    io.reactivex.rxjava3.disposables.CompositeDisposable compositeDisposable = new io.reactivex.rxjava3.disposables.CompositeDisposable();
    android.widget.ImageView img_newShoesByBrand;
    int page=1;
    int brandid;
    String sortid="sort";
    public String brandname;
    boolean banchay=false, moinhat=false,gia=false;
    java.util.List<com.example.shopsneaker.model.Shoes> shoesList;
    com.example.shopsneaker.model.SearchHistoryModel searchHistoryModel;
    TextView txtcountsp;
    androidx.appcompat.widget.SearchView searchView;
    //LinearLayout linearLayoutSort;
    //ImageView imageThemgiohang;
    com.example.shopsneaker.model.Shoes shoes;
    com.nex3z.notificationbadge.NotificationBadge badge;
    private com.example.shopsneaker.adapter.ShoesAdapter shoesAdapter;
    int sort;
    android.content.Context mContext;
    android.view.MenuItem menuItem;
    ProgressBar progressBarShoes;
    TextView txtNotifyShoes;



    public ShoesFragment(int sort) {
        this.sort = sort;
    }

    public ShoesFragment() {
    }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull android.view.LayoutInflater inflater, @androidx.annotation.Nullable android.view.ViewGroup container, @androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        return inflater.inflate(fragment_shoes,container,false);

    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiBanGiay = com.example.shopsneaker.retrofit.RetrofitClient.getInstance(com.example.shopsneaker.utils.Utils.BASE_URL).create(ApiService.class);
        Init(view);

        android.os.Bundle bundle = getArguments();
        if(bundle != null)
        {
            com.example.shopsneaker.model.Brand sv = (com.example.shopsneaker.model.Brand) bundle.getSerializable("KEY_SER_SV");
            brandid= sv.getBrandid();

        }

        getData();
        // brandid = getIntent().getIntExtra("brandid",1);
        // brandname = getIntent().getStringExtra("brandname");
    }

    @Override
    public void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        /*OnBackPressedCallback callback = new OnBackPressedCallback(true *//* enabled by default *//*) {
            @Override
            public void handleOnBackPressed() {
                if (!searchView.isIconified()){
                    searchView.setIconified(true);

                    return;
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);*/
    }

    @Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull android.view.Menu menu, @androidx.annotation.NonNull android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(com.example.shopsneaker.R.menu.menu,menu);
//       searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
//       searchView.setIconified(true);
        android.app.SearchManager searchManager = (android.app.SearchManager) getActivity().getSystemService(android.content.Context.SEARCH_SERVICE);
        searchView = (androidx.appcompat.widget.SearchView) menu.findItem(id.menuSearch).getActionView();
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

    /* @Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull android.view.Menu menu, @androidx.annotation.NonNull android.view.MenuInflater inflater) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchManager searchManager =(SearchManager) getSystemService(android.content.Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                linearLayoutSort.setVisibility(View.INVISIBLE);
                shoesAdapter.getFilter().filter(query);
                int countSP= shoesAdapter.getItemCount();
                txtcountsp.setText("("+countSP+" sản phẩm)");
                String keyword = query.toString();
                if (shoesAdapter.getItemCount()>0) {
                    int accountid = Utils.user_current.getAccountid();
                    compositeDisposable.add(apiBanGiay.SearchHistory(accountid,keyword).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(searchHistoryModel -> {

                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(),searchHistoryModel.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                            ));
                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                linearLayoutSort.setVisibility(View.INVISIBLE);
                shoesAdapter.getFilter().filter(newText);
                int countSP= shoesAdapter.getItemCount();
                txtcountsp.setText("("+countSP+" sản phẩm)");
                return false;
            }
        });
        return true;
    }*/

    public void getData() {
        txtNotifyShoes.setVisibility(INVISIBLE);
        progressBarShoes.setVisibility(VISIBLE);
        compositeDisposable.add(apiBanGiay.getShoesByBrand(page,brandid,sortid)

                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        shoesModel -> {
                            if (shoesModel.isSuccess()){
                                shoesList = shoesModel.getResult();
                                if (!shoesList.isEmpty()){
                                    txtNotifyShoes.setVisibility(INVISIBLE);
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
                                        case 4:
                                            //Collections.sort(shoesList, (o1, o2) -> o2.getShoesNew()-o1.getShoesNew());
                                            java.util.List<com.example.shopsneaker.model.Shoes> ls= new java.util.ArrayList<>();
                                            java.util.Date date = new java.util.Date();
                                            for (int i=0;i<shoesList.size();i++){
                                                if (shoesList.get(i).getSaleprice()!=0 && shoesList.get(i).getStartday().before(date)&& shoesList.get(i).getEndday().after(date)){
                                                    ls.add(shoesList.get(i));
                                                }
                                            }
                                            if (ls.isEmpty()){
                                                txtNotifyShoes.setVisibility(VISIBLE);
                                            }
                                            shoesList=ls;
                                            break;
                                    }
                                    shoesAdapter = new com.example.shopsneaker.adapter.ShoesAdapter(getContext(),shoesList);
                                    recyclerView.setAdapter(shoesAdapter);
                                }else {
                                    txtNotifyShoes.setVisibility(VISIBLE);
                                }
                                progressBarShoes.setVisibility(INVISIBLE);

                                int countSP= shoesList.size();
                                txtcountsp.setText("("+countSP+" sản phẩm)");
                            }

                        },
                        throwable -> {
                            progressBarShoes.setVisibility(INVISIBLE);
                            txtNotifyShoes.setVisibility(VISIBLE);
                            android.widget.Toast.makeText(getContext(),"Load san pham theo hang that bai", android.widget.Toast.LENGTH_LONG).show();

                        }
                ));
    }
    private void Init(View view) {
        txtNotifyShoes= view.findViewById(id.txtNotifyShoes);
        progressBarShoes= view.findViewById(id.progessbarShoes);
        recyclerView=view.findViewById(id.recycleviewShoesByBrand);
        //
        androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager = new androidx.recyclerview.widget.GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        shoesList = new java.util.ArrayList<>();
        brandid= com.example.shopsneaker.activity.MainActivity.id;
//        txt_sortBanChay=view.findViewById(id.banchay);
//        txt_sortMoiNhat=view.findViewById(id.moinhat);
//        txt_sortGia=view.findViewById(id.giathapdencao);
        //linearLayoutSort = view.findViewById(id.linearlayoutSort);
        txtcountsp= view.findViewById(id.txtcountsp);
    }
}
