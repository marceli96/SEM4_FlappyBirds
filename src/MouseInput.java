import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Klasa odpowiedzialna za obsługę myszy.
 */
public class MouseInput implements MouseListener {

    private FlappyBird flappy;

    /**
     * Konstruktor klasy.
     *
     * @param flappy określa obiekt, który będzie wyświetlany w oknie
     */
    public MouseInput(FlappyBird flappy){
        this.flappy = flappy;
    }

    /**
     * Metoda obsługująca naciśniecie myszy.
     *
     * @param e wydarzenie wciśnięcia myszy
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!flappy.started)
            flappy.started = true;

        if(flappy.gameOver){
            flappy.newRoom();
            flappy.gameOver = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
