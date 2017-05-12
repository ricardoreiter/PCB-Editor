package se.com.config;

public class GlobalConfig {

	private boolean showComponentBounds = false;
	private String componentsConfigFolder = "./components";
	private String componentsConfigExtension = ".pcbcomponent";
	
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
	
}
