package Test;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

public class GraphingData extends JPanel {
    /**
	 * 
	 */
	private static int Y_HATCH_CNT = 10;
	 private static final int GRAPH_POINT_WIDTH = 12;
	private static final long serialVersionUID = 1L;
	float[] data={(float) 45.7276229858,(float) 45.2766990662,(float) 44.6741676331,(float) 44.701789856,(float) 45.1425971985,(float) 44.2174263};
	//int[] data = {1,2,3,4,5,6,7,8,10,1,2,3,4,5,6,7,5,4,3,4,5,65,6,324,23,423,64,56, 74,42,23,34};
    final int PAD = 20;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        int max=(((int) getMax()/10)+1);
        Y_HATCH_CNT=max;
        for (int i = 0; i < Y_HATCH_CNT; i++) {
            int x0 = PAD;
            int x1 = GRAPH_POINT_WIDTH + PAD;
            int y0 = getHeight() - (((i + 1) * (getHeight() - PAD * 2)) / Y_HATCH_CNT + PAD);
            int y1 = y0;
            g2.drawLine(x0, y0, x1, y1);
            g2.drawString(String.valueOf(i*10), x0-10, y0);
         }
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // Ordinate label.
        String s = "Strategy Counter";
        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // Abcissa label.
        s = "Rounds";
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        // Draw lines.
        double xInc = (double)(w - 2*PAD)/(data.length-1);
        double scale = (double)(h - 2*PAD)/getMax();
        g2.setPaint(Color.green.darker());
        for(int i = 0; i < data.length-1; i++) {
            double x1 = PAD + i*xInc;
            double y1 = h - PAD - scale*data[i];
            double x2 = PAD + (i+1)*xInc;
            double y2 = h - PAD - scale*data[i+1];
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        // Mark data points.

    }

    private float getMax() {
        float max = - Float.MAX_VALUE;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > max)
                max = data[i];
        }
        return max;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new GraphingData());
        f.setSize(800,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}