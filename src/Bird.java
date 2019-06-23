import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa odpowiedzialna za ptaka w grze.
 */
public class Bird extends Rectangle {
    private double speedY = 0, speedX = 0;
    private double gravity = 0.3;

    private BufferedImage texture;
    private ArrayList<Rectangle> columns;
    private FlappyBird flappy;

    /**
     * Konstruktor klasy.
     *
     * @param x określa startowe położenie ptaka na osi x
     * @param y określa startowe położenie ptaka na osi y
     * @param columns określa listę kolumn, z którymi będzie sprawdzana kolizja
     * @param flappy określa obiekt, który będzie wyświetlany w oknie
     */
    public Bird(int x, int y, ArrayList<Rectangle> columns, FlappyBird flappy){
        this.columns = columns;
        this.flappy = flappy;
        try {
            texture = ImageIO.read(getClass().getResource("/img/bird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.x = x;
        this.y = y;
        this.height = texture.getHeight();
        this.width = texture.getWidth();
    }

    /**
     * Metoda zmieniająca szybkość spadania ptaka.
     *
     * @param speedY określa nową prędkość ptaka w pionie
     */
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    /**
     * Metoda zmieniająca szybkość przesuwania ptaka w poziomie.
     *
     * @param speedX określa nową prędkość ptaka w poziomie
     */
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }


    /**
     * Metoda aktualizująca położenie ptaka oraz sprawdzająca kolizję z kolumnami i z dołem ekranu.
     *
     */
    public void update(){
        y += speedY;
        x += speedX;
        speedY += gravity;

        if(y < 0){
            y = 0;
            speedY = 0;
        }

        if(x < 0)
            x = 0;
        if(x + width > FlappyBird.WIDTH)
            x = FlappyBird.WIDTH - width;

        if(speedY > 10)
            speedY = 10;

        for (Rectangle rect: columns) {
            if(this.intersects(rect))
                flappy.gameOver = true;
            if(y + height > FlappyBird.HEIGHT)
                flappy.gameOver = true;
            if(x + width / 2 == rect.x + rect.width / 2 && rect.y == 0 && speedX == 0)
                flappy.score++;
            else if(x + width / 2 > rect.x + rect.width / 2 - speedX - 0.01 && x + width / 2 < rect.x + rect.width / 2 + speedX && rect.y == 0 && speedX > 0)
                flappy.score++;
        }
    }

    /**
     * Metoda rysująca ptaka na ekranie.
     *
     * @param g obiekt, na którym ma zostać narysowany ptak
     */
    public void render(Graphics g){
        g.drawImage(texture, x, y, width, height, null);
    }
}
