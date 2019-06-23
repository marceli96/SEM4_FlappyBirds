import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Klasa odpowiedzialna za sterowanie grą.
 */
public class FlappyBird extends Canvas implements Runnable {

    public static final int WIDTH = 640, HEIGHT = 480;

    private final int SPEED = 4, TIME = 80, BIRD_X = 20, BIRD_Y = FlappyBird.HEIGHT/4;

    public double score = 0;

    private boolean running = false;

    public boolean started = false, gameOver = false;

    private Thread thread;
    private Room room;
    private Bird bird;
    private KeyInput keyInput;
    private BufferedImage texture;

    /**
     * Konstruktor klasy.
     */
    public FlappyBird(){
        new Window(WIDTH, HEIGHT,this);
        room = new Room(SPEED, TIME);
        bird = new Bird(BIRD_X, BIRD_Y, room.columns, this);
        keyInput = new KeyInput(bird);
        this.addKeyListener(keyInput);
        this.addMouseListener(new MouseInput(this));
        try {
            texture = ImageIO.read(getClass().getResource("/img/sky.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda uruchamiająca pracę wątka.
     *
     */
    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Metoda kończąca pracę wątka.
     *
     */
    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try{
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda tworząca nowy poziom.
     *
     */
    public void newRoom(){
        room = new Room(SPEED, TIME);
        bird = new Bird(BIRD_X, BIRD_Y, room.columns, this);
        this.removeKeyListener(keyInput);
        this.addKeyListener(keyInput = new KeyInput(bird));
        score = 0;
    }

    /**
     * Metoda odświeżająca ekran ze stała liczbą FPS'ów.
     *
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int fps = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update();
                render();
                fps++;
                delta--;
            }

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + fps);
                fps = 0;
            }
        }
        stop();
    }

    /**
     * Metoda rysująca wszystkie elemnty gry.
     *
     */
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(texture, 0, 0, WIDTH, HEIGHT, null);

        if(!started) {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 1, 40));
            g.drawString("Click to start the game!", 100, HEIGHT/2 - 20);
        }
        else if(gameOver) {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 1, 40));
            g.drawString("Game Over!", WIDTH/2 - 100, HEIGHT/2 - 50);
            g.drawString("Your score: " + (int)score, WIDTH/2 - 120, HEIGHT/2 - 20);
        }
        else {
            room.render(g);
            bird.render(g);

            g.setColor(Color.black);
            g.setFont(new Font("Arial", 1, 20));
            g.drawString("Score: " + (int)score, 10, 20);
        }

        g.dispose();
        bs.show();
    }

    /**
     * Metoda aktualizująca wszystkie elemnty gry.
     *
     */
    public void update(){
        if(started && !gameOver){
            room.update();
            bird.update();
        }
    }
    /**
     * Metoda startująca grę.
     *
     */
    public static void main(String[] args) {
        new FlappyBird();
    }
}