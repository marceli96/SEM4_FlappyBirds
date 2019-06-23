import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa odpowiedzialna za tworzenie poziomu.
 */
public class Room {

    public ArrayList<Rectangle> columns;
    private Random rand;

    private BufferedImage textureUp;
    private BufferedImage textureDown;

    private int speed, time, currentTime = 0;

    private final int SPACE = 100, COLUMN_WIDTH = 40;

    /**
     * Konstruktor klasy.
     *
     * @param speed określa jak szybko będą przesuwały się kolumny
     * @param time określa częstotliwość pojawiania się kolumn na ekranie
     */
    public Room(int speed, int time){
        this.speed = speed;
        this.time = time;
        columns = new ArrayList<>();
        rand = new Random();
        try {
            textureUp = ImageIO.read(getClass().getResource("/img/columnUp.png"));
            textureDown = ImageIO.read(getClass().getResource("/img/columnDown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Metoda odpowiedzialna za tworzenie i usuwanie kolumn, aktulizację położenia kolumn oraz zliczanie punktów.
     *
     */
    public void update(){
        currentTime++;
        if(currentTime == time){
            currentTime = 0;

            int height1 = rand.nextInt((FlappyBird.HEIGHT/2)-30) + 30;

            int y2 = height1 + SPACE;
            int height2 = FlappyBird.HEIGHT - y2;

            columns.add(new Rectangle(FlappyBird.WIDTH, 0, COLUMN_WIDTH, height1));
            columns.add(new Rectangle(FlappyBird.WIDTH, y2, COLUMN_WIDTH, height2));
        }

        for(int i = 0; i < columns.size(); i++){
            Rectangle rect = columns.get(i);
            rect.x -= speed;

            if(rect.x + COLUMN_WIDTH <= 0)
                columns.remove(i--);
        }
    }

    /**
     * Metoda rysująca kolumny na ekranie.
     *
     * @param g obiekt, na którym mają zostać narysowane kolumny
     */
    public void render(Graphics g){
        g.setColor(Color.green);

        for(int i = 0; i < columns.size(); i++){
            Rectangle rect = columns.get(i);
            if(i % 2 == 0)
                g.drawImage(textureUp.getSubimage(0,textureUp.getHeight() - rect.height, textureUp.getWidth(),rect.height), rect.x, rect.y, rect.width, rect.height, null);
            else
                g.drawImage(textureDown.getSubimage(0 ,0, textureUp.getWidth(), rect.height), rect.x, rect.y, rect.width, rect.height, null);
        }
    }
}