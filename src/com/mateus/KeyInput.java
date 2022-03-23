package com.mateus;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private boolean[] kewDown = new boolean[4];
    Jogo jogo;

    public KeyInput(Handler handler, Jogo game) {
        this.handler = handler;
        this.jogo = game;
        kewDown[0] = false;
        kewDown[1] = false;
        kewDown[2] = false;
        kewDown[3] = false;
    }

    //Verifica se as teclas W A ou D estão sendo pressionadas para movimentar o player
    //Verifica também se a tecla P foi pressionada para pausar o jogo
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();


        for (int i = 0; i < handler.objeto.size(); i++) {
            ObjetosJogo tempObject = handler.objeto.get(i);

            if (tempObject.getId() == ID.Jogador) {
                // key events for player 1
                if (key == KeyEvent.VK_W) {
                    tempObject.setVelY(-5);
                    kewDown[0] = true;
                }
                if (key == KeyEvent.VK_S) {
                    tempObject.setVelY(5);
                    kewDown[1] = true;
                }
                if (key == KeyEvent.VK_D) {
                    tempObject.setVelX(5);
                    kewDown[2] = true;
                }
                if (key == KeyEvent.VK_A) {
                    tempObject.setVelX(-5);
                    kewDown[3] = true;
                }
            }
        }
        if (key == KeyEvent.VK_ESCAPE) System.exit(1);
        if (jogo.estadoJogo == Jogo.STATE.Jogo) {
            if (key == KeyEvent.VK_P) {
                if (Jogo.paused) Jogo.paused = false;
                else Jogo.paused = true;
            }
        }


    }

    //Verifica se a tecla W A S ou D foi solta e atualiza seu estado pra false para que o player nao se mova mais na direção
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.objeto.size(); i++) {
            ObjetosJogo tempObject = handler.objeto.get(i);

            if (tempObject.getId() == ID.Jogador) {
                if (key == KeyEvent.VK_W) kewDown[0] = false;
                if (key == KeyEvent.VK_S) kewDown[1] = false;
                if (key == KeyEvent.VK_D) kewDown[2] = false;
                if (key == KeyEvent.VK_A) kewDown[3] = false;


                //movimento veritcal
                if (!kewDown[0] && !kewDown[1]) tempObject.setVelY(0);

                //movimento horizontal
                if (!kewDown[2] && !kewDown[3]) tempObject.setVelX(0);
            }


        }
    }
}
