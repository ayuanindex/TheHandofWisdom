package com.ayuan.vo;

import java.util.Objects;

public class TrafficLight_vo {
	private Integer road;
	private Integer red;
	private Integer green;
	private Integer yellow;

	public TrafficLight_vo() {
	}

	public TrafficLight_vo(Integer red, Integer green, Integer yellow) {
		this.red = red;
		this.green = green;
		this.yellow = yellow;
	}

	public TrafficLight_vo(Integer red, Integer green, Integer yellow, Integer road) {
		this.road = road;
		this.red = red;
		this.green = green;
		this.yellow = yellow;
	}

	public Integer getRed() {
		return red;
	}

	public void setRed(Integer red) {
		this.red = red;
	}

	public Integer getGreen() {
		return green;
	}

	public void setGreen(Integer green) {
		this.green = green;
	}

	public Integer getYellow() {
		return yellow;
	}

	public void setYellow(Integer yellow) {
		this.yellow = yellow;
	}

	public Integer getRoad() {
		return road;
	}

	public void setRoad(Integer road) {
		this.road = road;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TrafficLight_vo that = (TrafficLight_vo) o;
		return Objects.equals(road, that.road) &&
				Objects.equals(red, that.red) &&
				Objects.equals(green, that.green) &&
				Objects.equals(yellow, that.yellow);
	}

	@Override
	public int hashCode() {
		return Objects.hash(road, red, green, yellow);
	}
}
