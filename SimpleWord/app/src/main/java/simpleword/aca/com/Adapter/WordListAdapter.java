package simpleword.aca.com.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import simpleword.aca.com.Db.DBHelper;
import simpleword.aca.com.Fragment.MainFragment;
import simpleword.aca.com.R;
import simpleword.aca.com.Word;

public class WordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    Drawable fillStarImage;
    Drawable emptyStarImage;


    private ArrayList<Word> wordArrayList;
    private DBHelper dbHelper;

    public WordListAdapter(ArrayList<Word> wordArrayList, Context context, DBHelper dbHelper)
    {
        this.wordArrayList = wordArrayList;
        fillStarImage = context.getResources().getDrawable(R.drawable.star_full, null);
        emptyStarImage = context.getResources().getDrawable(R.drawable.star_empty, null);
        this.dbHelper = dbHelper;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.word_item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position)
    {
        MyViewHolder holder = (MyViewHolder) viewHolder;

        Word word = getItem(position);

        fillStar(word, holder); //Word 객체에 있는 Star count 수 만큼 star를 채운다.

        holder.tvKorean.setText(word.getKoreanStr());
        holder.tvEnglish.setText(word.getEnglishStr());
        holder.checkBox.setChecked(word.isChecked());

    }

    public void addItem(Word word)
    {
        wordArrayList.add(word);

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return wordArrayList.size();
    }

    public Word getItem(int position)
    {
        return this.wordArrayList.get(position);
    }

    public void fillStar(Word word, MyViewHolder holder)
    {
        int starCount = word.getStar();

        switch(starCount)
        {
            case 1:
                holder.ivStar[0].setImageDrawable(fillStarImage);
                holder.ivStar[1].setImageDrawable(emptyStarImage);
                holder.ivStar[2].setImageDrawable(emptyStarImage);
                holder.ivStar[3].setImageDrawable(emptyStarImage);
                holder.ivStar[4].setImageDrawable(emptyStarImage);
                break;
            case 2:
                holder.ivStar[0].setImageDrawable(fillStarImage);
                holder.ivStar[1].setImageDrawable(fillStarImage);
                holder.ivStar[2].setImageDrawable(emptyStarImage);
                holder.ivStar[3].setImageDrawable(emptyStarImage);
                holder.ivStar[4].setImageDrawable(emptyStarImage);
                break;
            case 3:
                holder.ivStar[0].setImageDrawable(fillStarImage);
                holder.ivStar[1].setImageDrawable(fillStarImage);
                holder.ivStar[2].setImageDrawable(fillStarImage);
                holder.ivStar[3].setImageDrawable(emptyStarImage);
                holder.ivStar[4].setImageDrawable(emptyStarImage);
                break;
            case 4:
                holder.ivStar[0].setImageDrawable(fillStarImage);
                holder.ivStar[1].setImageDrawable(fillStarImage);
                holder.ivStar[2].setImageDrawable(fillStarImage);
                holder.ivStar[3].setImageDrawable(fillStarImage);
                holder.ivStar[4].setImageDrawable(emptyStarImage);
                break;
            case 5:
                holder.ivStar[0].setImageDrawable(fillStarImage);
                holder.ivStar[1].setImageDrawable(fillStarImage);
                holder.ivStar[2].setImageDrawable(fillStarImage);
                holder.ivStar[3].setImageDrawable(fillStarImage);
                holder.ivStar[4].setImageDrawable(fillStarImage);
                break;

        }

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvKorean;
        TextView tvEnglish;
        CheckBox checkBox;
        public ImageView[] ivStar;



        public MyViewHolder(@NonNull View v)
        {
            super(v);
            ivStar = new ImageView[5];
            ivStar[0] = v.findViewById(R.id.iv_item_importance1);
            ivStar[1] = v.findViewById(R.id.iv_item_importance2);
            ivStar[2] = v.findViewById(R.id.iv_item_importance3);
            ivStar[3] = v.findViewById(R.id.iv_item_importance4);
            ivStar[4] = v.findViewById(R.id.iv_item_importance5);

            tvEnglish = v.findViewById(R.id.tv_worditem_english);
            tvKorean = v.findViewById(R.id.tv_worditem_korean);
            checkBox = v.findViewById(R.id.word_checkbox);


        }

    }






}
