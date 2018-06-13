package com.example.janghunlee.eunsol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddWordList extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist_add);

        Intent intent = new Intent(getIntent());
        final String wordbook_token = intent.getStringExtra("wordbook_token");

        final EditText word_ENG = findViewById(R.id.word_ENG);
        final EditText word_KOR = findViewById(R.id.word_KOR);

        Button btn = findViewById(R.id.add_word_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((word_ENG.getText().toString() != null) && (word_KOR.getText().toString() != null)){

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(Req.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                    final Req apiService = retrofit.create(Req.class);
                    Call<WordList_Item> res = apiService.setWordList(wordbook_token , word_ENG.getText().toString(),word_KOR.getText().toString());
                    res.enqueue(new Callback<WordList_Item>() {
                        @Override
                        public void onResponse(Call<WordList_Item> call, Response<WordList_Item> response) {
                            Intent i = new Intent(AddWordList.this , WordList.class);
                            i.putExtra("token",wordbook_token);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<WordList_Item> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}
