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


		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				int co = image[i][j]/3;
				g.setColor(new Color(co,co,co));
				g.drawRect(20+j, 20+i, 1, 1);
			}
		}
	}

	public mypanel(int[][] thing){
		this.image = thing;

		repaint();
	}

}
