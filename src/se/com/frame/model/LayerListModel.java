package se.com.frame.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import se.com.component.Board;

public class LayerListModel extends AbstractListModel<Integer> implements ComboBoxModel<Integer> {

	private static final long serialVersionUID = -8377500052242944022L;
	List<Integer> values = new ArrayList<>();
	Integer selected;
	
	public LayerListModel(Board board) {
		for (int i = 0; i < board.getLayers(); i++) {
			values.add(i);
		}
		selected = values.get(0);
	}

	@Override
	public int getSize() {
		return values.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return values.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selected = (Integer) anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selected;
	}

}
