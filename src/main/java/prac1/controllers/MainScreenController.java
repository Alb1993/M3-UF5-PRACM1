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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioFileFormat;
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
    
     @FXML
    private Button btn_play;
     
     @FXML
    private Button btn_pause;
     
     @FXML
    private Button btn_stop;
   
    private ObservableList<String> lista = FXCollections.observableArrayList();

    ArrayList<Cancion> playlist = new ArrayList<Cancion>();

    @FXML
    void on_botPlayClic(ActionEvent event) {

        if (this.player != null) {
            player.play();
            btn_play.setDisable(true);
            btn_pause.setDisable(false);
        }

    }
    
    @FXML
    void on_botPauseClic(ActionEvent event) {
        player.pause();
        btn_play.setDisable(false);
        btn_pause.setDisable(true);
    }
   
     @FXML
    void on_botBackward(ActionEvent event) {

    }
    
     @FXML
    void on_botForward(ActionEvent event) {

    }
    
    @FXML
    void on_botAddClic(ActionEvent event) {
        try {
            String path;
            File archivo = FileUtils.getMP3Fromfile();
            if (archivo != null) {
                path = archivo.toURI().toString();
                if (path != null) {
                    media = new Media(path);
                    player = new MediaPlayer(media);
                    String duracion = media.durationProperty().toString();
                    String titulo = archivo.getName();
                    String ruta = archivo.getAbsolutePath();
                    titulo = removerExtension(titulo);
                    Cancion cancion = new Cancion(titulo, duracion, ruta);
                    playlist.add(cancion);
                    lista.add(titulo);
                    list_music.setItems(lista);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    
    private String removerExtension(String titulo){
        int lastIndex = titulo.lastIndexOf('.');
        if (lastIndex != -1) {
            titulo = titulo.substring(0, lastIndex);
        }
        return titulo;  
    
    }
   
}
