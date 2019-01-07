package breaker;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {

	public static JFrame obj;
	public static Gameplay gameplay;
	public static Renderer renderer;
	public static Timer timer;
	
	public static void main(String[] args) {
		obj = new JFrame();
		gameplay = new Gameplay();
		renderer = new Renderer();
		timer = new Timer(20, gameplay);
		
		obj.setTitle("Brick Breaker");
		obj.add(renderer);
		obj.setBounds(20, 20, 900, 600);
		obj.setBackground(Color.white);
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);
		
		timer.start();
	}

}
