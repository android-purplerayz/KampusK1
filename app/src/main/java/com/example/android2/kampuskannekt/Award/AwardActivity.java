package com.example.android2.kampuskannekt.Award;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android2.kampuskannekt.R;

import java.util.ArrayList;

public class AwardActivity extends AppCompatActivity {

    private RecyclerView recyclerviewAward;
    ArrayList<String> arrayList1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award);
        recyclerviewAward=findViewById(R.id.recyclerviewAward);
        recyclerviewAward.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerviewAward.setAdapter(new MyadapterAward());
        arrayList1.add("Laljuia Colony");
        arrayList1.add("Laljuia Colony");
        arrayList1.add("Laljuia Colony");
        arrayList1.add("Laljuia Colony");
        arrayList1.add("Laljuia Colony");
        arrayList1.add("Laljuia Colony");
        arrayList1.add("Laljuia Colony");
        arrayList1.add("Laljuia Colony");

    }

    public class MyadapterAward extends RecyclerView.Adapter<MyadapterAward.ViewHolder9>{

        @NonNull
        @Override
        public ViewHolder9 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_award,parent,false);
            return new ViewHolder9(V);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder9 holder, int position) {

            holder.tvAward2.setText(arrayList1.get(position));

        }

        @Override
        public int getItemCount() {
            return arrayList1.size();
        }

        public class ViewHolder9 extends RecyclerView.ViewHolder{

            TextView tvAward2;
            public ViewHolder9(View itemView) {
                super(itemView);
                tvAward2=itemView.findViewById(R.id.tvAward2);
            }
        }
    }
}
