package org.jointheleague.ecolban.affinetransformexercise;

import static java.lang.Math.PI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.time.LocalTime;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class ClockFace extends JPanel implements Runnable {

	static final int INCH = 72; // in points
	private static final Stroke THIN_STROKE = new BasicStroke(1.0F);
	private static final Stroke FAT_STROKE = new BasicStroke(5.0F);
	private static final Dimension US_LETTER = new Dimension((int) (8.5 * INCH), 11 * INCH);
	private Smiley smiley = new Smiley();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ClockFace());
	}

	private BufferedImage background;

	@Override
	public void run() {
		JFrame frame = new JFrame("Smiley Clock");
		this.setPreferredSize(US_LETTER);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		background = buildBackground();
		frame.setVisible(true);
		new Timer(40, e -> repaint()).start();
	}

	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(background, 0, 0, null);
		LocalTime now = LocalTime.now();
		int hour = now.getHour();
		int min = now.getMinute();
		int sec = now.getSecond();
		int nano = now.getNano();
		g2.translate(getWidth() / 2, getHeight() / 2);
		AffineTransform center = g2.getTransform();
		drawSecondHand(g2, sec, nano);
		g2.setTransform(center);
		drawMinuteHand(g2, min, sec);
		g2.setTransform(center);
		drawHourHand(g2, hour, min);
		g2.setTransform(center);
		g2.setColor(Color.RED);
		g2.fillOval(-3, -3, 6, 6);
	}

	private void drawHourHand(Graphics2D g2, int hours, int minutes) {
		g2.rotate(PI / 6.0 * (hours + minutes / 60.0));
		g2.setColor(Color.BLACK);
		g2.setStroke(FAT_STROKE);
		g2.drawLine(0, ClockFace.INCH / 8, 0, (int) (-1.5 * INCH));
		g2.translate(-INCH / 2, -1.25 * INCH);
		smiley.drawSelf(g2);
	}

	private void drawMinuteHand(Graphics2D g2, int minutes, int seconds) {
		g2.rotate(PI / 30.0 * (minutes + seconds / 60.0));
		g2.setColor(Color.BLACK);
		g2.setStroke(FAT_STROKE);
		g2.drawLine(0, INCH / 8, 0, (int) (-2.0 * INCH));
		g2.translate(-INCH / 4, -1.75 * INCH);
		g2.scale(0.5, 0.5);
		smiley.drawSelf(g2);
	}

	private void drawSecondHand(Graphics2D g2, int seconds, int nanos) {
		g2.rotate(PI / 30.0 * (seconds + nanos * 1e-9));
		g2.setColor(Color.RED);
		g2.setStroke(THIN_STROKE);
		g2.drawLine(0, INCH / 8, 0, (int) (-2.5 * INCH));
		g2.translate(-INCH / 4, -2.25 * INCH);
		g2.scale(0.5, 0.5);
		smiley.drawSelf(g2);
	}

	private BufferedImage buildBackground() {
		BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, img.getWidth(), img.getHeight());
		g2.translate(img.getWidth() / 2, img.getHeight() / 2);
		g2.setColor(Color.BLACK);
		AffineTransform center = g2.getTransform();
		for (int i = 0; i < 60; i++) {
			g2.rotate(i * PI / 30.0);
			if (i % 5 == 0) {
				g2.setStroke(FAT_STROKE);
				g2.drawLine((int) (2.25 * INCH), 0, (int) (2.75 * INCH), 0);
				g2.setStroke(THIN_STROKE);
			} else {
				g2.drawLine((int) (2.5 * INCH), 0, (int) (2.75 * INCH), 0);
			}
			g2.setTransform(center);
		}

		return img;
	}

}
