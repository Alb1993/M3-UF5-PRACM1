/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package prac1.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioFileFormat;
import prac1.utils.Cancion;
import prac1.utils.FileUtils;

/**
 * FXML Controller class
 *
 * @author manel
 */
public class MainScreenController implements Initializable {

    Media media = null;

    MediaPlayer player = null;

    @FXML
    ListView list_music;

    ObservableList<String> lista;

    ArrayList<Cancion> playlist;

    @FXML
    void on_botTestClic(ActionEvent event) {

        if (this.player != null) {
            player.play();
        }

    }

    @FXML
    void on_botAddClic(ActionEvent event) throws FileNotFoundException, IOException {
        
        InputStream entrada = null;
        Properties propiedades = new Properties();

        Path ruta = FileUtils.getMP3Fromfile();

        entrada = new FileInputStream(ruta.toFile());
        propiedades.load(entrada);
        String duracion = propiedades.getProperty("duration");
        String titulo = propiedades.getProperty("title");

        Cancion cancion = null;
        cancion.setNombre(titulo);
        cancion.setDuracion(duracion);
        cancion.setRuta(ruta);
        playlist.add(cancion);
        lista.add(titulo);
        list_music.refresh();

    }

    @FXML
    void on_botDeleteClic(ActionEvent event) {

    }

    /**
     * *
     * Inicialitza el controlador
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String path = FileUtils.getTestMP3(this);

        openMedia(path);

    }

    /**
     * *
     * Inicialitza el reproductor amb un fitxer MP3
     *
     * El format ha de ser de tipus URL
     *
     *
     */
    private void openMedia(String path) {
        try {

            // actuaslitzem el recurs MP3
            this.media = new Media(path);

            // inicialitzem el reproductor
            this.player = new MediaPlayer(media);

        } catch (MediaException e) {

            System.out.println("ERROR obrint fitxer demo: " + path + ":" + e.toString());
        }
    }
}
