package com.example.lab6;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceInsertSanPham {
    @FormUrlEncoded
    @POST("insert1.php")
    Call<SvrResponseSanPham> insertSanPham(
            @Field("MaSP") String MaSp,
            @Field("TenSP") String TenSP,
            @Field("MoTa") String MoTa
    );
}