package com.example.user.ormliteapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 9/6/17.
 */

class NameAdapter extends RecyclerView.Adapter<NameAdapter.CustomViewHolder> {

    private List<Person> feedItemList;
    private IMyRecyclerAdaptersCallBack iMyRecyclerAdaptersCallBack;

    public NameAdapter(List<Person> feedItemList,IMyRecyclerAdaptersCallBack iMyRecyclerAdaptersCallBack){
        this.feedItemList=feedItemList;
        this.iMyRecyclerAdaptersCallBack=iMyRecyclerAdaptersCallBack;
    }

    public void setFeedItemList(List<Person> feedItemList) {
        this.feedItemList = feedItemList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        Person person=feedItemList.get(position);
         holder.textViewName.setText(person.getName());
        holder.listRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMyRecyclerAdaptersCallBack.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ViewGroup listRow;
        public CustomViewHolder(View itemView) {
            super(itemView);
            this.textViewName=(TextView)itemView.findViewById(R.id.textViewName);
            this.listRow=(ViewGroup)itemView.findViewById(R.id.listRow);
        }
    }

    public interface IMyRecyclerAdaptersCallBack{
        public void onClick(int position);
    }
}
