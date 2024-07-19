import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyHandler implements KeyListener
{
	public boolean upPressed, startGame, resetGame, quitGame;
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			startGame = true;
			resetGame = true;
		}
		if (e.getKeyCode()==KeyEvent.VK_SPACE || e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode()==KeyEvent.VK_UP)
		{
			upPressed = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			quitGame = true;
		}
	}

	public void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			startGame = true;
			resetGame = true;
		}
		if (e.getKeyCode()==KeyEvent.VK_SPACE || e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode()==KeyEvent.VK_UP)
		{
			upPressed = false;
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			quitGame = true;
		}
	}

	public void keyTyped(KeyEvent e) {}
}
