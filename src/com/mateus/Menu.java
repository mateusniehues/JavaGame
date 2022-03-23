package com.mateus;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

public class Menu extends MouseAdapter {

    private Jogo jogo;
    private Handler handler;
    private Random r = new Random();
    private HUD hud;
    int topscore1, topscore2, topscore3;

    public Menu(Jogo jogo, Handler handler, HUD hud) {
        this.jogo = jogo;
        this.handler = handler;
        this.hud = hud;
    }

    public void setTopscore1(int topscore1) {
        this.topscore1 = topscore1;
    }

    public void setTopscore2(int topscore2) {
        this.topscore2 = topscore2;
    }

    public void setTopscore3(int topscore3) {
        this.topscore3 = topscore3;
    }

    //verifica qual opcao o usuario clicou
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (jogo.estadoJogo == Jogo.STATE.Menu) {
            //botão play
            //inicia o jogo
            if (mouseSobre(mx, my, 210, 150, 200, 64)) {
                jogo.estadoJogo = Jogo.STATE.Jogo;
                handler.objeto.clear();
                handler.adicionarObjeto(new Jogador(Jogo.WIDTH / 2 - 32, Jogo.HEIGHT / 2 - 32, ID.Jogador, handler));
                handler.adicionarObjeto(new InimigoNormal(r.nextInt(Jogo.WIDTH), r.nextInt(Jogo.HEIGHT), ID.InimigoNormal));
            }

            //botão help
            //abre tela com ajuda sobre o jogo
            if (mouseSobre(mx, my, 210, 250, 200, 64)) {
                jogo.estadoJogo = Jogo.STATE.Ajuda;
            }


            //botão top scores
            //abre a tela dos maiores scores do jogo
            if (mouseSobre(mx, my, 450, 150, 160, 64)) {
                jogo.estadoJogo = Jogo.STATE.TopScore;
            }

            //botão quit
            //fecha o jogo
            if (mouseSobre(mx, my, 210, 350, 200, 64)) {
                System.exit(1);
            }
        }


        //botão back
        //volta para o menu principal
        if (jogo.estadoJogo == Jogo.STATE.Ajuda || jogo.estadoJogo == Jogo.STATE.TopScore) {
            if (mouseSobre(mx, my, 210, 350, 200, 64)) {
                jogo.estadoJogo = Jogo.STATE.Menu;
                return;
            }
        }

        //Quando o jogo vai para a tela end, verifica qual foi o score do jogador e verifica se é melhor que os scores atuais, caso seja, substitui
        // e gera o arquivo txt para salvar os scores e csv
        //Tambem reinicia o level e o score para um novo jogo
        if (jogo.estadoJogo == Jogo.STATE.Fim) {
            if (mouseSobre(mx, my, 210, 350, 200, 64)) {
                jogo.estadoJogo = Jogo.STATE.Menu;
                if (hud.getScore() >= topscore1) {
                    topscore3 = topscore2;
                    topscore2 = topscore1;
                    topscore1 = hud.getScore();
                } else if (hud.getScore() >= topscore2) {
                    topscore3 = topscore2;
                    topscore2 = hud.getScore();
                } else if (hud.getScore() >= topscore3) {
                    topscore3 = hud.getScore();
                }

                try {
                    Jogo.escritor("topscores.txt", topscore1, topscore2, topscore3);
                    Jogo.escritor("topscores.csv", topscore1, topscore2, topscore3);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                hud.setLevel(1);
                hud.score(0);
                for (int i = 0; i < 30; i++) {
                    handler.adicionarObjeto(new AnimacaoMenu(r.nextInt(jogo.WIDTH), r.nextInt(jogo.HEIGHT), ID.AnimacaoMenu));
                }
            }
        }
    }


    //verifica se o mouse esta sobre as coordenadas passadas
    private boolean mouseSobre(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            } else return false;
        } else return false;
    }

    public void tick() {

    }

    //Gera as telas do jogo
    public void render(Graphics g) {
        //caso seja menu, gera o menu principal
        if (jogo.estadoJogo == Jogo.STATE.Menu) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Menu", 240, 70);

            g.setFont(fnt2);

            g.drawRect(210, 150, 200, 64);
            g.drawString("Jogar", 275, 190);

            g.drawRect(210, 250, 200, 64);
            g.drawString("Ajuda", 275, 290);

            g.drawRect(210, 350, 200, 64);
            g.drawString("Sair", 275, 390);

            g.drawRect(450, 150, 160, 64);
            g.drawString("Scores", 480, 190);
        }
        //caso seja help, gera e tela de help
        else if (jogo.estadoJogo == Jogo.STATE.Ajuda) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);
            Font fnt3 = new Font("arial", 1, 20);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Ajuda", 240, 70);

            g.setFont(fnt3);
            g.drawString("Use as teclas W A S D  para se mover e desviar de inimigos.", 10, 200);
            g.drawString("Aperte P para pausar.", 10, 240);

            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Voltar", 265, 390);
        }
        //caso seja end, gera a tela do final do jogo
        else if (jogo.estadoJogo == Jogo.STATE.Fim) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);
            Font fnt3 = new Font("arial", 1, 20);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Fim de Jogo", 200, 70);

            g.setFont(fnt3);
            g.drawString("Voce morreu. Score: " + hud.getScore(), 200, 200);

            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Voltar", 265, 390);
        }
        // caso seja top score, gera as telas dos melhores scores
        else if (jogo.estadoJogo == Jogo.STATE.TopScore) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 20);
            Font fnt3 = new Font("arial", 1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Top Scores", 200, 70);

            g.setFont(fnt2);
            g.drawString("Top score 1: " + topscore1, 240, 150);
            g.drawString("Top score 2: " + topscore2, 240, 200);
            g.drawString("Top score 3: " + topscore3, 240, 250);

            g.setFont(fnt3);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Voltar", 265, 390);
        }
    }


}
