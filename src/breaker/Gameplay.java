package breaker;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private ImageIcon gfxBlock;
	private ImageIcon gfxBall;
	private ImageIcon gfxPlayer;
	
	private int plrX;
	private int plrY;
	
	private int windowWidth;
	private int windowHeight;
	private int lvlWidthUnits;
	private int lvlHeightUnits;
	private int unitPixels;
	private int lvlUnits;
	
	private int numBlockRows;
	private int[] level;
	
	public Gameplay()
	{
		gfxBlock = new ImageIcon("block.png");
		gfxBall = new ImageIcon("ball.png");
		gfxPlayer = new ImageIcon("player.png");
		
		plrX = 0;
		plrY = 810;
		
		windowWidth = 900;
		windowHeight = 600;
		unitPixels = 15;
		lvlWidthUnits = windowWidth / unitPixels;
		lvlHeightUnits = windowHeight / unitPixels;
		lvlUnits = lvlWidthUnits * lvlHeightUnits;
		
		numBlockRows = 15;
		level = new int[lvlUnits];
		setLevel();
		//for(int i = 0; i < lvlUnits; i++)
		//	level[i] = 0;
	}
	
	// WHERE TO CALL THIS?
	public void gfxClose(Graphics g)
	{
		g.dispose();
	}
	
	public void gfxDraw(Graphics g)
	{
		gfxPlayer.paintIcon(this, g, plrX, plrY);
		
	}
	
	public void setLevel()
	{
		int i = 0;
		while(i < numBlockRows * lvlWidthUnits)
		{
			level[i] = 1;
			i++;
		}
		while(i < lvlUnits)
		{
			level[i] = 0;
			i++;
		}
	}
	
	public void tick()
	{
		
	}
	
	public void colliding()
	{
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
