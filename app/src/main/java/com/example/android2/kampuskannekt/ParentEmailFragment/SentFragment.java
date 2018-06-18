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
public class SentFragment extends Fragment {


    private ArrayList<String> arrayList = new ArrayList<>();
    public SentFragment() {
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
        View view= inflater.inflate(R.layout.fragment_sent, container, false);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerViewPEsentID);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MySentAdapter());
        return view;
    }


    public class MySentAdapter extends RecyclerView.Adapter<MySentAdapter.SentViewHolder>{
        @NonNull
        @Override
        public SentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getContext()).inflate(R.layout.item_parent_email,parent,false);

            return new SentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SentViewHolder holder, int position) {

            holder.textView.setText(arrayList.get(position));

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class SentViewHolder extends RecyclerView.ViewHolder{

            TextView textView;
            public SentViewHolder(View itemView) {
                super(itemView);
                textView=(TextView)itemView.findViewById(R.id.textViewEmail14);
            }
        }
    }
}
