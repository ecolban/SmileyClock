package org.jointheleague.ecolban.affinetransformexercise;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.time.LocalTime;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import static java.lang.Math.*;

@SuppressWarnings("serial")
public class ClockFace extends JPanel implements Runnable, ActionListener {

    private static final Stroke THIN_STROKE = new BasicStroke(1.0F);
    private static final Stroke FAT_STROKE  = new BasicStroke(5.0F);

    private ClockHand           hour        = new ClockHand(100, FAT_STROKE);
    private ClockHand           minute      = new ClockHand(130, FAT_STROKE);
    private ClockHand           second      = new ClockHand(130, THIN_STROKE);
    private Timer               ticker;
    private Image               background;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClockFace());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Smiley Face");
        this.setPreferredSize(new Dimension(500, 500));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        background = buildBackground();
        frame.setVisible(true);
        ticker = new Timer(40, this);
        ticker.start();
    }

    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(background, 0, 0, null);
        LocalTime now = LocalTime.now();
        int h = now.getHour();
        int m = now.getMinute();
        int s = now.getSecond();
        int n = now.getNano();
        g2.translate(getWidth() / 2, getHeight() / 2);
        AffineTransform cachedTransform = g2.getTransform();
        g2.rotate(PI * (h / 6.0 + m / 360.0));
        g2.translate(0.0, -hour.getHandLenght());
        hour.draw(g2);
        g2.setTransform(cachedTransform);
        g2.rotate(PI * (m / 30.0 + s / 1800.0));
        g2.translate(0.0, -minute.getHandLenght());
        minute.draw(g2);
        g2.setTransform(cachedTransform);
        g2.rotate(PI * (s / 30.0 + n / 3.0e10));
        g2.translate(0.0, -second.getHandLenght());
        second.draw(g2);
        g2.setTransform(cachedTransform);
        g2.setColor(Color.RED);
        g2.fillOval(-3, -3, 6, 6);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private BufferedImage buildBackground() {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D imgGraphics = img.createGraphics();
        imgGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
        imgGraphics.setColor(Color.WHITE);
        imgGraphics.fillRect(0, 0, img.getWidth(), img.getHeight());
        imgGraphics.translate(img.getWidth() / 2, img.getHeight() / 2);
        imgGraphics.setColor(Color.BLACK);
        AffineTransform cachedTransform = imgGraphics.getTransform();
        for (int i = 0; i < 60; i++) {
            imgGraphics.rotate(i * PI / 30.0);
            if (i % 5 == 0) {
                imgGraphics.setStroke(FAT_STROKE);
                imgGraphics.drawLine(170, 0, 180, 0);
            } else {
                imgGraphics.setStroke(THIN_STROKE);
                imgGraphics.drawLine(175, 0, 180, 0);
            }
            imgGraphics.setTransform(cachedTransform);
        }

        return img;
    }

}
