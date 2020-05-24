package com.chaddy.mareu.reunion_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chaddy.mareu.R;
import com.chaddy.mareu.di.DI;
import com.chaddy.mareu.events.DeleteReunionEvent;
import com.chaddy.mareu.model.Reunion;
import com.chaddy.mareu.service.ReunionApiService;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyReunionRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionRecyclerViewAdapter.ViewHolder> {

    private List<Reunion> mReunion;








    public MyReunionRecyclerViewAdapter(List<Reunion> items) {

        mReunion = items;


    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reunion, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Reunion reunion = mReunion.get(position);
        holder.mMeetingInfo.setText(reunion.getSujet() +" - "+ reunion.getDate() +" - " + reunion.getHoraire() + " - " + reunion.getSalle());
        holder.mListParticipants.setText(reunion.getParticipants());








        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override



            public void onClick(View v) {


                EventBus.getDefault().post(new DeleteReunionEvent(reunion));







            }
        });
    }

    @Override
    public int getItemCount() {
        return mReunion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_meeting)
        public TextView mMeetingInfo;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;
        @BindView(R.id.list_participants)
        public TextView mListParticipants;







        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view );



        }





    }


}
