package com.mycompany.buscaminaspoo;

import java.io.IOException;

public class Carga implements Runnable {

    @Override
    public void run() {
        PantallaCarga pc = new PantallaCarga();
        try {
            Thread.sleep(5000);
            pc.changeView();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
