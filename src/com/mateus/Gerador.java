package com.mateus;

import java.util.Random;

public class Gerador {
    private Handler handler;
    private HUD hud;
    private int scoreKeep = 0;
    private Random r = new Random();

    public Gerador(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
    }

    //função de cria os inimigos de acordo com o nivel que o jogador esta
    // a cada 250 pontos o jogador avança 1 nivel
    public void tick() {
        scoreKeep++;
        if (scoreKeep >= 250) {
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);

            if (hud.getLevel() % 5 == 0) {
                handler.adicionarObjeto(new InimigoInteligente(r.nextInt(Jogo.WIDTH / 2), r.nextInt(Jogo.HEIGHT / 2), ID.InimigoInteligente, handler));;
            } else if (hud.getLevel() % 3 == 0) {
                handler.adicionarObjeto(new InimigoRapido(r.nextInt(Jogo.WIDTH / 2), r.nextInt(Jogo.HEIGHT / 2), ID.InimigoRapido));
            } else{
                handler.adicionarObjeto(new InimigoNormal(r.nextInt(Jogo.WIDTH / 2), r.nextInt(Jogo.HEIGHT / 2), ID.InimigoNormal));
            }
        }
    }
}
