package eldunari.form.design;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import eldunari.form.components.Button;

public class HoloButton implements MouseListener{
	
	private Button button;
	private Border border;
	
	public HoloButton(Button button){
		this.button = button;
		this.button.setBackground(Color.WHITE);
		this.button.setFont(new Font(Font.MONOSPACED,Font.ITALIC,16));
		this.button.setBorder(BorderFactory.createEmptyBorder());
		this.button.addMouseListener(this);
		this.border = this.button.getBorder();
	}
	
	public Button getButton(){
		return button;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.button.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.button.setBorder(border);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.button.setBorder(border);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.button.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
	}

}
