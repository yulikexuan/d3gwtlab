//: com.yuli.d3gwtlab.client.democases.DemoContainer.java


package com.yuli.d3gwtlab.client.democases;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.yuli.d3gwtlab.client.democases.svg.PolarPlotDemo;
import com.yuli.d3gwtlab.client.democases.svg.SymbolDemo;

public class DemoContainer extends Composite {

	interface DemoContainerUiBinder extends UiBinder<HTMLPanel, DemoContainer> {
	}

	private static DemoContainerUiBinder ourUiBinder =
			GWT.create(DemoContainerUiBinder.class);

	@UiField
	SimpleLayoutPanel symbolsPanel;

	@UiField
	SimpleLayoutPanel polarChartPanel;

	public DemoContainer() {
		SymbolDemo symbolDemo = new SymbolDemo();
		symbolDemo.start();

		PolarPlotDemo ppd = new PolarPlotDemo();
//		ppd.start();

		this.initWidget(ourUiBinder.createAndBindUi(this));

		this.symbolsPanel.add(symbolDemo);
		this.polarChartPanel.add(ppd);
	}
}