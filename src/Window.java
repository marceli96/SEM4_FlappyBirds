import javax.swing.*;
import java.awt.*;

/**
 * Klasa odpowiedzialna za tworzenie okna.
 */
public class Window {

    /**
     * Konstruktor klasy
     *
     * @param width określa szerokość okna
     * @param height określa wysokość okna
     * @param flappy określa obiekt, który będzie wyświetlany w oknie
     */
    public Window(int width, int height, FlappyBird flappy){
        JFrame frame = new JFrame("FlappyBird");

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(flappy);
        frame.setVisible(true);
        flappy.start();
    }
}
