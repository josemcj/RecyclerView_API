package com.josebv.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ServicioActivity extends AppCompatActivity {

    ShapeableImageView ivImgServicio, ivImgPrestador;
    TextView tvTitulo, tvDescripcion, tvNombrePrestador, tvInfoPrestador, tvPrecio;
    String idServicio;
    RequestQueue requestQueue;
    String URI_SERVICIO = "https://homecareplus.vercel.app/api/servicio/";
    String URI_IMG_USERS = "https://homecareplus.vercel.app/static/images/users/";
    String URI_IMG_SERVICIOS = "https://homecareplus.vercel.app/static/images/services/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);

        requestQueue = Volley.newRequestQueue(this);

        // Obtener el ID del servicio a consultar
        idServicio = getIntent().getStringExtra("idServicio");

        ivImgServicio = findViewById(R.id.imgServicio);
        ivImgPrestador = findViewById(R.id.imgPrestador);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvNombrePrestador = findViewById(R.id.tvNombrePrestador);
        tvInfoPrestador = findViewById(R.id.tvInfoPrestador);
        tvPrecio = findViewById(R.id.tvPrecio);

        getServicio(idServicio);

        // Boton de retorno
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Boton de retorno
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getServicio(String idServicio) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URI_SERVICIO + idServicio,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject res = new JSONObject(response);
                        Integer status = Integer.parseInt( res.getString("code") );

                        if (status == 200) {
                            JSONObject servicio = new JSONObject( res.getString("servicio") );
                            JSONObject prestador = new JSONObject( servicio.getString("prestadorDeServicio") );

                            String titulo = servicio.getString("titulo");
                            String descripcion = servicio.getString("descripcion");
                            String imagen = servicio.getString("imagen");
                            String precio = servicio.getString("precio");
                            String nombrePrestador = prestador.getString("nombre");
                            String profesionPrestador = prestador.getString("profesion");
                            String imagenPrestador = prestador.getString("imagen");

                            insertarDatos(titulo, imagen, descripcion, precio, nombrePrestador, profesionPrestador, imagenPrestador);

                        }

                    } catch (JSONException e) { e.printStackTrace(); }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        );

        requestQueue.add(stringRequest);

    }

    private void insertarDatos(String titulo, String imgServicio, String descripcion, String precio, String nombrePrestador, String profesionPrestador, String imgPrestador) {
        // Formatear numeros por miles
        DecimalFormat formato = new DecimalFormat("$###,###,###.##");
        Double precioServicio = Double.parseDouble(precio);

        tvTitulo.setText(titulo);
        tvDescripcion.setText(descripcion);
        tvPrecio.setText( formato.format(precioServicio) );
        tvNombrePrestador.setText(nombrePrestador);
        tvInfoPrestador.setText(profesionPrestador);

        // Cargar imagenes
        Picasso.get().load(URI_IMG_SERVICIOS + imgServicio).into(ivImgServicio);
        Picasso.get().load(URI_IMG_USERS + imgPrestador).into(ivImgPrestador);
    }
}