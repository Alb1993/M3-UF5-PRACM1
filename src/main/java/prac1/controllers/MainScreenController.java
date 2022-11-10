package prac1.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.MapChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import prac1.utils.FileUtils;

/**
 * FXML Controller class
 *
 * @author FpShare
 *
 * Esta clase contiene todas las funcionalidades de nuestros controladores
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

    @FXML
    private Slider volumeSlider;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_forward;

    private final ObservableList<String> lista = FXCollections.observableArrayList();

    ArrayList<Cancion> playlist = new ArrayList<>();

    /**
     * en esta funcion hacemos play de la canción seleccionada y deshabilitamos
     * el mismo boton play a la vez que habilitamos los botones de fast forward,
     * fast backword, pause y stop,
     *
     * @param event
     */
    @FXML
    void on_botPlayClic(ActionEvent event) {
        int pos = list_music.getSelectionModel().getSelectedIndex();
        Cancion cancion = playlist.get(pos);
        openMedia(cancion.getRuta());
        if (this.player != null) {
            player.play();
            btn_play.setDisable(true);
            btn_pause.setDisable(false);
            btn_stop.setDisable(false);
            btn_back.setDisable(false);
            btn_forward.setDisable(false);
        } else {

        }
    }

    /**
     * en esta funcion acabamos con la repoduccion de la canción que este
     * sonando, deshabilitamos el mismo boton stop y el boton de pause a la vez
     * que habilitamos el boton de play
     *
     * @param event
     */
    @FXML
    void on_botStopClic(ActionEvent event) {
        if (this.player != null) {
            player.stop();
            btn_pause.setDisable(true);
            btn_stop.setDisable(true);
            btn_play.setDisable(false);

        }

    }

    /**
     * en esta función pausamos la canción que se este reprodciendo
     * deshabilitando el boton pause y habilitando el boton play
     *
     * @param event
     */
    @FXML
    void on_botPauseClic(ActionEvent event) {
        if (this.player != null) {
            player.pause();
            btn_play.setDisable(false);
            btn_pause.setDisable(true);
        }
    }

    /**
     * en esta funcuión hacemos que la canción que se este reproduciendo se le
     * reste 15 segundos y siga sondado des de ese punto
     *
     * @param event
     */
    @FXML
    void on_botBackward(ActionEvent event) {
        Duration currentTime = player.getCurrentTime();
        double time = currentTime.toSeconds();
        double newTime = (time - 15.00) * 1000;
        if (newTime < player.getStartTime().toMillis()) {
            player.seek(player.getStartTime());
        } else {
            Duration finalTime = new Duration(newTime);
            player.seek(finalTime);

        }

    }

    /**
     * en esta función hacemos que a la canción que este sonando se le añadan 15
     * segundos y siga sondando des de se punto
     *
     * @param event
     */

    @FXML
    void on_botForward(ActionEvent event) {
        Duration currentTime = player.getCurrentTime();
        double time = currentTime.toSeconds();
        double newTime = (time + 15.00) * 1000;
        if (newTime > player.getTotalDuration().toMillis()) {
            player.stop();
            btn_play.setDisable(false);
            btn_pause.setDisable(true);
            btn_stop.setDisable(true);
            btn_back.setDisable(true);
            btn_forward.setDisable(true);
        } else {
            Duration finalTime = new Duration(newTime);
            player.seek(finalTime);

        }
    }

    @FXML
    void on_botAddClic(ActionEvent event) {
        try {
            String path;
            Cancion cancion = new Cancion();
            File archivo = FileUtils.getMP3Fromfile();
            if (archivo != null) {
                path = archivo.toURI().toString();
                System.out.println(path);
                if (path != null) {
                    media = new Media(path);
                    //player = new MediaPlayer(media);
                    media.getMetadata().addListener((Change<? extends String, ? extends Object> c) -> {
                        if (c.wasAdded()) {
                            if ("title".equals(c.getKey())) {
                                String titulo = c.getValueAdded().toString();
                                System.out.println(titulo);
                                cancion.setNombre(titulo);
                                lista.add(titulo);
                            } else if ("duration".equals(c.getKey())) {
                                String duracion = c.getValueAdded().toString();
                                System.out.println(duracion);
                                cancion.setDuracion(duracion);
                            }
                        }

                    });
                    cancion.setRuta(FileUtils.normalizeURLFormat(path));
                    System.out.println(cancion.getNombre());
                    //String ruta = archivo.getAbsolutePath();
                    //titulo = removerExtension(titulo);
                    //Cancion cancion = new Cancion(titulo, ruta, duracion);
                    playlist.add(cancion);
                    list_music.setItems(lista);

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param event Este metodo lo que hace es comparar la canción seleccionada
     * por el usuario con las guardadas en la lista.
     */
    @FXML
    String onClickSong(ActionEvent event) {
        String selectedSong = list_music.getSelectionModel().getSelectedItem().toString();

        buscaCancion(selectedSong, playlist);

        return selectedSong;

    }

    @FXML
    void on_botDeleteClic(ActionEvent event) {
        String title = list_music.getSelectionModel().getSelectedItem().toString();
        borraCancion(title, playlist);
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

        // aqui instanciamos el slider para que funcione lo de regular el volumen
        volumeSlider.valueProperty().addListener((Observable observable) -> {
            player.setVolume(volumeSlider.getValue() / 100);
        });
    }

    /**
     *
     * @param title
     * @param playlist Este metodo devuelve la cancion de la lista que se
     * asemeja a la enviada en el string title
     */
    public Cancion buscaCancion(String title, ArrayList<Cancion> playlist) {
        Cancion ret = null;
        for (int i = 0; i < playlist.size(); i++) {
            Cancion c = playlist.get(i);
            if (c.getNombre().equals(title)) {
                ret = playlist.get(i);

            }
        }

        return ret;
    }

    public void borraCancion(String title, ArrayList<Cancion> playlist) {
        for (int i = 0; i < playlist.size(); i++) {
            Cancion c = playlist.get(i);
            if (c.getNombre().equals(title)) {
                playlist.remove(i);
                lista.remove(i);
                list_music.refresh();
            }
        }
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

    private String removerExtension(String titulo) {
        int lastIndex = titulo.lastIndexOf('.');
        if (lastIndex != -1) {
            titulo = titulo.substring(0, lastIndex);
        }
        return titulo;

    }

}
