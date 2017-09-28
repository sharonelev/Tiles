package com.example.android.grabble_v4;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.android.grabble_v4.data.SingleLetter;

import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 25/09/2017.
 */

public class myWordsAdapter extends RecyclerView.Adapter<myWordsAdapter.WordViewHolder> {
    Context mContext;
    List<List<SingleLetter>> myWords;
    ListWordClickListener mOnClickListener;



   public interface ListWordClickListener {
        // YOSSI explanation
        void onWordItemClick(int clickedWord, int clickedLetter);
        ;
    }

    public  myWordsAdapter(Context context,  List<List<SingleLetter>> aWords, ListWordClickListener listener){
        mContext=context;
        myWords=aWords;
        mOnClickListener=listener;

    }
    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.my_words_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        myWordsAdapter.WordViewHolder viewHolder = new myWordsAdapter.WordViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position)

    {

//        Log.i("on bind", myWords.get(position).get(1).getLetter_name());

        if(myWords.get(position)==null){

            return;}

        holder.mList.clear();
       holder.mList.addAll(myWords.get(position));

       holder.mBoardAdapter.notifyItemChanged(position); //duplicates the list!
    }

    @Override
    public int getItemCount() {
        return myWords.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements BoardAdapter.ListItemClickListener {//,View.OnClickListener{

        RecyclerView eachWordRecView;
        BoardAdapter mBoardAdapter;
        List<SingleLetter> mList = new ArrayList<>();
        BoardAdapter.ListItemClickListener listener;
        GridLayoutManager gridLayoutManager;


        public WordViewHolder(View itemView) {

            super(itemView);

            eachWordRecView = (RecyclerView) itemView.findViewById(R.id.each_word);

            //      listener= new MainActivity();
            gridLayoutManager = new GridLayoutManager(mContext, 7);

            eachWordRecView.setLayoutManager(gridLayoutManager); //this prevents onClick to work!!
            mBoardAdapter = new BoardAdapter(mContext, mList, this, R.id.myWordsRecyclerView);

            //try sending word in list to the adapter and erase the onclick methods here. first erase and see if mywords still gets emptied
            eachWordRecView.setAdapter(mBoardAdapter);


            //       eachWordRecView.setLayoutFrozen(true);
            // itemView.setOnClickListener(this);
        }

        @Override
        public void onListItemClick(int view_id, int clickedItemIndex) { // a letter in a word was clicked
            Log.i("click in word adapter", "do u get here?");
            int position = getAdapterPosition();
            Log.i("adapter position", String.valueOf(position));
            Log.i("letter position", String.valueOf(clickedItemIndex));
            mOnClickListener.onWordItemClick(position, clickedItemIndex);
            mList.remove(clickedItemIndex);
            mList.add(clickedItemIndex, new SingleLetter("", 0, 0));
            mBoardAdapter.notifyItemRemoved(clickedItemIndex);
            mBoardAdapter.notifyItemInserted(clickedItemIndex);

       }
    }

}
