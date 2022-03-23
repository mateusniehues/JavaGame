package com.mateus;


import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.util.Random;
import java.util.Scanner;


public class Jogo extends Canvas implements Runnable {

    private static final long serialVersionUID = 373302601927338074L;

    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;
    public static boolean paused = false;

    private Random r;
    private Handler handler;
    private HUD hud;
    private Gerador gerador;
    private static Menu menu;

    public enum STATE {
        Menu,
        Ajuda,
        Jogo,
        Fim,
        TopScore
    }


    public STATE estadoJogo = STATE.Menu;


    public Jogo() {
        handler = new Handler();
        hud = new HUD();
        menu = new Menu(this, handler, hud);
        this.addKeyListener(new KeyInput(handler, this));
        this.addMouseListener(menu);


        try {
            leitor("topscores.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Janela(WIDTH, HEIGHT, "Jogo", this);

        gerador = new Gerador(handler, hud);


        r = new Random();

        if (estadoJogo == STATE.Jogo) {
            handler.adicionarObjeto(new Jogador(Jogo.WIDTH / 2 - 32, Jogo.HEIGHT / 2 - 32, ID.Jogador, handler));
            handler.adicionarObjeto(new InimigoNormal(r.nextInt(Jogo.WIDTH), r.nextInt(Jogo.HEIGHT), ID.InimigoNormal));
        } else {
            for (int i = 0; i < 30; i++) {
                handler.adicionarObjeto(new AnimacaoMenu(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.AnimacaoMenu));
            }
        }


    }
    //inicia o jogo
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    //para o jogo quando sai
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //renderiza a tela do jogo e pega todos seus dados como frames, timer e atualiza de tempo em tempo
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    //atualiza o jogo de acordo com cada classe a atributo executado fazendo com que ele fique rodando de forma linear
    private void tick() {

        if (estadoJogo == STATE.Jogo) {
            if (!paused) {
                handler.tick();
                hud.tick();
                gerador.tick();
                if (HUD.HEALTH <= 0) {
                    HUD.HEALTH = 100;
                    handler.objeto.clear();
                    estadoJogo = STATE.Fim;
                }
            }
        } else if (estadoJogo == STATE.Menu || estadoJogo == STATE.Fim ||estadoJogo == STATE.Ajuda || estadoJogo == STATE.TopScore) {
            menu.tick();
            handler.tick();
        }

    }

    //gera a janela do jogo e verifica em qual tela ele se encontra
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);


        handler.render(g);
        if (paused) {
            g.drawString("PAUSED", 100, 100);
        }
        if (estadoJogo == STATE.Jogo) {
            hud.render(g);
        } else if (estadoJogo == STATE.Menu || estadoJogo == STATE.Ajuda || estadoJogo == STATE.Fim || estadoJogo == STATE.TopScore) {
            menu.render(g);
        }


        g.dispose();
        bs.show();
    }

    //função para que nao permite o player ou os inimigos sairem da tela
    public static float clamp(float var, float min, float max) {
        if (var >= max)
            return var = max;
        else if (var <= min)
            return var = min;
        else
            return var;
    }

    public static void main(String[] args) {
        new Jogo();
    }


    //leitor do arquivo scores que foi salvo em txt
    public static void leitor(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha = "";
        int contadorLinha = 1;
        while (true) {
            if (linha != null) {
                if(contadorLinha==1) {
                    linha = buffRead.readLine();
                    System.out.println(linha+ "  1");
                    int numero = Integer.parseInt(linha);
                    menu.setTopscore1(numero);
                    contadorLinha++;

                }else if(contadorLinha==2){
                    linha = buffRead.readLine();
                    System.out.println(linha + "  2");
                    int numero = Integer.parseInt(linha);
                    menu.setTopscore2(numero);
                    contadorLinha++;
                }else if(contadorLinha==3){
                    linha = buffRead.readLine();
                    System.out.println(linha + "  3");
                    int numero = Integer.parseInt(linha);
                    menu.setTopscore3(numero);
                    contadorLinha++;
                    break;
                }

            } else
                break;

        }
        buffRead.close();
    }

    //gerador do arquivo de melhores scores
    public static void escritor(String path, int score1, int score2, int score3) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
        Scanner in = new Scanner(System.in);

        buffWrite.append(score1 + "\n" + score2 + "\n" + score3);
        buffWrite.close();
    }




}
