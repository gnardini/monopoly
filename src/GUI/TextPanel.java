package GUI;

import java.awt.Graphics;

import javax.swing.JPanel;

public class TextPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private String[] str;
	
	public TextPanel(String[] str) {
		this.str=str;
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(g.getFont().deriveFont(16f));
		for(int i=0 ; i<str.length ; i++)
			g.drawString(str[i], 20, (1+i)*30);
	}
}
