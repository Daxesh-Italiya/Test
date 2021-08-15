package com.TST.Crash_Investigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TST.Crash_Investigation.Function.Case_DB;
import com.TST.Crash_Investigation.object.Case;

import java.util.ArrayList;

public class View_Case extends AppCompatActivity {

    private RecyclerView Case_Recycler_View;

    //------------- Local Database -----------------
    private Case_DB db;
    private Case_Adapter case_adapter;
    private ArrayList<Case> Case_List;

    private TextView No_Case_Found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__case);
        Define();


    }

    private void Define() {
        No_Case_Found=(TextView) findViewById(R.id.No_Case_Found);
        Case_Recycler_View=(RecyclerView)findViewById(R.id.Case_Recycler_View);
        Case_Recycler_View.setLayoutManager(new LinearLayoutManager(this));
        Case_List=new ArrayList<>();
    }

    public class Case_Adapter extends RecyclerView.Adapter<Case_Adapter.MyViewHolder> {
        Context context;
        ArrayList<Case> arrayList;

        public Case_Adapter(Context c, ArrayList<Case> p) {
            context = c;
            arrayList = p;
        }

        @NonNull
        @Override
        public Case_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Case_Adapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.case_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final Case_Adapter.MyViewHolder holder, final int position) {
            holder.Case_name.setText(arrayList.get(position).getName());
            holder.car_icon.setImageResource(R.drawable.file_icon);
            holder.Card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(View_Case.this,Starting_Slide.class)
                            .putExtra("id",arrayList.get(position).getNumber())
                            .putExtra("heading",arrayList.get(position).getName())
                            .putExtra("source","View"));
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView Case_name;
            ImageView car_icon;
            LinearLayout Card;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                Case_name = (TextView) itemView.findViewById(R.id.Case_name);
                car_icon = (ImageView) itemView.findViewById(R.id.car_icon);
                Card = (LinearLayout) itemView.findViewById(R.id.Card);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Case_List.clear();
        db=new Case_DB(this);
        Cursor res=db.Get_Case_data();
        int i=0;
        while (res.moveToNext())
        {
            if(res.getInt(8)==1)
            {
                Case Data=new Case();
                Data.setNumber(res.getInt(0));
                Data.setName(res.getString(1));
                Data.setFIR(res.getString(2));
                Data.setDate(res.getString(3));
                Data.setArea(res.getString(4));
                Data.setLocation(res.getString(5));
//            Toast.makeText(View_Case.this,res.getString(1),Toast.LENGTH_SHORT).show();
                Case_List.add(Data);
            }

        }

        if(Case_List.size()==0)
        {
            No_Case_Found.setVisibility(View.VISIBLE);
        }else
        {
            No_Case_Found.setVisibility(View.GONE);
        }

        case_adapter =new Case_Adapter(View_Case.this,Case_List);
        Case_Recycler_View.setAdapter(case_adapter);
    }

    public void Add_new_click(View view)
    {
        startActivity(new Intent(View_Case.this,New_Case.class));
        finish();
    }


}
