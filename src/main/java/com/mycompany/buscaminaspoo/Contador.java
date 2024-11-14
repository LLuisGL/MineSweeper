package com.mycompany.buscaminaspoo;
import controlador.UsuarioController;
import java.io.FileNotFoundException;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Contador implements Runnable {
    String contadorS = "00";
    String contadorM = "00";
    String contadorH = "00";
    boolean flag = false;
    int ganador = 0;
    ImageView iS;
    ImageView iM;
    ImageView iH;
    
    public void cargarContadorS(ImageView l){
        this.iS = l;
    }
    public void cargarContadorM(ImageView l){
        this.iM = l;
    }
    public void cargarContadorH(ImageView l){
        this.iH = l;
    }
    
    public void cambiarFlag(int num){
        this.flag = true;
        this.ganador = num;
    }
    
    int score = 595959;
    UsuarioController uc = new UsuarioController();
    @Override
    public void run() {
        Clasico c = new Clasico();
        for(int i=0; i<217; i++){
            if(flag == true && ganador != 0){
                    System.out.println(score);
                    uc.createOrUpdateScore(score);
                    break;
            } else if(flag == true){
                break;
            }
            try {
                score -= 1231;
                c.animacionContador(iH, iM, iS);
                Thread.sleep(1000);
                contadorS = String.valueOf(Integer.parseInt(contadorS) + 1);
                if(contadorS == "60"){
                    contadorS = "00";
                    contadorM = String.valueOf(Integer.parseInt(contadorM) + 1);
                    if(contadorM == "60"){
                        contadorM = "00";
                        contadorH = String.valueOf(Integer.parseInt(contadorH) + 1);        
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            
        }
    }
}
