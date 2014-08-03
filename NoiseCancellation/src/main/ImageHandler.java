package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author Emmanuel Godinez
 * @created 03/08/2014
 * 
 *          This class was created for the purposes of Project 1 for COMP422 at
 *          Victoria University of Wellington.
 *
 */
public class ImageHandler {

	/**
	 * Given a filepath for an image, converts the image into a 2d Array
	 * 
	 * @param imagepath
	 * @return
	 */
	public static int[][] readImage(String imagepath) {
		int[][] output;

		// Store image as BufferedImage
		BufferedImage bi = null;
		int killCount = 0;
		int killLimit = 100000000; // some big number
		while (bi == null) {
			if (killCount >= killLimit) {
				JOptionPane.showMessageDialog(null,
						"Could not read image. Please make sure it is a png");
				System.exit(0);
			}
			try {
				bi = ImageIO.read(new File(imagepath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			killCount++;
		}

		// Read bufferedImage into 2d Array grayscale
		output = new int[bi.getWidth()][bi.getHeight()];

		for (int h = 0; h < bi.getHeight(); h++) {
			for (int w = 0; w < bi.getWidth(); w++) {
				int rgb = bi.getRGB(w, h);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);
				int gray = (r + g + b) / 3;

				output[w][h] = gray * 2;
			}
		}

		return output;
	}

	/**
	 * Prints 2d image array to screen
	 * 
	 * @param name
	 * @param image
	 */
	public static void displayImage(String name, int[][] image) {
		JFrame frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((image.length + 60), (image[0].length + 60));
		frame.setVisible(true);
		frame.add(new mypanel(image));
	}

	/**
	 * Given an image and convolution mask, performs the convolution process and
	 * returns the new image. Checks that the pixel values are within the range.
	 * 
	 * @param image
	 * @param mask
	 * @return
	 */
	public static int[][] applyConvolutionMask(int[][] image, double[][] mask) {
		int[][] out = new int[image.length][image[0].length];

		int maxValue = Integer.MIN_VALUE; // some small number
		int minValue = Integer.MAX_VALUE; // some big number

		// loop through image
		for (int y = 0; y < image[0].length; y++) {
			for (int x = 0; x < image.length; x++) {

				// find the sum of the filter
				int sum = 0;

				// loop through mask
				for (int j = 0; j < mask[0].length; j++) {
					for (int i = 0; i < mask.length; i++) {
						try {
							int imgWid = x - 1 + i;
							int imgHei = y - 1 + j;
							sum += image[imgWid][imgHei] * mask[i][j];
						} catch (ArrayIndexOutOfBoundsException e) {
							continue;
						}
					}
				}
				if (sum > maxValue) {
					maxValue = sum;
				}
				if (sum < minValue) {
					minValue = sum;
				}

				out[x][y] = sum;
			}
		}

		System.out.println(maxValue);
		System.out.println(minValue);

		// before returning image, make sure values are within bounds 0-255
		if (maxValue > (255 * 3) || minValue < 0) {

			int range = maxValue - minValue; // denominator

			// go through image and adjust the pixel values
			for (int y = 0; y < out[0].length; y++) {
				for (int x = 0; x < out.length; x++) {
					double temporary1 = (double) Math.abs(out[x][y])
							/ (double) range;
					double temporary2 = temporary1 * 255;
					out[x][y] = (int) temporary2;
				}

			}
		}

		return out;
	}

	/**
	 * Given an image, increases the brightness of the image.
	 * @param image
	 * @return
	 */
	public static int[][] brightenImage(int[][] image) {
		int[][] temp = new int[image.length][image[0].length];
		for (int y = 0; y < image[0].length; y++) {
			for (int x = 0; x < image.length; x++) {
				temp[x][y] = (int) (image[x][y] * 2.8);
			}
		}

		return temp;
	}
}
