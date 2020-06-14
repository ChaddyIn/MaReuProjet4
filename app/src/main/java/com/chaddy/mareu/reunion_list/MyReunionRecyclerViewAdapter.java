package com.chaddy.mareu.reunion_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chaddy.mareu.R;
import com.chaddy.mareu.di.DI;
import com.chaddy.mareu.events.DeleteReunionEvent;
import com.chaddy.mareu.model.Reunion;
import com.chaddy.mareu.service.ReunionApiService;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyReunionRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionRecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<Reunion> mReunionAdapterList;
    private List<Reunion> mReunionTest;
    private List<Reunion> mReunionFull;
    private ReunionApiService reunionApiServiceForFilter = DI.getReunionApiService();


    public MyReunionRecyclerViewAdapter(List<Reunion> items) {

        this.mReunionAdapterList = items;
        mReunionTest = reunionApiServiceForFilter.getReunion();
        mReunionFull = reunionApiServiceForFilter.getReunionForFilter();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reunion, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Reunion reunion = mReunionAdapterList.get(position);
        holder.mMeetingInfo.setText(reunion.getSujet() + " - " + reunion.getDate() + " - " + reunion.getHoraire() + " - " + reunion.getSalle().toUpperCase());
        holder.mListParticipants.setText(reunion.getParticipants());

        holder.mNeighbourAvatar.setImageResource(reunion.getLogo());


        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                EventBus.getDefault().post(new DeleteReunionEvent(reunion));
                reunionApiServiceForFilter.deleteReunion(reunion);

            }
        });
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
            ButterKnife.bind(this, view);

        }
    }

    @Override
    public int getItemCount() {
        return mReunionAdapterList.size();
    }


    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public Filter getmFilter2() {
        return mFilter2;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {


            List<Reunion> listFiltered = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {

                listFiltered.clear();
                listFiltered.addAll(mReunionFull);
            } else {

                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Reunion reunionList : mReunionFull) {

                    if (reunionList.getSalle().toLowerCase().contains(filterPattern)) {

                        listFiltered.add(reunionList);

                    }

                }

            }

            FilterResults results = new FilterResults();
            results.values = listFiltered;

            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {


            mReunionAdapterList.clear();
            mReunionAdapterList.addAll((List) filterResults.values);

            notifyDataSetChanged();

        }


    };

    private Filter mFilter2 = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {


            List<Reunion> listFiltered = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {


                listFiltered.clear();
                listFiltered.addAll(mReunionFull);
            } else {


                for (Reunion reunionList : mReunionFull) {

                    String filterPattern = charSequence.toString().toLowerCase().trim();

                    if (reunionList.getDate().toLowerCase().contains(filterPattern)) {


                        listFiltered.add(reunionList);

                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = listFiltered;

            return results;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            mReunionAdapterList.clear();
            mReunionAdapterList.addAll((List) filterResults.values);

            notifyDataSetChanged();
        }

    };


}
