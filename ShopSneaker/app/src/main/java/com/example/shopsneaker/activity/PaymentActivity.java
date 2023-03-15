package com.example.shopsneaker.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopsneaker.R;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;
import com.example.shopsneaker.utils.checkconnect;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.momo.momo_partner.AppMoMoLib;

public class PaymentActivity extends AppCompatActivity {
    TextView txtAddress,txtEdit, txtName, txtPhone,txtEmail;
    EditText editNote;
    Button btxacnhan,bttrove,btnMomo;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    long tongtien;
    String username;
    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "VLTH Store";
    private String merchantCode = "MOMOGEIB20220529";
    private String merchantNameLabel = "DuyHanh";
    private String description = "Mua giày shop VLTH Store";
    int iddonhang;
    public static com.example.shopsneaker.model.Order order = new com.example.shopsneaker.model.Order();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        username = Utils.user_current.getUsername();
        getUser();
        getOrderID();
        initUi();
        initControl();
        if(checkconnect.isNetworkAvailable(getApplicationContext())){
            eventButton();
        }
        else{
            checkconnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }
    }

    //Get token through MoMo app
    private void requestPayment(String iddonhang) {
        vn.momo.momo_partner.AppMoMoLib.getInstance().setAction(vn.momo.momo_partner.AppMoMoLib.ACTION.PAYMENT);
        vn.momo.momo_partner.AppMoMoLib.getInstance().setActionType(vn.momo.momo_partner.AppMoMoLib.ACTION_TYPE.GET_TOKEN);
//        if (edAmount.getText().toString() != null && edAmount.getText().toString().trim().length() != 0)
//            amount = edAmount.getText().toString().trim();

        java.util.Map<String, Object> eventValue = new java.util.HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", tongtien); //Kiểu integer
        eventValue.put("orderId", iddonhang); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", iddonhang); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        org.json.JSONObject objExtraData = new org.json.JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        vn.momo.momo_partner.AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }

    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == vn.momo.momo_partner.AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("thanhcong", data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    compositeDisposable.add(apiBanGiay.updateMomo(iddonhang,token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    orderModel -> {
                                        if (orderModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(),"Thành Công", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            Utils.manggiohang = null;
                                            finish();
                                        }
                                    },
                                    throwable -> {
                                        Log.d("error", throwable.getMessage());
                                    }
                            ));



                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("thanhcong", "khong thanh cong");
                        deleteOrder();
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("thanhcong", "khong thanh cong");
                    deleteOrder();
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("thanhcong", "khong thanh cong");
                    deleteOrder();
                } else {
                    //TOKEN FAIL
                    Log.d("thanhcong", "khong thanh cong");
                    deleteOrder();
                }
            } else {
                Log.d("thanhcong", "khong thanh cong");
                deleteOrder();
            }
        } else {
            Log.d("thanhcong", "khong thanh cong");
            deleteOrder();
        }
    }

    private void initControl(){
        txtEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ThongTinKhachHangActivity.class);
            startActivity(intent);
        });
    }
    public void setDialog(){

    }

    private void deleteOrder(){
        int orderid = order.getOrderid();
        iddonhang = orderid+1;
        compositeDisposable.add(apiBanGiay.deleteOrder(iddonhang).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            if(orderModel.isSuccess()){

                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getOrderID(){
        compositeDisposable.add(apiBanGiay.getOrderID().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            if(orderModel.isSuccess()){
                                order = orderModel.getResult().get(0);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getUser(){
        compositeDisposable.add(apiBanGiay.getUser(username).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                Utils.user_current = userModel.getResult().get(0);
                            }else {
                                Toast.makeText(getApplicationContext(),"Thất bại", Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void eventButton() {
        bttrove.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
            startActivity(intent);
            finish();
        });
        btxacnhan.setOnClickListener(v -> {

            String Name = txtName.getText().toString().trim();
            String Phone = txtPhone.getText().toString().trim();
            String email = txtEmail.getText().toString().trim();
            String Address = txtAddress.getText().toString().trim();
            String Note = editNote.getText().toString().trim();
            if(Name.length()>0&&Phone.length()>0&&email.length()>0&&Address.length()>0){
                Log.d("test", new Gson().toJson(Utils.manggiohang));
                int AccountId = Utils.user_current.getAccountid();
                //
                androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(this);
                //Thiết lập tiêu đề
                b.setTitle("Xác nhận");
                b.setMessage("Bạn có đồng ý đặt hàng không?");
                b.setPositiveButton("Đồng ý", (dialog, id) -> compositeDisposable.add(apiBanGiay.getCreateOrder(email,Name,Phone,Address,Note,tongtien,AccountId,0,new Gson().toJson(Utils.manggiohang))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(

                                userModel -> {
                                    Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    Utils.manggiohang = null;
                                    finish();
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), "Không kết nối được server", Toast.LENGTH_SHORT).show();
                                }
                        )));
//Nút Cancel
                b.setNegativeButton("Không đồng ý", (dialog, id) -> dialog.cancel());
//Tạo dialog
                androidx.appcompat.app.AlertDialog al = b.create();
//Hiển thị
                al.show();
                //

            }
        });


        btnMomo.setOnClickListener(view -> {
            String Name = txtName.getText().toString().trim();
            String Phone = txtPhone.getText().toString().trim();
            String email = txtEmail.getText().toString().trim();
            String Address = txtAddress.getText().toString().trim();
            String Note = editNote.getText().toString().trim();

            //
            if(Name.length()>0&&Phone.length()>0&&email.length()>0&&Address.length()>0){
                Log.d("test", new Gson().toJson(Utils.manggiohang));
                int AccountId = Utils.user_current.getAccountid();
                //
                androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(this);
                //Thiết lập tiêu đề
                b.setTitle("Xác nhận");
                b.setMessage("Bạn có đồng ý đặt hàng và thanh toán bằng momo không?");
                b.setPositiveButton("Đồng ý", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int id) {

                        compositeDisposable.add(apiBanGiay.getCreateOrder(email,Name,Phone,Address,Note,tongtien,AccountId,0,new Gson().toJson(Utils.manggiohang))
                                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribe(
                                        userModel -> {
                                            int orderid = order.getOrderid();
                                            iddonhang = orderid+1;
                                            String s = String.valueOf(iddonhang);
                                            requestPayment(s);
                                        },
                                        throwable -> {
                                            Toast.makeText(getApplicationContext(), "Không kết nối được server", Toast.LENGTH_SHORT).show();
                                        }
                                ));
                    }
                });
//Nút Cancel
                b.setNegativeButton("Không đồng ý", (dialog, id) -> dialog.cancel());
//Tạo dialog
                androidx.appcompat.app.AlertDialog al = b.create();
//Hiển thị
                al.show();
                //


            }
        });
    }

    private void initUi(){
        username = Utils.user_current.getUsername();
        txtAddress=findViewById(R.id.txtAddress);
        txtName=findViewById(R.id.txtName);
        txtPhone=findViewById(R.id.txtPhone);
        txtEmail=findViewById(R.id.txtEmail);
        txtEdit=findViewById(R.id.textEdit);
        editNote=findViewById(R.id.edittextghichu);
        btxacnhan=findViewById(R.id.buttonxacnhan);
        bttrove=findViewById(R.id.buttontrove);
        tongtien = getIntent().getLongExtra("tongtien",0);
        txtEmail.setText(Utils.user_current.getUsername());
        txtAddress.setText(Utils.user_current.getAddress());
        txtName.setText(Utils.user_current.getName());
        txtPhone.setText(Utils.user_current.getPhone());
        btnMomo = findViewById(com.example.shopsneaker.R.id.buttonmomo);
    }

    protected void onResume () {
        super.onResume();
        if (Utils.user_current.getUsername() != null && Utils.user_current.getPassword() != null ){
            txtEmail.setText(Utils.user_current.getUsername());
            txtAddress.setText(Utils.user_current.getAddress());
            txtName.setText(Utils.user_current.getName());
            txtPhone.setText(Utils.user_current.getPhone());
        }
    }

}
