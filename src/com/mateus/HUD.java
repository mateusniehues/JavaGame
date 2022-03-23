package com.mateus;

import java.awt.*;

public class HUD {

    public static int HEALTH = 100;
    private int greenValue = 255;
    private int score = 0;
    private int level = 1;

    //Atualiza a barra de vida do player
    public void tick() {
        HEALTH = (int)Jogo.clamp(HEALTH, 0, 100);
        greenValue =(int) Jogo.clamp(greenValue, 0, 255);
        greenValue = HEALTH * 2;
        score++;
    }

    //Gera a barra de vida, pontuação e o level mostrados na tela
    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(75, greenValue, 0));
        g.fillRect(15, 15, HEALTH * 2, 32);
        g.setColor(Color.white);
        g.drawRect(15, 15, 200, 32);

        g.drawString("Score: " + score, 10, 64);
        g.drawString("Level: " + level, 10, 80);
    }

    //Atualiza o score
    public void score (int score){
        this.score = score;
    }

    //Retorna o score
    public int getScore() {
        return score;
    }

    //Retorna o level
    public int getLevel() {
        return level;
    }

    //Define o level
    public void setLevel(int level) {
        this.level = level;
    }
}
