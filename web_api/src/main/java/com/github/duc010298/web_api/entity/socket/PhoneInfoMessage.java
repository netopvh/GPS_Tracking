package com.github.duc010298.web_api.entity.socket;

public class PhoneInfoMessage {
	private String networkName;
	private String networkType;
	private int batteryLevel;
	private boolean isCharging;
	
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public int getBatteryLevel() {
		return batteryLevel;
	}
	public void setBatteryLevel(int batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
	public boolean isCharging() {
		return isCharging;
	}
	public void setCharging(boolean isCharging) {
		this.isCharging = isCharging;
	}

	
}
