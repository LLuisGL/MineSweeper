package com.mycompany.buscaminaspoo;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AnimMenu implements Runnable {
    
    MenuPrincipal m;
    VBox i;
    public void cargarMenu(VBox i) {
        this.i = i;
    }
    int flag;
    
    public void setFlag(int a){
        this.flag = a;
    }
    
    @Override
    public void run() {
        flag = 0;
        m = new MenuPrincipal();
        while(flag <= 100){
            try {
                m.animacionFondo(i);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AnimMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            flag++;
        }
    }
    
}
