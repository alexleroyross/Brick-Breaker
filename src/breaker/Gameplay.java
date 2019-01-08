package breaker;

import java.awt.Color;
import java.awt.Font;
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
	
	private int plrSpeed;
	private int plrDir;
	
	private boolean died;
	
	private int score;
	
	public Gameplay()
	{
		gfxBlock = new ImageIcon("res/block.png");
		gfxBall = new ImageIcon("res/ball.png");
		gfxPlayer = new ImageIcon("res/player.png");
		
		unitPixels = 15;
		lvlWidthUnits = Main.windowWidth / unitPixels;
		lvlHeightUnits = Main.windowHeight / unitPixels;
		lvlUnits = lvlWidthUnits * lvlHeightUnits;
		
		numBlockRows = 30;
		level = new Rect[lvlHeightUnits][lvlWidthUnits];
		resetGame();
		//for(int i = 0; i < lvlUnits; i++)
		//	level[i] = 0;
	}
	
	public void resetGame()
	{
		plrHitbox = new Rect(Main.windowWidth / 2 - 15, 810, 135, 15);
		ballHitbox = new Rect(Main.windowWidth / 2, 795, 15, 15);
		
		died = false;
		
		score = 0;
		
		ballHdir = 0;
		ballVdir = 0;
		ballSpeed = 4;
		
		plrSpeed = 8;
		plrDir = 0;
		
		setLevel();
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
		
		g.setColor(Color.red.darker());
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Score: " + score, Main.windowWidth - 96, 22);
		
		if(died)
		{
			g.setColor(Color.red.darker());
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.drawString("Game Over", 175, 400);
		}
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
		if(!died)
		{
			for(int i = 0; i < lvlHeightUnits; i++)
			{
				for(int j = 0; j < lvlWidthUnits; j++)
				{
					if(level[i][j] != null)
					{
						if(colliding(level[i][j], ballHitbox))
						{
							handleBlockCollision(i, j);
							score++;
						}
					}
				}
			}
			
			if(colliding(ballHitbox, plrHitbox))
				handlePlayerCollision();
			
			if(ballHitbox.y > Main.windowHeight)
				died = true;
			
			if(ballHdir != 0)
				ballHitbox.x += ballHdir * ballSpeed;
			if(ballVdir != 0)
				ballHitbox.y += ballVdir * ballSpeed;
			
			if(plrDir != 0)
				plrHitbox.x += plrDir * plrSpeed;
			
			if(ballHitbox.x < 0 || ballHitbox.x + ballHitbox.w > Main.windowWidth)
				ballHdir = invertDir(ballHdir);
			if(ballHitbox.y < 0)
				ballVdir = invertDir(ballVdir);
		}
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
		
		// NOTE: Don't use level[i][j] anymore!!
		level[i][j] = null;
		
		if(ballHdir != 0)
		{
			t1.x = ballHitbox.x - ballSpeed * ballHdir;
			if(!colliding(t1, t2))
			{
				ballHdir = invertDir(ballHdir);
				return;
			}
			t1.x = ballHitbox.x;
		}
		if(ballVdir != 0)
		{
			t1.y = ballHitbox.y - ballSpeed * ballVdir;
			if(!colliding(t1, t2))
			{
				ballVdir = invertDir(ballVdir);
				return;
			}
			t1.y = ballHitbox.y;
		}
		
		
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
	public void keyPressed(KeyEvent e) {
		if(!died)
		{
			if(ballVdir == 0)
				ballVdir = 1;
			if(ballHdir == 0)
				ballHdir = 1;
			
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				plrDir = 1;
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
				plrDir = -1;
		}
		else
		{
			resetGame();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && plrDir == 1)
			plrDir = 0;
		if(e.getKeyCode() == KeyEvent.VK_LEFT && plrDir == -1)
			plrDir = 0;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
