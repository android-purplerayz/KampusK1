package com.example.android2.kampuskannekt.ParenMsgFragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android2.kampuskannekt.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment {


    private ArrayList<String> arrayList = new ArrayList<>();

    public RecentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        arrayList.add("Loren ipsum dolor sit amet,consectetur.....");
        arrayList.add("Loren ipsum dolor sit amet,consectetur....");
        arrayList.add("Loren ipsum dolor sit amet,consectetur....");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur....");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur....");

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_recent, container, false);
        RecyclerView recyclerViewRecent=(RecyclerView)view.findViewById(R.id.recyclerViewRecentID);

        recyclerViewRecent.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewRecent.setAdapter(new MyAdapterRecent());
        return view;
    }
    public class MyAdapterRecent extends RecyclerView.Adapter<MyAdapterRecent.ViewHolderRecent>{

        public class ViewHolderRecent extends RecyclerView.ViewHolder{

            TextView textView;
            public ViewHolderRecent(View itemView) {
                super(itemView);
                textView =(TextView)itemView.findViewById(R.id.textViewMSG14);

            }
        }
        @NonNull
        @Override
        public MyAdapterRecent.ViewHolderRecent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_messeges,parent,false);
            return new ViewHolderRecent(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapterRecent.ViewHolderRecent holder, int position) {
            holder.textView.setText(arrayList.get(position));

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

}
