package com.example.shopsneaker.retrofit;
import com.example.shopsneaker.model.AddModel;
import com.example.shopsneaker.model.MessageModel;
import com.example.shopsneaker.model.uploadModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @retrofit2.http.POST("xemchitietdonhang.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.OrderDetailsModel> getChiTietDH(
            @Field("orderid") int idorder
    );
    @retrofit2.http.POST("insertOrder.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> getCreateOrder(
            @Field("email") String email,
            @Field("name") String Name,
            @Field("phone") String Phone,
            @Field("address") String Address,
            @Field("note") String Note,
            @Field("total") long Total,
            @Field("accountid") int accountid,
            @Field("payment") int payment,
            @Field("chitiet") String chitiet

    );

    @retrofit2.http.POST("insertSales.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> insertSales(
            @Field("salesname") String salesname,
            @Field("startday") String startday,
            @Field("endday") String endday,
            @Field("content") String content,
            @Field("percent") String percent
    );

    @retrofit2.http.POST("updateSales.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> updateSales(
            @Field("salesid") int salesid,
            @Field("salesname") String salesname,
            @Field("startday") String startday,
            @Field("endday") String endday,
            @Field("content") String content,
            @Field("percent") String percent
    );

    @retrofit2.http.POST("xemdonhang.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.OrderModel> getDonHang(
            @Field("accountid") int accountId
    );

    @retrofit2.http.GET("getOrderID.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.OrderModel> getOrderID(
    );

    @retrofit2.http.GET("getsanphammoinhat.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.ShoesModel> getSanPhamMoi();

    @retrofit2.http.GET("getShoesNotSales.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.SaleDetailsModel> getShoesNotSales();

    @retrofit2.http.GET("getSales.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.SalesModel> getSales();

    @retrofit2.http.POST("getSaleDetails.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.SaleDetailsModel> getSaleDetails(
            @Field("salesid") int salesId
    );

    @retrofit2.http.POST("insertSaleDetails.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.SaleDetailsModel> insertSaleDetails(
            @Field("salesid") int salesId,
            @Field("updateby") int updateby,
            @Field("chitiet") String chitiet
    );
    @retrofit2.http.POST("deleteSaleDetails.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.SaleDetailsModel> deleteSaleDetails(
            @Field("salesid") int salesId,
            @Field("chitiet") String chitiet
    );

    @retrofit2.http.POST("deleteSales.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.SalesModel> deleteSales(
            @Field("salesid") int salesId
    );

    @retrofit2.http.POST("registeraccount.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> ReGister(
            @Field("username") String username,
            @Field("password") String password
    );


    @retrofit2.http.POST("loginaccount.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> LoGin(
            @Field("username") String username,
            @Field("password") String password
    );

    @retrofit2.http.POST("registerinfor.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> AddInfor(
            @Field("username") String username,
            @Field("name") String strname,
            @Field("address") String straddress,
            @Field("phone") String strphone
    );

    @retrofit2.http.POST("getAccount.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> getUser(
            @Field("username") String username
    );

    @retrofit2.http.POST("sendmail.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> resetPass(
            @Field("username") String username
    );

    @retrofit2.http.POST("changepass.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> changePass(
            @Field("username") String username,
            @Field("oldpassword") String stroldpass,
            @Field("newpassword") String strnewpass
    );

    @retrofit2.http.GET("GetBrand.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.BrandModel> getBrand();
    @retrofit2.http.POST("GetShoesByBrand.php")
    @retrofit2.http.FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.ShoesModel> getShoesByBrand(
            @retrofit2.http.Field("page") int page,
            @retrofit2.http.Field("brandid") int brandid,
            @retrofit2.http.Field("sortid") String sortid

    );
    @retrofit2.http.GET("GetFlashSaleShoes.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.ShoesModel> getFlashSaleShoes();
    @retrofit2.http.POST("GetSaleShoes.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.ShoesModel> getSaleShoes(
            @Field("saleid") int saleid
    );

    @retrofit2.http.POST("getSizeTable.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.SizeTableModel> getSizeTable(
            @Field("id") int id
    );

//    @retrofit2.http.GET("getOrder.php")
//    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.OrderModel> getOrder();

    @retrofit2.http.POST("updateOrder.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.AddModel> updateOrder(
            @Field("orderid") int orderid,
            @Field("statusname") String status
    );

//    @retrofit2.http.GET("getallshoes.php")
//    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.ShoesModel> getSPMoi();

    @POST("insertShoes.php")
    @FormUrlEncoded
    Observable<AddModel> insertSp(
            @Field("brandid") int brandid,
            @Field("name") String name,
            @Field("images") String images,
            @Field("price") Integer price,
            @Field("description") String description,
            @Field("new") int newsp,
            @Field("exist") int exist
    );
    @Multipart
    @POST("upload.php")
    Call<AddModel> uploadFile(@Part MultipartBody.Part file);
    @POST("updateShoes.php")
    @FormUrlEncoded
    Observable<AddModel> updateSp(
            @Field("brandid") int brandid,
            @Field("id") int id,
            @Field("name") String name,
            @Field("images") String images,
            @Field("price") Integer price,
            @Field("description") String description,
            @Field("new") int newsp,
            @Field("exist") int exist
    );
    @POST ("Insert_key_search.php")
    @FormUrlEncoded
    Observable<com.example.shopsneaker.model.SearchHistoryModel> SearchHistory(
            @Field("accountid") int accountid,
            @Field("keyword") String keyword
    );
    @retrofit2.http.POST("GetSearchHistoryShoes.php")
    @retrofit2.http.FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.ShoesModel> getSearchHistoryShoes(
            @retrofit2.http.Field("accountid") int accountid
    );
    @POST("getStatisticsYear.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.StatisticsModel> getStatisticsYear(
            @Field("year") String year
    );
    @POST("getStatistics.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.StatisticsModel> getStatisticsMonth(
            @Field("month") String month,
            @Field("year") String year
    );

    @POST("getStatisticsId.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.StatisticsModel> getStatisticsID(
            @Field("id") int id,
            @Field("brandid") int brandid

    );

    @retrofit2.http.POST("getStatisticsDate.php")
    @retrofit2.http.FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.StatisticsModel> getStatisticsDay(
            @Field("day") int day,
            @Field("month") int month,
            @Field("year") int year
    );
    //update momo
    @retrofit2.http.POST("updateMomo.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.OrderModel> updateMomo(
            @Field("orderid") int orderid,
            @Field("token") String token

    );

    // get tinh trang don hang
    @retrofit2.http.GET("GetThongKeDonHang.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.ThongKeTinhTrangDonHangModel> getTinhTrangDonHang(
    );

    // get tinh trang don hang
    @retrofit2.http.GET("getTKDHClient.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.ThongKeTinhTrangDonHangModel> getTKDHClient(
    );

    //deleteOrder
    @retrofit2.http.POST("deleteOrder.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.OrderModel> deleteOrder(
            @Field("orderid") int iddonhang

    );

    //statusid
    @retrofit2.http.POST("getOrder.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.OrderModel> getOrder(
            @Field("statusid") int statusid

    );
    //update Account
    @retrofit2.http.POST("updateAccount.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> updateAccount(
            @Field("accountid") int accountid,
            @Field("enabled") byte enabled,
            @Field("rolesid") int rolesid
    );

    @retrofit2.http.POST("rating.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.UserModel> RatingStar(
            @Field("id") int id,
            @Field("accountid") int accountid,
            @Field("rate") float rate
    );

    @retrofit2.http.POST("allcomment.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.CommentModel> getComment(
            @Field("id") int id
    );

    @retrofit2.http.POST("InsertComment.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.CommentModel> insertComment(
            @Field("id") int id,
            @Field("accountid") int accountid,
            @Field("content") String content
    );

    @retrofit2.http.POST("getRateShoes.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.ShoesModel> getRateShoes(
            @Field("id") int id
    );
    @retrofit2.http.POST("getTransactionShoes.php")
    @retrofit2.http.FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.MarketModel> getTransactionShoes(
            @Field("username") String username
    );

    //SIZE TABLE
    @retrofit2.http.GET("getChiTietSizeTable.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.SizeManagmentModel> getSizeManagement();

    @retrofit2.http.POST("updateSizeTable.php")
    @retrofit2.http.FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.AddModel> updateSize(
            @Field("sizeid") int sizeid,
            @Field("s38") String s38,
            @Field("s39") String s39,
            @Field("s40") String s40,
            @Field("s41") String s41,
            @Field("s42") String s42,
            @Field("s43") String s43,
            @Field("s44") String s44,
            @Field("s45") String s45,
            @Field("s46") String s46,
            @Field("s47") String s47,
            @Field("s48") String s48
    );
    //MARKET
    @retrofit2.http.POST("getPostText2.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.MarketModel> getPosts(
            @Field("statusid") int statusid
    );

    //MARKET
    @retrofit2.http.POST("getPost.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.MarketModel> getPosts2(
            @Field("accountid") int accountid
    );

    @retrofit2.http.POST("updatePosts.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.MarketModel> updatePosts(
            @Field("id") int id,
            @Field("statusname") String status
    );
    //Update Market
    @retrofit2.http.POST("updateMarket.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.MarketModel> updateMarket(
            @Field("id") int id
    );
    //Thong ke san pham
    @retrofit2.http.GET("getTKBrand.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.BrandModel> getBrandStatistical();

    @retrofit2.http.POST("getBrandStatistical.php")
    @FormUrlEncoded
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.ShoesModel> getBrandStatisticalDetails(
            @Field("brandid") int brandid
    );

    @retrofit2.http.GET("getTKDay.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.TKDayMonthYearModel> getTKDayMonthYear();
    //Thong ke san pham theo hang
    @retrofit2.http.GET("getTKBrand1.php")
    io.reactivex.rxjava3.core.Observable<com.example.shopsneaker.model.BrandModel> getTKBrand1();

    @POST("insertPosts.php")
    @FormUrlEncoded
    Observable<AddModel> insertPosts(
            @Field("shoesname") String shoesname,
            @Field("price") String price,
            @Field("size") String size,
            @Field("description") String description,
            @Field("accountid") int accountid,
            @Field("images")  String images
    );
    @Multipart
    @retrofit2.http.POST("multi_upload.php")
    Call<uploadModel> uploadNewsFeedImages(
            @Part List<MultipartBody.Part> files);

    @retrofit2.http.POST("getListChat.php")
    @FormUrlEncoded
    Observable<MessageModel> getListChat(
            @Field("sender") int sender
    );

    @retrofit2.http.POST("insertChatMess.php")
    @FormUrlEncoded
    Observable<MessageModel> insertChatMess(
            @Field("sender") int sender,
            @Field("receiver") int receiver,
            @Field("content") String content
    );

    @retrofit2.http.POST("getChatMess.php")
    @FormUrlEncoded
    Observable<MessageModel> getChatMess(
            @Field("sender") int sender,
            @Field("receiver") int receiver
    );

}
