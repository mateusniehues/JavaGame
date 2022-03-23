package com.mateus;

import java.awt.*;
import java.util.Random;

// essa classe gera os quadradinhos que ficam rebatendo no menu do jogo
public class AnimacaoMenu extends ObjetosJogo {

    private Random r = new Random();
    private Color col;

    private int dir = 0;

    public AnimacaoMenu(int x, int y, ID id) {
        super(x, y, id);

        dir = r.nextInt(2);
        if (dir == 0) {
            velX = 5;
            velY = 9;
        } else if (dir == 1) {
            velX = 9;
            velY = 5;
        }


        col = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Jogo.HEIGHT - 40) velY *= -1;
        if (x <= 0 || x >= Jogo.WIDTH - 16) velX *= -1;
    }

    public void render(Graphics g) {
        g.setColor(col);
        g.fillRect((int) x, (int) y, 16, 16);
    }
}
