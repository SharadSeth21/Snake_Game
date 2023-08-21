import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeGame extends JFrame implements ActionListener {

    Board board;

    SnakeGame(){
        board = new Board();
        setTitle("Snake Game");
        add(board);
        setBounds(400 ,100 , 400 , 400);
        pack();
        setResizable(false);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        SnakeGame snakeGame = new SnakeGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    //restart the Game

}