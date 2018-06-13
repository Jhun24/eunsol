package com.example.janghunlee.eunsol;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordListVIewAdapter extends BaseAdapter {
    private Context context = null;
    private ArrayList<WordList_Item> arrayList = new ArrayList<WordList_Item>();

    public WordListVIewAdapter(Context context){
        super();
        this.context = context;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.wordlist_item,null);

            holder.word_KOR = (TextView)convertView.findViewById(R.id.word_KOR);
            holder.word_ENG = (TextView)convertView.findViewById(R.id.word_ENG);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        Button deleteBtn = convertView.findViewById(R.id.delete_wordlist);

        final WordList_Item wordList_item = arrayList.get(position);
        holder.word_ENG.setText(wordList_item.getWord_ENG());
        holder.word_KOR.setText(wordList_item.getWord_KOR());

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Req.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                Req apiService = retrofit.create(Req.class);
                Call<WordList_Item> response = apiService.deleteWordList(wordList_item.getWord_token());
                response.enqueue(new Callback<WordList_Item>() {
                    @Override
                    public void onResponse(Call<WordList_Item> call, Response<WordList_Item> response) {
                        arrayList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<WordList_Item> call, Throwable t) {
                        Log.d("ERROR",t.getMessage());
                        Toast.makeText(context.getApplicationContext(),"Fail to Delete Word",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return convertView;
    }

    public void addItem(String word_ENG , String word_KOR , String word_token , String wordbook_token){
        WordList_Item saveWordList_Item = new WordList_Item();
        saveWordList_Item.setWord_ENG(word_ENG);
        saveWordList_Item.setWord_KOR(word_KOR);
        saveWordList_Item.setWordbook_token(wordbook_token);
        saveWordList_Item.setWord_token(word_token);

        arrayList.add(saveWordList_Item);
    }

    public void remove(int position){
        arrayList.remove(position);
    }

}
