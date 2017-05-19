package se.com.frame.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import se.com.component.ComponentConfig;

@SuppressWarnings("serial")
public class ComponentListModel extends AbstractListModel<ComponentConfig> {

	List<ComponentConfig> values = new ArrayList<>();
	
	public ComponentListModel(List<ComponentConfig> values) {
		this.values.addAll(values);
	}
	
	@Override
	public int getSize() {
		return values.size();
	}

	@Override
	public ComponentConfig getElementAt(int index) {
		return values.get(index);
	}

}
