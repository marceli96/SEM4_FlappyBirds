import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Klasa odpowiedzialna za obsługę klawiatury.
 */
public class KeyInput extends KeyAdapter {

    private Bird bird;

    /**
     * Konstruktor klasy.
     *
     * @param bird określa ptaka, który jest aktualnie wyświetlany w grze
     */
    public KeyInput(Bird bird){
        this.bird = bird;
    }

    /**
     * Metoda obsługująca naciśniecie "Spacji", prawej oraz lewej strzałki.
     *
     * @param e wydarzenie wciśnięcia klawisza
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            bird.setSpeedY(-5);
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            bird.setSpeedX(-4);
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            bird.setSpeedX(4);
    }

    /**
     * Metoda obsługująca puszczenie prawej oraz lewej strzałki.
     *
     * @param e wydarzenie wciśnięcia klawisza
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            bird.setSpeedX(0);
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            bird.setSpeedX(0);
    }

    @Override
    public void keyTyped(KeyEvent e) { }
}
