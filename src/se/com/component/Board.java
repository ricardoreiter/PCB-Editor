package se.com.component;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import se.com.frame.render.Drawable;

public class Board implements Drawable {

	private List<BoardComponent> components = new LinkedList<>();

	public void addComponent(BoardComponent component) {
		components.add(component);
	}
	
	@Override
	public void paint(Graphics2D g) {
		for (BoardComponent comp : components) {
			comp.paint(g);
		}
	}
	
	
}
