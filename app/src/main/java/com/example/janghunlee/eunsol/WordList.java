package com.example.janghunlee.eunsol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordList extends AppCompatActivity {
    ArrayList<WordList_Item> wordList_items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);

        final Intent intent = new Intent(getIntent());
        final String token = intent.getStringExtra("token");

        final ListView listView = findViewById(R.id.listView);
        final WordListVIewAdapter listViewAdapter = new WordListVIewAdapter(this);
        Button addBtn = findViewById(R.id.add_word_btn);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Req.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final Req apiService = retrofit.create(Req.class);
        Call<WordList_ArrayList> res = apiService.getWordList(token);
        res.enqueue(new Callback<WordList_ArrayList>() {
            @Override
            public void onResponse(Call<WordList_ArrayList> call, Response<WordList_ArrayList> response) {
                wordList_items = response.body().getData();

                for(WordList_Item i : wordList_items){
                    listViewAdapter.addItem(i.getWord_ENG(),i.getWord_KOR(),i.getWord_token(),i.getWordbook_token());
                }

                listView.setAdapter(listViewAdapter);
            }

            @Override
            public void onFailure(Call<WordList_ArrayList> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                Toast.makeText(getApplicationContext(),"Fail to Load Word List",Toast.LENGTH_LONG).show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WordList.this , AddWordList.class);
                i.putExtra("wordbook_token",token);
                startActivity(i);
            }
        });

    }
}
