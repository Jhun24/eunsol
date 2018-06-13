package com.example.janghunlee.eunsol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordBook extends AppCompatActivity{

    ArrayList<WordBook_Item> wordBook_items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordbook_list);

        final ListView listView = findViewById(R.id.wordbook_listView);
        final WordBookListViewAdapter listViewAdapter = new WordBookListViewAdapter(this);
        Button addWordBook = findViewById(R.id.add_wordbook_btn);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Req.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final Req apiService = retrofit.create(Req.class);
        Call<WordBook_ArrayList> res = apiService.getWordBook();
        res.enqueue(new Callback<WordBook_ArrayList>() {
            @Override
            public void onResponse(Call<WordBook_ArrayList> call, Response<WordBook_ArrayList> response) {
                wordBook_items = response.body().getData();
                for(WordBook_Item i : wordBook_items){
                    listViewAdapter.addItem(i.getWordbook_name() , i.getWordbook_date() , i.getWordbook_token());
                }

                listView.setAdapter(listViewAdapter);
            }

            @Override
            public void onFailure(Call<WordBook_ArrayList> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                Toast.makeText(getApplicationContext(),"Fail to load WordBook List ",Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_LONG).show();
                String token = wordBook_items.get(position).getWordbook_token();
                Intent intent = new Intent(WordBook.this , WordList.class);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });

        addWordBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordBook.this , AddWordBook.class);
                startActivity(intent);
            }
        });

    }


}
