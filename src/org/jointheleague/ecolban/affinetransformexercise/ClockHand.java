package org.jointheleague.ecolban.affinetransformexercise;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class ClockHand {
    
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    
    private static Stroke MOUTH_STROKE = new BasicStroke(5);
    
    private final int handLength;
    private final Stroke stroke;
    
    public ClockHand(int handLength, Stroke stroke) {
        this.handLength = handLength;
        this.stroke = stroke;
    }

    
    protected void draw (Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.setStroke(stroke);
        g2.drawLine(0, -15, 0, handLength + 5);
        double s = 0.25;
        g2.scale(s , s);
        g2.setColor(Color.YELLOW);
        g2.fillOval(-WIDTH / 2, -HEIGHT / 2, WIDTH, HEIGHT);
        g2.setColor(Color.BLACK);
        g2.fillOval(-20, -30, 10, 25);
        g2.fillOval(10, -30, 10, 25);
        g2.setStroke(MOUTH_STROKE);
        g2.drawArc(-40, -60, 80, 90, 235, 70);
    }


    public int getHandLenght() {
        return handLength;
    }
    

}
