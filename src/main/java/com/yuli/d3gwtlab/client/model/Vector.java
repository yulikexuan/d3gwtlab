//: com.yuli.d3gwtlab.client.model.Vector.java


package com.yuli.d3gwtlab.client.model;


import java.util.Objects;


public class Vector {

	private double magnitude;
	private int angle;
	private String color;

	public Vector(double magnitude, int angle, String color) {
		this.magnitude = magnitude;
		this.angle = angle;
		this.color = color;
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

	@Override
	public String toString() {
		return "Vector{" +
				"magnitude=" + magnitude +
				", angle=" + angle +
				'}';
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