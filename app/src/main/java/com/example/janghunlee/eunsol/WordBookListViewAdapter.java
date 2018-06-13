package com.example.janghunlee.eunsol;

import android.content.Context;
import android.content.Intent;
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

public class WordBookListViewAdapter extends BaseAdapter{
    private Context context = null;
    private ArrayList<WordBook_Item> arrayList = new ArrayList<WordBook_Item>();

    public WordBookListViewAdapter(Context context){
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
            convertView = inflater.inflate(R.layout.wordbook_list_item,null);

            holder.wordbook_date = (TextView)convertView.findViewById(R.id.wordbook_date);
            holder.wordbook_name = (TextView)convertView.findViewById(R.id.wordbook_name);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        Button deleteBtn = convertView.findViewById(R.id.delete_wordbook);

        final WordBook_Item wordBook_item = arrayList.get(position);
        holder.wordbook_name.setText(wordBook_item.getWordbook_name());
        holder.wordbook_date.setText(wordBook_item.getWordbook_date());

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Req.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                Req apiService = retrofit.create(Req.class);
                Call<WordBook_Item> response = apiService.deleteWordBook(wordBook_item.getWordbook_token());
                response.enqueue(new Callback<WordBook_Item>() {
                    @Override
                    public void onResponse(Call<WordBook_Item> call, Response<WordBook_Item> response) {
                        arrayList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<WordBook_Item> call, Throwable t) {
                        Log.d("ERROR",t.getMessage());
                        Toast.makeText(context.getApplicationContext(),"Fail to Delete WordBook",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        return convertView;
    }

    public void addItem(String wordbook_name , String wordbook_date , String wordbook_token){
        WordBook_Item saveWordBook_Item = new WordBook_Item();
        saveWordBook_Item.setWordbook_name(wordbook_name);
        saveWordBook_Item.setWordbook_date(wordbook_date);
        saveWordBook_Item.setWordbook_token(wordbook_token);

        arrayList.add(saveWordBook_Item);
    }

    public void remove(int position){
        arrayList.remove(position);
    }

}
