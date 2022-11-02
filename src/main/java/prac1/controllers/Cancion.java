package prac1.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Cancion {

    private String nombre;
    private Path ruta;
    private AudioFileFormat duracion;

    public String getNombre() {
        return nombre;
    }

    public Path getRuta() {
        return ruta;
    }

    public AudioFileFormat getDuracion() {
        return duracion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRuta(Path ruta) {
        this.ruta = ruta;
    }

    public void setDuracion(File file) throws UnsupportedAudioFileException, IOException {
        this.duracion = AudioSystem.getAudioFileFormat(file);
    }

    public Cancion(String nombre, Path ruta, AudioFileFormat duracion) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.duracion = duracion;
    }


    
}
