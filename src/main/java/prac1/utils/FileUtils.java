/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1.utils;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author manel
 */
public class FileUtils {
    
    /***
     * Retorna un MP3 de prova
     * 
     * @param instance
     * @return 
     */
    public static File getTestMP3(Object instance)
    {
        File ret = new File(instance.getClass().getClassLoader().getResource("sounds/test_sound.mp3").toExternalForm());
        
        return ret;
    }
    
    /***
     * Retorna una icona
     * 
     * @param instance
     * @param nom
     * @return 
     */
    public static File getIcona(Object instance, String nom)
    {
        File ret = new File(instance.getClass().getClassLoader().getResource("icons/" + nom).toExternalForm());
        
        return ret;
    }
    
    /***
     * Permet seleccionar un fitxer MP3 d'una unitat de disc
     * 
     * @return 
     */
    public static File getMP3Fromfile()
    {
        File ret = null;
        
        Stage stage1 = new Stage();
        
        FileChooser filechooser1 = new FileChooser();
        
        filechooser1.setTitle("Seleccionar fixter MP3");
        File fitxerMP3 = filechooser1.showOpenDialog(stage1);
        
        if (fitxerMP3 != null)
            ret = fitxerMP3;
        
        return ret;
    }
    
}
