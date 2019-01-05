package main;

/**
 * Created by perri on 22/12/2018.
 */

import javax.swing.*;
import java.awt.*;

public class Window {

    public static void main(String[] args)
    {
        JFrame frame = new JFrame(StaticValues.WINDOW_TITLE);

        Game game = new Game(StaticValues.WINDOW_WIDTH, StaticValues.WINDOW_HEIGHT);

        frame.setPreferredSize(new Dimension(StaticValues.WINDOW_WIDTH, StaticValues.WINDOW_HEIGHT));
        frame.setMaximumSize(new Dimension(StaticValues.WINDOW_WIDTH, StaticValues.WINDOW_HEIGHT));
        frame.setMinimumSize(new Dimension(StaticValues.WINDOW_WIDTH, StaticValues.WINDOW_HEIGHT));

        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



    }
}
