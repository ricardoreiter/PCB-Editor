package se.com.frame.model;

import javax.swing.table.AbstractTableModel;

import se.com.component.BoardComponent;

@SuppressWarnings("serial")
public class SelectedComponentTableModel extends AbstractTableModel {

	private String[] columnNames = {"Property", "Value"};
    private Object[][] data;

    public SelectedComponentTableModel() {
    	setComponent(null);
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

	public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
    public void setComponent(BoardComponent component) {
    	if (component == null) {
    		data = new Object[][]{
	    		{"type", ""}, 
	    		{"pos", ""}, 
	    		{"rotation", ""}};
    	} else {
	    	data = new Object[][]{
	    		{"type", component.getConfig().getName()}, 
	    		{"pos", component.getPos().x + ", " + component.getPos().y}, 
	    		{"rotation", component.getRotation()}};
    	}
    	fireTableDataChanged();
    }
    
}
