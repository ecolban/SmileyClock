package org.jointheleague.ecolban.affinetransformexercise;

import static org.jointheleague.ecolban.affinetransformexercise.ClockFace.INCH;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Smiley {
	


	private static final int WIDTH = INCH;
	private static final int HEIGHT = INCH;

	private static Stroke MOUTH_STROKE = new BasicStroke(5);

	public void drawSelf(Graphics2D g2) {
		g2.setColor(Color.YELLOW);
		g2.fillOval(0, 0, WIDTH, HEIGHT);
		g2.setColor(Color.BLACK);
		int eigth = INCH / 8;
		g2.fillOval(2 * eigth, 2 * eigth, eigth, 2 * eigth);
		g2.fillOval(5 * eigth, 2 * eigth, eigth, 2 * eigth);
		g2.setStroke(MOUTH_STROKE);
		int mouthLength = 70; // in degrees
		g2.drawArc(10, -20, WIDTH - 2 * 10, HEIGHT,
		        -90 - mouthLength / 2, mouthLength);
	}
}
