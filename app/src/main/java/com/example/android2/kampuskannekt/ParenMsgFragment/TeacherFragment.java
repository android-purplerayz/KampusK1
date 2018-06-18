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
public class TeacherFragment extends Fragment {


    private ArrayList<String> arrayList = new ArrayList<>();
    public TeacherFragment() {
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
        View view=inflater.inflate(R.layout.fragment_teacher, container, false);
        RecyclerView recyclerViewTeacher=(RecyclerView)view.findViewById(R.id.recyclerViewTeacherID);
        recyclerViewTeacher.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTeacher.setAdapter(new MyAdapterTeacher());
        return view;
    }
    public class MyAdapterTeacher extends RecyclerView.Adapter<MyAdapterTeacher.ViewHolderTeacher>{

        @NonNull
        @Override
        public ViewHolderTeacher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_messeges,parent,false);
            return new ViewHolderTeacher(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderTeacher holder, int position) {
            holder.textView.setText(arrayList.get(position));

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolderTeacher extends RecyclerView.ViewHolder{


            TextView textView;
            public ViewHolderTeacher(View itemView) {
                super(itemView);
                textView=(TextView)itemView.findViewById(R.id.textViewMSG14);
            }
        }
    }

}
