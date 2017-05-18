package se.com.config;

import java.awt.Color;

public class GlobalConfig {

	private boolean showComponentBounds = false;
	private String componentsConfigFolder = "./components";
	private String componentsConfigExtension = ".pcbcomponent";
	private Color trackColor = Color.GREEN;
	private Color boardColor = Color.RED;
	private Color workableAreaColor = Color.YELLOW;
	private int componentsGlobalBoundsSizeBonus = 2;
	private int componentShapeWidth = 1;
	private int trackWidth = 2;
	private int padWidth = 2;
	private int highlightBoxWidth = 1;
	
	public int getComponentShapeWidth() {
		return componentShapeWidth;
	}

	public void setComponentShapeWidth(int componentShapeWidth) {
		this.componentShapeWidth = componentShapeWidth;
	}

	public int getTrackWidth() {
		return trackWidth;
	}

	public void setTrackWidth(int trackWidth) {
		this.trackWidth = trackWidth;
	}

	public int getPadWidth() {
		return padWidth;
	}

	public void setPadWidth(int padWidth) {
		this.padWidth = padWidth;
	}

	public int getHighlightBoxWidth() {
		return highlightBoxWidth;
	}

	public void setHighlightBoxWidth(int highlightBoxWidth) {
		this.highlightBoxWidth = highlightBoxWidth;
	}

	public void setTrackColor(Color trackColor) {
		this.trackColor = trackColor;
	}

	private static GlobalConfig instance = new GlobalConfig();
	
	private GlobalConfig() {
	}
	
	public static GlobalConfig getInstance() {
		return instance;
	}

	public boolean isShowComponentBounds() {
		return showComponentBounds;
	}

	public void setShowComponentBounds(boolean showComponentBounds) {
		this.showComponentBounds = showComponentBounds;
	}

	public String getComponentsConfigFolder() {
		return componentsConfigFolder;
	}

	public void setComponentsConfigFolder(String componentsConfigFolder) {
		this.componentsConfigFolder = componentsConfigFolder;
	}

	public String getComponentsConfigExtension() {
		return componentsConfigExtension;
	}

	public void setComponentsConfigExtension(String componentsConfigExtension) {
		this.componentsConfigExtension = componentsConfigExtension;
	}

	public Color getTrackColor() {
		return trackColor;
	}

	public int getComponentsGlobalBoundsSizeBonus() {
		return componentsGlobalBoundsSizeBonus;
	}

	public void setComponentsGlobalBoundsSizeBonus(int minimalComponentsGlobalBoundsSizeBonus) {
		this.componentsGlobalBoundsSizeBonus = minimalComponentsGlobalBoundsSizeBonus;
	}

	public Color getBoardColor() {
		return boardColor;
	}

	public Color getWorkableAreaColor() {
		return workableAreaColor;
	}

	public void setWorkableAreaColor(Color workableAreaColor) {
		this.workableAreaColor = workableAreaColor;
	}

	public void setBoardColor(Color boardColor) {
		this.boardColor = boardColor;
	}
	
}
