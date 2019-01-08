package breaker;

import java.awt.Color;
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
	
	private int lvlWidthUnits;
	private int lvlHeightUnits;
	private int unitPixels;
	private int lvlUnits;
	
	private int numBlockRows;
	private Rect[][] level;
	
	private int ballHdir;
	private int ballVdir;
	private int ballSpeed;
	
	public Gameplay()
	{
		gfxBlock = new ImageIcon("res/block.png");
		gfxBall = new ImageIcon("res/ball.png");
		gfxPlayer = new ImageIcon("res/player.png");
		
		plrHitbox = new Rect(Main.windowWidth / 2 - 15, 810, 135, 15);
		ballHitbox = new Rect(Main.windowWidth / 2, 795, 15, 15);
		
		ballHdir = 0;
		ballVdir = 0;
		ballSpeed = 2;
		
		unitPixels = 15;
		lvlWidthUnits = Main.windowWidth / unitPixels;
		lvlHeightUnits = Main.windowHeight / unitPixels;
		lvlUnits = lvlWidthUnits * lvlHeightUnits;
		
		numBlockRows = 30;
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
		g.setColor(Color.black);
		g.fillRect(0, 0, Main.windowWidth, Main.windowHeight);
		
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
				}
				else
				{
					level[i][j] = null;
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
				if(level[i][j] != null)
					if(colliding(level[i][j], ballHitbox))
						handleBlockCollision(i, j);
			}
		}
		
		if(colliding(ballHitbox, plrHitbox))
			handlePlayerCollision();
	}
	
	public boolean colliding(Rect a, Rect b)
	{
		if(a.x + a.w <= b.x || a.x >= b.x + b.w || a.y + a.h <= b.y || a.y >= b.y + b.h)
			return false;
		
		return true;
	}
	
	public void handleBlockCollision(int i, int j)
	{
		Rect t1 = new Rect(ballHitbox.x, ballHitbox.y, ballHitbox.w, ballHitbox.h);
		Rect t2 = new Rect(level[i][j].x, level[i][j].y, level[i][j].w, level[i][j].h);
		
		t1.x = ballHitbox.x - ballSpeed;
		if(!colliding(t1, t2))
			ballHdir = invertDir(ballHdir);
		
		t1.x = ballHitbox.x + ballSpeed;
		if(!colliding(t1, t2))
			ballHdir = invertDir(ballHdir);
		
		t1.x = ballHitbox.x;

		t1.y = ballHitbox.y - ballSpeed;
		if(!colliding(t1, t2))
			ballVdir = invertDir(ballVdir);

		t1.y = ballHitbox.y + ballSpeed;
		if(!colliding(t1, t2))
			ballVdir = invertDir(ballVdir);
		
		t1.y = ballHitbox.y;
		
		level[i][j] = null;
		
	}
	
	public void handlePlayerCollision()
	{
		ballVdir = invertDir(ballVdir);
	}
	
	public int invertDir(int dir)
	{
		return -dir;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Main.renderer.repaint();
		Main.gameplay.tick();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(ballVdir == 0)
			ballVdir = 1;
		if(ballHdir == 0)
			ballHdir = 1;
		
		
		
		
		
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
