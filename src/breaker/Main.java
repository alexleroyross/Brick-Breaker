package breaker;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {

	public static JFrame obj;
	public static Gameplay gameplay;
	public static Renderer renderer;
	public static Timer timer;
	public static int windowWidth;
	public static int windowHeight;
	
	public static void main(String[] args) {
		windowWidth = 600;
		windowHeight = 900;
		
		obj = new JFrame();
		gameplay = new Gameplay();
		renderer = new Renderer();
		timer = new Timer(20, gameplay);
		
		obj.setTitle("Brick Breaker");
		obj.add(renderer);
		obj.setBounds(20, 20, windowWidth, windowHeight);
		obj.addKeyListener(gameplay);
		obj.setBackground(Color.black);
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);
		
		timer.start();
	}

}
