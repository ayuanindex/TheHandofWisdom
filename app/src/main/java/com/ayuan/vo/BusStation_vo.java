package com.ayuan.vo;

public class BusStation_vo {
	private Integer busId;
	private Integer distance;

	public BusStation_vo() {
	}

	public BusStation_vo(Integer busId, Integer distance) {
		this.busId = busId;
		this.distance = distance;
	}

	public Integer getBusId() {
		return busId;
	}

	public void setBusId(Integer busId) {
		this.busId = busId;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "BusStation_vo{" +
				"busId=" + busId +
				", distance=" + distance +
				'}';
	}
}
