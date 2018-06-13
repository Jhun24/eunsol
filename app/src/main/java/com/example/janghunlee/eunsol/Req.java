package com.example.janghunlee.eunsol;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Req {
    static final String BASE_URL = "http://aws.soylatte.kr:5000";

    @GET("/eunsol/getWordBook")
    Call<WordBook_ArrayList> getWordBook();

    @FormUrlEncoded
    @POST("/eunsol/setWordBook")
    Call<WordBook_Item> setWordBook(@Field("wordbook_name") String wordbook_name);

    @FormUrlEncoded
    @POST("/eunsol/deleteWordBook")
    Call<WordBook_Item> deleteWordBook(@Field("wordbook_token") String wordbook_token);

    @GET("/eunsol/getWordList")
    Call<WordList_ArrayList> getWordList(@Query("wordbook_token") String word_token);

    @FormUrlEncoded
    @POST("/eunsol/setWordList")
    Call<WordList_Item> setWordList(@Field("wordbook_token") String wordbook_token , @Field("word_ENG") String word_ENG , @Field("word_KOR") String word_KOR);

    @FormUrlEncoded
    @POST("/eunsol/deleteWordList")
    Call<WordList_Item> deleteWordList(@Field("word_token") String word_token);
}
