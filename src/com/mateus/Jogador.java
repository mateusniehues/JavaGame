package com.mateus;

import java.awt.*;
import java.util.Random;

//classe que gera o jogador
public class Jogador extends ObjetosJogo {

    private Random r = new Random();
    private Handler handler;

    public Jogador(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    //define o tamanho da colisão
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }



    public void tick() {
        //define a velocidade do jogador
        x += velX;
        y += velY;

        //nao deixa o jogador sair da janela do jogo
        x = Jogo.clamp(x, 0, Jogo.WIDTH - 50);
        y = Jogo.clamp(y, 0, Jogo.HEIGHT - 70);

        collision();
    }

    //verifica se o jogador bateu em algum objeto para retirar parte de sua vida
    private void collision() {
        for (int i = 0; i < handler.objeto.size(); i++) {
            ObjetosJogo tempObject = handler.objeto.get(i);

            if (tempObject.getId() == ID.InimigoNormal || tempObject.getId() == ID.InimigoRapido || tempObject.getId() == ID.InimigoInteligente) { // tempObject agora é um BasicEnemy
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.HEALTH -= 2;
                }
            }

        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //define o tamanho do jogador e o tamanho para colisão
        g.setColor(Color.green);
        g2d.draw(getBounds());
        g.setColor(Color.green);
        g.fillRect((int) x, (int) y, 32, 32);
    }
}
