package com.example.examenfinal.ui;

import android.content.Context;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.examenfinal.NuevaNotaDialogViewModel;
import com.example.examenfinal.R;
import com.example.examenfinal.db.entity.NotaEntity;

import java.util.List;

public class MyNotaRecyclerViewAdapter extends RecyclerView.Adapter<MyNotaRecyclerViewAdapter.ViewHolder> {

    private List<NotaEntity> mValues;
    private Context ctx;
    NuevaNotaDialogViewModel viewModel;


    public MyNotaRecyclerViewAdapter(List<NotaEntity> items, Context ctx) {
        mValues = items;
        this.ctx = ctx;
        viewModel = ViewModelProviders.of((AppCompatActivity)ctx).get(NuevaNotaDialogViewModel.class);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitulo.setText(holder.mItem.getTitulo());
        holder.tvContenido.setText(holder.mItem.getContenido());

        if(holder.mItem.isFavorita()) {
            holder.ivFavorita.setImageResource(R.drawable.icono_estrella);
        }

        holder.ivFavorita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mItem.isFavorita()) {
                    holder.mItem.setFavorita(false);
                    holder.ivFavorita.setImageResource(R.drawable.nofavorito);

                } else {
                    holder.mItem.setFavorita(true);
                    holder.ivFavorita.setImageResource(R.drawable.icono_estrella);
                }
                viewModel.updateNota(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public void setNuevasNotas(List<NotaEntity> nuevasNotas) {
        this.mValues = nuevasNotas;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTitulo;
        public final TextView tvContenido;
        public final ImageView ivFavorita;
        public NotaEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitulo = view.findViewById(R.id.textViewTitulo);
            tvContenido = view.findViewById(R.id.textViewContenido);
            ivFavorita = view.findViewById(R.id.imageViewFavorita);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitulo.getText() + "'";
        }
    }
}
