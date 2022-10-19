/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package prac1.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import prac1.utils.FileUtils;

/**
 * FXML Controller class
 *
 * @author manel
 */
public class MainScreenController implements Initializable {
    
    Media m;
    
    MediaPlayer mp;
  
    /***
     * Inicialitza el controlador
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String fitxerDemo = FileUtils.getTestMP3(this).toString();
        
        if (openMedia(fitxerDemo))
        {
            mp.play();     
        }
        else
            mp = null;
    }
    
    /***
     * Inicialitza el reproductor amb un fitxer MP3
     * 
     */
    private boolean openMedia(String path)
    {
        
        boolean ret = false;
        
        try{
            
            this.m = new Media(path);

            this.mp = new MediaPlayer(m);

            ret = true;
            
        } catch (Exception e) {
            
            
        } 
        
        return ret;
    }
}
