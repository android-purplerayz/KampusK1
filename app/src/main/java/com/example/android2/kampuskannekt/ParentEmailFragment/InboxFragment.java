package com.example.android2.kampuskannekt.ParentEmailFragment;


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
public class InboxFragment extends Fragment {


    private ArrayList<String> arrayList = new ArrayList<>();

    public InboxFragment() {
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
        View view=inflater.inflate(R.layout.fragment_inbox, container, false);
        RecyclerView recyclerView =(RecyclerView)view.findViewById(R.id.recyclerViewPEinboxID);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyInboxAdapter());
        return view;
    }

    public class MyInboxAdapter extends RecyclerView.Adapter<MyInboxAdapter.ViewHolderInbox>{

        @NonNull
        @Override
        public ViewHolderInbox onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_email,parent,false);
            return new ViewHolderInbox(V);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderInbox holder, int position) {

            holder.textView.setText(arrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolderInbox extends RecyclerView.ViewHolder{

            TextView textView;
            public ViewHolderInbox(View itemView) {
                super(itemView);
                textView=(TextView)itemView.findViewById(R.id.textViewEmail14);
            }
        }
    }

}
