package main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class mypanel extends JPanel{

	private int[][] image;

	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);

		for (int y = 0; y < image[0].length; y++) {
			for (int x = 0; x < image.length; x++) {
				int co = image[x][y]/3;
				g.setColor(new Color(co,co,co));
				g.drawRect(20+x, 20+y, 1, 1);
			}
		}
	}

	public mypanel(int[][] thing){
		this.image = thing;
		repaint();
	}

}
