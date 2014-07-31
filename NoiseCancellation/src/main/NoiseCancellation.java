package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class NoiseCancellation {

	public static void main(String[] args) {
		if (args.length != 1) {
			JOptionPane.showMessageDialog(null, "Only one arguement");
			System.exit(0);
		} else if (!args[0].endsWith(".png")) {
			JOptionPane.showMessageDialog(null,
					"Please only give me a png file");
			System.exit(0);
		}
		String imagepath = args[0];
		new NoiseCancellation(imagepath);
	}

	public NoiseCancellation(String imagepath) {
		int[][] img;
		int[][] output;
		
		try {
			BufferedImage bi = null;
			while(bi==null){
				bi = ImageIO.read(new File(imagepath));
			}

			// create 2d array images
			img = new int[bi.getHeight()][bi.getWidth()];
			output = new int[bi.getWidth()][bi.getHeight()];

			// read in image pixels
			for (int i = 0; i < bi.getWidth(); i++) {
				for (int j = 0; j < bi.getHeight(); j++) {

					int rgb = bi.getRGB(i, j);
					int r = (rgb >> 16) & 0xFF;
					int g = (rgb >> 8) & 0xFF;
					int b = (rgb & 0xFF);
					int gray = (r + g + b) / 3;

					img[j][i] = gray*2;
				}
			}
			
			displayImage("original",img);
			
			// create convolution masks
			double[][] smoothingMask = {{0.11,0.11,0.11},
					{0.11,0.11,0.11},
					{0.11,0.11,0.11}};
			
			int[][] medianFilterMask = {{5,5,6},
					{3,4,7},
					{8,2,2}};
			
			// apply to original image
			
			// for each pixel (using 0 for the outside pixels)
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void displayImage(String name, int[][] image) {
		JFrame frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((image.length+60), (image[0].length+60));
		frame.setVisible(true);
		frame.add(new mypanel(image));
	}

	private void printImage(int[][] image) {

	}
}
