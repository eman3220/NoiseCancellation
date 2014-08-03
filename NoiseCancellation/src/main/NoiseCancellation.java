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
			while (bi == null) {
				bi = ImageIO.read(new File(imagepath));
			}

			// create 2d array images
			img = new int[bi.getWidth()][bi.getHeight()];
			output = new int[bi.getWidth()][bi.getHeight()];

			// read in image pixels
			for (int h = 0; h < bi.getHeight(); h++) {
				for (int w = 0; w < bi.getWidth(); w++) {

					int rgb = bi.getRGB(w, h);
					int r = (rgb >> 16) & 0xFF;
					int g = (rgb >> 8) & 0xFF;
					int b = (rgb & 0xFF);
					int gray = (r + g + b) / 3;

					img[bi.getWidth() - 1 - w][h] = gray * 2;
				}
			}

			displayImage("original", img);

			// create convolution masks
			double[][] smoothingMask = { { 0.11, 0.11, 0.11 },
					{ 0.11, 0.11, 0.11 }, { 0.11, 0.11, 0.11 } };

			double[][] medianFilterMask = { { 5, 5, 6 }, { 3, 4, 7 }, { 8, 2, 2 } };

			// apply to original image
			
			// for each pixel (using 0 for the outside pixels)
			for (int hei = 0; hei < img[0].length; hei++) {
				for (int wid = 0; wid < img.length; wid++) {

					applyMask(img, output, medianFilterMask, wid, hei);

				}
			}
			
			if(maxValue>(255*3)){
				fixValues(output);
			}
			
			displayImage("new image", output);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Incase the result of the vector inner product is out of 
	 * range (0-255)
	 * @param output
	 */
	private void fixValues(int[][] output) {
		for (int y = 0; y < output[0].length; y++) {
			for (int x = 0; x < output.length; x++) {
				double temp = (double)output[x][y] / (double)maxValue;
				double tempo = temp * 255;
				output[x][y] = (int)tempo;
			}
		}
	}

	private int maxValue = 0;

	/**
	 * Applies the given mask onto the given point. If mask is on boundary,
	 * points beyond image will be zero.
	 * 
	 * @param img
	 * @param mask
	 * @param wid
	 * @param hei
	 */
	private void applyMask(int[][] img, int[][] output, double[][] mask, int wid, int hei) {
		int left = wid - 1;
		int right = wid + 1;
		int up = hei - 1;
		int down = hei + 1;

		// add all the squares together and the result is what the middle square
		// will be.

		int result = 0;
		
		for (int y = 0; y < mask[0].length; y++) { // height
			for (int x = 0; x < mask.length; x++) { // width
				//System.out.print(mask[x][y] + " ");
				
				try{
				int imgWid = wid-1+x;
								
				int imgHei = hei-1+y;
				
				result += img[imgWid][imgHei] * mask[x][y];
				}catch(ArrayIndexOutOfBoundsException e){
					continue;
				}
			}
			//System.out.println();
		}
		//System.out.println(img[wid][hei]);
		//System.out.println(result);
		
		if(result>maxValue){
			maxValue = result;
		}
		
		output[wid][hei] = result;
	}

	private void displayImage(String name, int[][] image) {
		JFrame frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((image.length + 60), (image[0].length + 60));
		frame.setVisible(true);
		frame.add(new mypanel(image));
	}

	private void printImage(int[][] image) {

	}
}
