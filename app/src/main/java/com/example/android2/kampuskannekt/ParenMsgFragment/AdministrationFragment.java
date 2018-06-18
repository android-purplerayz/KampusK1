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

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdministrationFragment extends Fragment {


    private ArrayList<String> arrayList = new ArrayList<>();
    public AdministrationFragment() {
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
        View view=inflater.inflate(R.layout.fragment_administration, container, false);
        RecyclerView recyclerViewAdmin=(RecyclerView)view.findViewById(R.id.recyclerViewAdminID);
        recyclerViewAdmin.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewAdmin.setAdapter(new MyadapterAdmin());
        return view;
    }

    public class MyadapterAdmin extends RecyclerView.Adapter<MyadapterAdmin.ViewHolderAdmnin>{

        @NonNull
        @Override
        public ViewHolderAdmnin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v=LayoutInflater.from(getContext()).inflate(R.layout.item_parent_messeges,parent,false);
            return new ViewHolderAdmnin(v);

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderAdmnin holder, int position) {
            holder.textView.setText(arrayList.get(position));

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolderAdmnin extends RecyclerView.ViewHolder{

            TextView textView;
            public ViewHolderAdmnin(View itemView) {
                super(itemView);
                textView=(TextView)itemView.findViewById(R.id.textViewMSG14);
            }
        }
    }

}
