import javax.swing.*;
import java.awt.*;
public class Main 
{

	public static void main(String[]args)
    {
        JFrame window = new JFrame("STEVE RUN");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setPreferredSize(new Dimension(900,600));
        
        window.add(new GamePanel());
        window.pack();
        
        window.setVisible(true);
    }
}

