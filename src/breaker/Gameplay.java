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
	
	Rect plrHitbox;
	Rect ballHitbox;
	
	private int windowWidth;
	private int windowHeight;
	private int lvlWidthUnits;
	private int lvlHeightUnits;
	private int unitPixels;
	private int lvlUnits;
	
	private int numBlockRows;
	private Rect[][] level;
	
	public Gameplay()
	{
		gfxBlock = new ImageIcon("block.png");
		gfxBall = new ImageIcon("ball.png");
		gfxPlayer = new ImageIcon("player.png");

		windowWidth = 900;
		windowHeight = 600;
		
		plrHitbox = new Rect(windowWidth / 2 - 15, 810, 135, 15);
		ballHitbox = new Rect(windowWidth / 2, 795, 15, 15);
		
		unitPixels = 15;
		lvlWidthUnits = windowWidth / unitPixels;
		lvlHeightUnits = windowHeight / unitPixels;
		lvlUnits = lvlWidthUnits * lvlHeightUnits;
		
		numBlockRows = 15;
		level = new Rect[lvlHeightUnits][lvlWidthUnits];
		setLevel();
		//for(int i = 0; i < lvlUnits; i++)
		//	level[i] = 0;
	}
	
	// WHERE TO CALL THIS?
	public void gfxClose(Graphics g)
	{
		g.dispose();
	}
	
	public void repaint(Graphics g)
	{
		gfxPlayer.paintIcon(this, g, plrHitbox.x, plrHitbox.y);
		for(int i = 0; i < lvlHeightUnits; i++)
		{
			for(int j = 0; j < lvlWidthUnits; j++)
			{
				if(level[i][j] != null)
				{
					gfxBlock.paintIcon(this, g, level[i][j].x, level[i][j].y);
				}
			}
		}
		gfxBall.paintIcon(this, g, ballHitbox.x, ballHitbox.y);
	}
	
	public void setLevel()
	{
		for(int i = 0; i < lvlHeightUnits; i++)
		{
			for(int j = 0; j < lvlWidthUnits; j++)
			{
				if(i < numBlockRows)
				{
					level[i][j] = new Rect(j * unitPixels, i * unitPixels, unitPixels, unitPixels);
					System.out.println(i);
				}
			}
		}
	}
	
	public void tick()
	{
		for(int i = 0; i < lvlHeightUnits; i++)
		{
			for(int j = 0; j < lvlWidthUnits; j++)
			{
				if(colliding(level[i][j], ballHitbox))
					handleBlockCollision();
			}
		}
		
		if(colliding(ballHitbox, plrHitbox))
			handlePlayerCollision();
	}
	
	// I DON'T THINK THIS IS RIGHT
	public boolean colliding(Rect a, Rect b)
	{
		if((a.x + a.w > b.x && a.x < b.x + b.w && a.y + a.h > b.y && a.y < b.y + b.h))
			return true;
			
		return false;
	}
	
	public void handleBlockCollision()
	{
		
	}
	
	public void handlePlayerCollision()
	{
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Main.renderer.repaint();
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
