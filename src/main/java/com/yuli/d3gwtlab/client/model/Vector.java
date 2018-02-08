//: com.yuli.d3gwtlab.client.model.Vector.java


package com.yuli.d3gwtlab.client.model;


import java.util.Objects;


public class Vector {

	private String desc;
	private double magnitude;
	private int angle;
	private String color;
	private int index;

	public Vector(String desc, int index, double magnitude, int angle,
	              String color) {
		this.desc = desc;
		this.index = index;
		this.magnitude = magnitude;
		this.angle = angle;
		this.color = color;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {

		StringBuffer toStrBuilder = new StringBuffer();
		toStrBuilder.append(this.desc)
				.append(": ")
				.append(this.index)
				.append(" - ")
				.append(this.magnitude)
				.append("/")
				.append(this.angle)
				.append(" - ")
				.append(this.color);

		return toStrBuilder.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vector vector = (Vector) o;
		return Double.compare(vector.magnitude, magnitude) == 0 &&
				angle == vector.angle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(magnitude, angle);
	}

}///:~