package com.josebv.recyclerview;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<serviciosList> listaServicios;
    String URL_IMG_SERVICIOS = "https://homecareplus.vercel.app/static/images/services/";

    public Adaptador(Context context, ArrayList<serviciosList> listaServicios, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.listaServicios = listaServicios;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.servicio_list_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        serviciosList servicio = listaServicios.get(position);

        // Cargar imagen desde la API
        Picasso.get().load(URL_IMG_SERVICIOS + servicio.getImagen()).into(holder.imgServicio);

        holder.titulo.setText( servicio.getTitulo() );
        holder.descripcion.setText( servicio.getDescripcion() );
        holder.precio.setText( "$" + servicio.getPrecio() );
    }

    @Override
    public int getItemCount() {
        return listaServicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgServicio;
        TextView titulo, descripcion, precio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgServicio = itemView.findViewById(R.id.imgServicio);
            titulo = itemView.findViewById(R.id.titulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            precio = itemView.findViewById(R.id.precio);

            // Se añade para detectar un clic en el item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
