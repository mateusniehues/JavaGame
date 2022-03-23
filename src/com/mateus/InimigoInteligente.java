package com.mateus;

import java.awt.*;

public class InimigoInteligente extends ObjetosJogo {
    private Handler handler;
    private ObjetosJogo player;

    public InimigoInteligente(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        for (int i = 0; i < handler.objeto.size(); i++) {
            if (handler.objeto.get(i).getId() == ID.Jogador) player = handler.objeto.get(i);
        }


    }

    //define o tamanho da colisão
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    //essa função permite que o SmartEnemy sempre siga o player de acordo com sua posição atual
    public void tick() {
        x += velX;
        y += velY;

        float diffX = x - player.getX() - 8;
        float diffY = y - player.getY() - 8;
        float distance = (float) Math.sqrt((x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));

        velX = (float) ((-1.0 / distance) * diffX);
        velY = (float) ((-1.0 / distance) * diffY);

        if (y <= 0 || y >= Jogo.HEIGHT - 40) velY *= -1;
        if (x <= 0 || x >= Jogo.WIDTH - 16) velX *= -1;
    }

    public void render(Graphics g) {
        //define cor e tamanho dos inimigos
        g.setColor(Color.green);
        g.fillRect((int) x, (int) y, 16, 16);
    }
}
