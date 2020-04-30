package com.example.todo.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.db.model.Grupa;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Grupa> data;
    private OnItemClickListener listener;

    public MyAdapter(ArrayList<Grupa> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public Grupa get(int position) {
        return data.get(position);

    }

    public void clear(){
        data.clear();

    }

    public void addAll(List<Grupa> grupaList){

        data.addAll(grupaList);

    }
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.single_item, parent, false);


        return new MyAdapter.MyViewHolder(view, listener);
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.tvNaziv.setText(data.get(position).getDbNaziv()+"\nKreirano: "+data.get(position).getDbDatum()+" u "+data.get(position).getDbVreme());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNaziv;
        private OnItemClickListener vhListener;


        public MyViewHolder(@NonNull View itemView, OnItemClickListener vhListener) {
            super(itemView);
            tvNaziv = itemView.findViewById(R.id.tvNaziv);
            this.vhListener = vhListener;
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            vhListener.OnItemClick(getAdapterPosition());
        }


    }
}
