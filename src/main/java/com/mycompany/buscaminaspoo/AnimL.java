
package com.mycompany.buscaminaspoo;

import java.io.FileNotFoundException;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class AnimL implements Runnable {
    
    Creditos c;
    ImageView i;
    public void cargarCreditos(ImageView i) {
        this.i = i;
    }
    

    @Override
    public void run() {
        int flag = 0;
        c = new Creditos();
        while(flag <= 100){
            
            try {
                c.animacionLuis(i);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            flag++;
        }
    }
    
}
