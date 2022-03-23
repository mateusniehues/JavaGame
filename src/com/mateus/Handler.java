package com.mateus;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    LinkedList <ObjetosJogo> objeto = new LinkedList<ObjetosJogo>();

    public void tick(){
        for(int i = 0; i< objeto.size(); i++){
            ObjetosJogo tempObject = objeto.get(i);

            tempObject.tick();
        }
    }

    public void render(Graphics g){
        for( int i =0; i< objeto.size();i++){
            ObjetosJogo tempObject = objeto.get(i);

            tempObject.render(g);
        }
    }

    public void adicionarObjeto(ObjetosJogo objeto){
        this.objeto.add(objeto);
    }

}
