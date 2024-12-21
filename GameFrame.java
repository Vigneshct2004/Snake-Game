import javax.swing.*;
public class GameFrame extends JFrame{

        GameFrame(){
              this.add(new GamePanel());
              setTitle("Snake Game");
              setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              setResizable(false);
              pack();
              setVisible(true);
              setLocationRelativeTo(null);
            }
}
