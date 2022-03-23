package com.mateus;

import java.awt.*;
//classe que gera os inimigos rapidos
public class InimigoRapido extends ObjetosJogo {
    public InimigoRapido(int x, int y, ID id) {
        super(x, y, id);

        velX = 5;
        velY = 9;
    }

    //define o tamanho da colisão
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public void tick() {
        //define a velocidade dos inimigos
        x += velX;
        y += velY;

        if (y <= 0 || y >= Jogo.HEIGHT - 40) velY *= -1;
        if (x <= 0 || x >= Jogo.WIDTH - 16) velX *= -1;
    }

    public void render(Graphics g) {
        //define cor e tamanho dos inimigos
        g.setColor(Color.cyan);
        g.fillRect((int) x, (int) y, 16, 16);
    }
}
