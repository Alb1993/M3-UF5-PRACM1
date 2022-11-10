package prac1.controllers;

public class Cancion{

    private String nombre;
    private String duracion;
    private String ruta;

    public String getNombre() {
        return nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Cancion(String nombre, String ruta, String duracion) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.duracion = duracion;
    }
        public Cancion() {
        this.nombre = "";
        this.ruta = "";
        this.duracion = "";
    }

}
