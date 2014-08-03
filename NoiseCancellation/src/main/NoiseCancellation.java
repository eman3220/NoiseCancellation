package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author Emmanuel
 * 
 */
public class NoiseCancellation {

	public static void main(String[] args) {
		if (args.length != 1) {
			JOptionPane.showMessageDialog(null, "One argument please");
			return;
		}
		if (!args[0].endsWith(".png")) {
			JOptionPane.showMessageDialog(null, "PNG file please");
			return;
		}

		new NoiseCancellation(args[0]);
	}

	public NoiseCancellation(String imagepath) {
		int[][] img = ImageHandler.readImage(imagepath);
		ImageHandler.displayImage("original", img);

		// create convolution masks

		// Mean Filter
		double[][] equalMeanFilterMask = { { 0.11, 0.11, 0.11 },
				{ 0.11, 0.11, 0.11 }, { 0.11, 0.11, 0.11 } };

		double[][] unequalMeanFilterMask = { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };

		// Median Filter
		double[][] medianFilterMask = { { 5, 5, 6 }, { 3, 4, 7 }, { 8, 2, 2 } };
		int median = 0;
		// find the median
		

		int[][] equalMean = ImageHandler.applyConvolutionMask(img,
				equalMeanFilterMask);
		ImageHandler.brightenImage(equalMean);
		ImageHandler.displayImage("Equal Mean Filter", equalMean);

		int[][] unequalMean = ImageHandler.applyConvolutionMask(img,
				unequalMeanFilterMask);
		ImageHandler.brightenImage(unequalMean);
		ImageHandler.displayImage("Unequal Mean Filter", unequalMean);

		int[][] median1 = ImageHandler.applyConvolutionMask(img,
				medianFilterMask);
		ImageHandler.brightenImage(median1);
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageHandler.displayImage("Median Filter", median1);
	}
}
