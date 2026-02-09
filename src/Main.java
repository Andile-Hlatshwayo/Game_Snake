import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int boardWidth=600;
        int boardHeight=boardWidth;

        JFrame frame=new JFrame("Snake");
        frame.setVisible(true); // show the window
        frame.setSize(boardWidth,boardHeight); //window size
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        The_Game snakeGame=new The_Game(boardWidth,boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}