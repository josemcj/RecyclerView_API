package com.josebv.recyclerview;

public class serviciosList {

    private String imagen;
    private String titulo;
    private String descripcion;
    private String precio;

    public serviciosList(String imagen, String titulo, String descripcion, String precio) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }
}
