package com.example.janghunlee.eunsol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddWordBook extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordbook_add);

        final EditText wordbook_name = findViewById(R.id.wordbook_name);
        Button addWordBook = findViewById(R.id.add_wordbook_btn);

        addWordBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = wordbook_name.getText().toString();

                if(text != null){
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(Req.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                    Req apiService = retrofit.create(Req.class);
                    Call<WordBook_Item> response = apiService.setWordBook(text);
                    response.enqueue(new Callback<WordBook_Item>() {
                        @Override
                        public void onResponse(Call<WordBook_Item> call, Response<WordBook_Item> response) {
                            Intent intent = new Intent(AddWordBook.this , WordBook.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<WordBook_Item> call, Throwable t) {
                            Log.d("ERROR",t.getMessage());
                            Toast.makeText(getApplicationContext(),"Fail to set WordBook",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
