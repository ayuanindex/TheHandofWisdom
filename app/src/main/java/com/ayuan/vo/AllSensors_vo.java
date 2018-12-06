package com.ayuan.vo;

/***
 * 所有传感器的vo
 */
public class AllSensors_vo {
	private Integer pm2_5;
	private Integer co2;
	private Integer LightIntensity;
	private Integer humidity;
	private Integer temperature;

	public AllSensors_vo() {
	}

	public AllSensors_vo(Integer pm2_5, Integer co2, Integer lightIntensity, Integer humidity, Integer temperature) {
		this.pm2_5 = pm2_5;
		this.co2 = co2;
		LightIntensity = lightIntensity;
		this.humidity = humidity;
		this.temperature = temperature;
	}

	public Integer getPm2_5() {
		return pm2_5;
	}

	public void setPm2_5(Integer pm2_5) {
		this.pm2_5 = pm2_5;
	}

	public Integer getCo2() {
		return co2;
	}

	public void setCo2(Integer co2) {
		this.co2 = co2;
	}

	public Integer getLightIntensity() {
		return LightIntensity;
	}

	public void setLightIntensity(Integer lightIntensity) {
		LightIntensity = lightIntensity;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "AllSensors_vo{" +
				"pm2_5=" + pm2_5 +
				", co2=" + co2 +
				", LightIntensity=" + LightIntensity +
				", humidity=" + humidity +
				", temperature=" + temperature +
				'}';
	}
}
