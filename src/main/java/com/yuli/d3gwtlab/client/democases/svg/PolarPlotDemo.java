//: com.yuli.d3gwtlab.client.democases.svg.PolarPlotDemo.java


package com.yuli.d3gwtlab.client.democases.svg;


import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtd3.api.Colors;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.svg.Symbol;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.yuli.d3gwtlab.client.IDemoCase;
import com.yuli.d3gwtlab.client.model.Vector;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;


public class PolarPlotDemo extends FlowPanel implements IDemoCase,
		ClickHandler, ValueChangeHandler<Boolean>, MouseOverHandler,
		MouseOutHandler {

	interface ICSSResources extends CssResource {

		String polarplotdemo();

		String line();
		String axis();
		String control();
		String vector1();
		String vector2();
	}
	interface IResourceBundle extends ClientBundle {


		IResourceBundle RESOURCE_BUNDLE = GWT.create(IResourceBundle.class);
		@Source("PolarPlotDemo.gss")
		ICSSResources css();

	}

	static final int RADIUS = Math.min(DEFAULT_WIDTH, DEFAULT_HEIGHT) / 2 - 30;

	static final String COLOR_RED = "#FF4400";
	static final String COLOR_BLUE = "#3333FF";

	static final String DESC_1 = "V-1";
	static final String DESC_2 = "V-2";

	private Vector[] vectors;
	private Selection defsG;

	private LinearScale radiusScale;

	private Selection polarGroup;

	private final ICSSResources css;

	private Button startButton;
	private Button stopButton;
	private Button updateButton;
	private CheckBox checkBox;

	private Selection vectorG;
	private final HTML vector1;
	private final HTML vector2;

	private FlowPanel svgPanel;

	private String[] colors;

	public PolarPlotDemo() {

		super();
		this.css = IResourceBundle.RESOURCE_BUNDLE.css();
		this.css.ensureInjected();

		this.startButton = new Button();
		this.startButton.setIcon(IconType.PLUS_SIGN);
		this.startButton.setText("Draw");
		this.startButton.setTabIndex(100);
		this.startButton.addClickHandler(this);

		this.stopButton = new Button();
		this.stopButton.setIcon(IconType.REMOVE);
		this.stopButton.setText("Clear");
		this.stopButton.setTabIndex(101);
		this.stopButton.addClickHandler(this);

		this.updateButton = new Button();
		this.updateButton.setIcon(IconType.ADJUST);
		this.updateButton.setText("Update");
		this.updateButton.setTabIndex(102);
		this.updateButton.addClickHandler(this);

		this.checkBox = new CheckBox("Labels");
		this.checkBox.setValue(false);
		this.checkBox.setEnabled(false);
		this.checkBox.setTabIndex(110);
		this.checkBox.addValueChangeHandler(this);

		FlowPanel controlPanel = new FlowPanel();
		controlPanel.add(this.startButton);
		controlPanel.add(this.stopButton);
		controlPanel.add(this.updateButton);
		controlPanel.add(this.checkBox);
		controlPanel.addStyleName(this.css.control());

		this.vector1 = new HTML(DESC_1);
		this.vector1.addStyleName(this.css.vector1());
		this.vector1.addMouseOverHandler(this);
		this.vector1.addMouseOutHandler(this);
		this.vector2 = new HTML(DESC_2);
		this.vector2.addStyleName(this.css.vector2());
		this.vector2.addMouseOverHandler(this);
		this.vector2.addMouseOutHandler(this);
		FlowPanel legendPanel = new FlowPanel();
		legendPanel.add(this.vector1);
		legendPanel.add(this.vector2);

		this.svgPanel = new FlowPanel();

		this.polarGroup = D3.select(this.svgPanel)
				.append("svg")
				.attr("width", DEFAULT_WIDTH)
				.attr("height", DEFAULT_HEIGHT)
				.style("border", "1px solid #A2A9AF")
				.append("g")
				.attr("id", "polarG")
				.attr("transform", "translate(" +
						DEFAULT_WIDTH / 2 + "," +
						DEFAULT_HEIGHT / 2 + ")");

		this.add(this.svgPanel);
		this.add(controlPanel);
		this.add(legendPanel);

		this.colors = new String[] {
				this.getColor(),
				this.getColor(),
		};

	}// End of PolarPlotDemo()

	private String getColor() {
		return Colors.rgb(Random.nextInt(255),
				Random.nextInt(255), Random.nextInt(255))
				.toHexaString();
	}

	private double getMagnitude() {
		double m = Random.nextDouble();
		if (m < 0.3) {
			m += 0.5;
		}
		return m;
	}

	private void drawPolarChart() {

		this.radiusScale = D3.scale.linear()
				.domain(0, 1.0)
				.range(0, RADIUS);

		Array<Double> tickValues = radiusScale.ticks(10)
				.filter((thisArg, element, index, array) -> index != 0 && index % 2 == 0)
				.map((thisArg, element, index, array) -> element.asDouble());

		double[] radiusArr = {
				radiusScale.apply(0.2).asDouble(),
				radiusScale.apply(0.4).asDouble(),
				radiusScale.apply(0.6).asDouble(),
				radiusScale.apply(0.8).asDouble(),
				radiusScale.apply(1.0).asDouble(),
		};

		Selection rAxisGroup = this.polarGroup.append("g")
				.selectAll("g")
				.data(tickValues)
				.enter()
				.append("g")
				.classed("r " + this.css.axis(), true);

		rAxisGroup.append("circle")
				.attr("r", (e, data, i) -> radiusArr[i]);

		rAxisGroup.append("text")
				.attr("y", (e, data, i) -> -(radiusArr[i] + 4))
				.attr("transform", "rotate(15)")
				.style("text-anchor", "middle")
				.text((e, data, i) -> data.asString());

		int[] angles = IntStream.rangeClosed(0, 330)
				.filter(n -> n % 30 == 0)
				.toArray();

		Selection aAxisGroup = this.polarGroup.append("g")
				.classed("a " + this.css.axis(), true)
				.selectAll("g")
				.data(angles)
				.enter()
				.append("g")
				.attr("transform", (e, data, i) ->
						"rotate(" + -data.asDouble() + ")");

		aAxisGroup.append("line")
				.attr("x2", RADIUS);

		aAxisGroup.append("text")
				.attr("x", RADIUS + 6)
				.attr("dy", "0.35em")
				.style("text-anchor", (e, d, i) ->
						(d.asDouble() < 270 && d.asDouble() > 90) ?
								"end" : null)
				.attr("transform", (e, d, i) ->
						(d.asDouble() < 270 && d.asDouble() > 90) ?
								"rotate(180 " + (RADIUS + 6) + ", 0)" : null)
				.text((e, d, i) -> d.asDouble() + "°");

		this.vectors = this.getVectors();

		Arrays.stream(this.vectors).forEach(v -> GWT.log(v.toString()));

		this.vectorG = this.polarGroup.append("g")
				.selectAll("g")
				.data(this.vectors)
				.enter()
				.append("g")
				.attr("id", (e, data, i) ->
						"vg-" + data.as(Vector.class).getDesc())
				.attr("marker-end", (e, data, i) ->
						"url(#arrow" + data.as(Vector.class).getDesc() +  ")")
//				.sort(Comparator.comparing(v -> v.as(Vector.class).getIndex()))
				.style("stroke", (e, data, i) ->
						data.as(Vector.class).getColor())
				.style("stroke-width", "2px");

		this.vectorG.transition()
				.duration(1000)
				.attr("transform", (e, data, i) -> "rotate(" +
						-data.as(Vector.class).getAngle() + ")");

		this.vectorG.append("line")
				.classed("needle", true)
				.attr("x2", (e, data, i) -> this.radiusScale.apply(
						data.as(Vector.class).getMagnitude()).asDouble() - 8);

		this.vectorG.append("text")
				.attr("x", (e, d, i) -> radiusScale.apply(
						d.as(Vector.class).getMagnitude()).asDouble() / 2)
				.attr("dy", "-0.5em")
				.style("text-anchor", (e, d, i) ->
						(d.as(Vector.class).getAngle() < 270 && d.as(Vector.class).getAngle() > 90) ?
								"end" : null)
				.attr("transform", (e, d, i) ->
						(d.as(Vector.class).getAngle() < 270 && d.as(Vector.class).getAngle() > 90) ?
								"rotate(180 " + (radiusScale.apply(
										d.as(Vector.class).getMagnitude()).asDouble() / 2) + ", 0)" : null)
				.style("stroke-width", "0px")
				.style("fill", "none")
				.text((e, d, i) -> d.as(Vector.class).getDesc());

		// Defines arrows
		defsG = this.polarGroup.append("defs");

		Selection markerG = defsG.append("g")
				.selectAll("g")
				.data(this.vectors)
				.enter()
				.append("marker")
				.attr("id", (e, data, i) -> "arrow" + data.as(Vector.class).getDesc())
				.attr("markerUnits", "strokeWidth")
				.attr("markerWidth", "12")
				.attr("markerHeight", "12")
				.attr("viewBox", "0 0 12 12")
				.attr("refX", "6")
				.attr("refY", "6")
				.attr("orient", "auto");

		markerG.append("path")
				.attr("d", "M2,2 L10,6 L2,10 L6,6 L2,2")
				.style("fill", (e, data, i) ->
						data.as(Vector.class).getColor());

		// Add the core
		Symbol symbol = D3.svg()
				.symbol()
				.type(Symbol.Type.CIRCLE)
				.size(25);

		this.polarGroup.append("g")
				.append("path")
				.attr("id", "core")
				.attr("d", symbol.generate(1.0))
				.style("fill", "#32393F");

	}// End of drawPolarChart()

	private int[] getAngles() {

		if (Random.nextInt() % 3 == 0) {
			int angle = Random.nextInt(360);
			return new int[] {angle, angle};
		} else {
			return new int[] {
					Random.nextInt(360),
					Random.nextInt(360),
			};
		}
	}

	private Vector[] getVectors() {

		this.colors = new String[] {
				COLOR_RED, COLOR_BLUE,
		};

		int[] angles = this.getAngles();

		return new Vector[] {
				new Vector(DESC_1, 0, this.getMagnitude(), angles[0],
						this.colors[0]),
				new Vector(DESC_2, 0, this.getMagnitude(), angles[1],
						this.colors[1]),
		};
	}

	@Override
	public void onClick(ClickEvent clickEvent) {
		Object source = clickEvent.getSource();
		if (source == this.startButton) {
			if (this.polarGroup.selectAll("g").size() <= 0) {
				this.checkBox.setEnabled(true);
				this.drawPolarChart();
			}
		} else if (source == this.stopButton) {
			this.stop();
		} else {
			this.update();
		}

	}// End of onClick(ClickEvent)

	@Override
	public void start() {
		this.drawPolarChart();
	}// End of start()

	@Override
	public void stop() {
		this.checkBox.setValue(false, true);
		this.checkBox.setEnabled(false);
		this.polarGroup.selectAll("g").remove();
		this.polarGroup.selectAll("defs").remove();

	}

	private void update() {

		this.checkBox.setValue(false);

		this.vectorG.select("text")
				.style("stroke-width", "0px");

		if (this.polarGroup.select("g").size() == 0) {
			return;
		}

		this.vectors = this.getVectors();

		Arrays.stream(this.vectors).forEach(v -> GWT.log(v.toString()));

		this.vectorG.data(this.vectors)
				.transition()
				.duration(700)
				.attr("transform", (e, data, i) -> "rotate(" +
						-data.as(Vector.class).getAngle() + ")")
				.select("line")
				.attr("x2", (e, data, i) -> this.radiusScale.apply(
						data.as(Vector.class).getMagnitude()).asDouble() - 8);

		this.vectorG.select("text")
				.attr("x", (e, d, i) -> this.radiusScale.apply(
						d.as(Vector.class).getMagnitude()).asDouble() / 2)
				.style("text-anchor", (e, d, i) ->
						(d.as(Vector.class).getAngle() < 270 &&
								d.as(Vector.class).getAngle() > 90) ?
								"end" : null)
				.attr("transform", (e, d, i) ->
						(d.as(Vector.class).getAngle() < 270 &&
								d.as(Vector.class).getAngle() > 90) ?
								"rotate(180 " + (this.radiusScale.apply(
										d.as(Vector.class).getMagnitude())
										.asDouble() / 2) + ", 0)" : null);

	}// End of update()

	@Override
	public void onValueChange(ValueChangeEvent<Boolean> event) {
		this.setLabelVisible(event.getValue());
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		String desc = event.getSource() == this.vector1 ? DESC_1 : DESC_2;
		this.sortVectors(desc);
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		this.vectorG.select("text")
				.transition()
				.duration(300)
				.style("stroke-width", "0px");
	}

	private void sortVectors(String desc) {

		if (this.polarGroup.select("g").size() == 0) {
			return;
		}

		Arrays.stream(this.vectors).forEach(v -> {
			if (v.getDesc().equals(desc)) {
				v.setIndex(10);
			} else {
				v.setIndex(0);
			}
		});

		this.vectorG.sort(Comparator.comparing(
				v -> v.as(Vector.class).getIndex()));

		this.vectorG.select("text")
				.transition()
				.duration(300)
				.style("stroke-width", (e, d, i) ->
						(d.as(Vector.class).getDesc().equals(desc)) ?
								"1px" : "0px");
	}

	private void setLabelVisible(boolean visible) {
		String strokeWidth = visible ? "1px" : "0px";
		this.vectorG.select("text")
				.transition()
				.duration(300)
				.style("stroke-width", strokeWidth);
	}

}///:~