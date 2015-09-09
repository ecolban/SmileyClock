package org.jointheleague.ecolban.affinetransformexercise;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ClockHand {

	private static final int WIDTH = 100;
	private static final int HEIGHT = 100;

	private static Stroke MOUTH_STROKE = new BasicStroke(5);

	private final int handLength;
	private final Stroke stroke;
	private final BufferedImage smiley = importSmiley("smiley");
	
	
	private void exportSmiley(String fileName) {
		File file = new File(fileName + ".png");
		System.out.println(file);
		try {
			BufferedImage bi = new BufferedImage(100, 100,
			        BufferedImage.TYPE_INT_ARGB);
			Graphics2D big = bi.createGraphics();
			big.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			        RenderingHints.VALUE_ANTIALIAS_ON);
			drawSmiley(big);
			ImageIO.write(bi, "png", file);
		} catch (IOException e) {

		}
	}

	private BufferedImage importSmiley(String fileName) {
		BufferedImage img = null;
		fileName = String.format("res/%s.png", fileName);
		try (InputStream in = this.getClass().getResourceAsStream(fileName)){
			img = ImageIO.read(in);
		} catch (IOException e) {
		}
		return img;
	}

	public static void main(String[] args) {
		ClockHand hand = new ClockHand(0, null);
		hand.exportSmiley("smiley");
	}

	public ClockHand(int handLength, Stroke stroke) {
		this.handLength = handLength;
		this.stroke = stroke;
	}

	protected void draw(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.setStroke(stroke);
		g2.drawLine(0, -15, 0, handLength + 25);
		double s = 0.25;
		g2.scale(s, s);
		g2.drawImage(smiley, -50, -50, null);
	}

	private void drawSmiley(Graphics2D g2) {
		g2.setColor(Color.YELLOW);
		g2.fillOval(0, 0, WIDTH, HEIGHT);
		g2.setColor(Color.BLACK);
		g2.fillOval(30, 20, 10, 25);
		g2.fillOval(60, 20, 10, 25);
		g2.setStroke(MOUTH_STROKE);
		int mouthLength = 70; // in degrees
		g2.drawArc(10, -20, WIDTH - 2 * 10, HEIGHT,
		        -90 - mouthLength / 2, mouthLength);
	}

	public int getHandLenght() {
		return handLength;
	}

}
