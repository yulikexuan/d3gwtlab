//: com.yuli.d3gwtlab.client.democases.svg.SymbolDemo.java


package com.yuli.d3gwtlab.client.democases.svg;


import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtd3.api.Colors;
import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transform;
import com.github.gwtd3.api.svg.Symbol;
import com.github.gwtbootstrap.client.ui.Button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.FlowPanel;

import com.yuli.d3gwtlab.client.IDemoCase;

import java.util.Stack;


public class SymbolDemo extends FlowPanel implements IDemoCase, ClickHandler {

	interface IMyResources extends CssResource {
		String symboldemo();
	}

	interface Bundle extends ClientBundle {
		Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("SymbolDemo.css")
		IMyResources css();
	}

	private static final Stack<Coords> POINTS = new Stack<Coords>();

	private Selection svg;
	private Symbol symbol;

	private final IMyResources css;

	public SymbolDemo() {
		super();
		this.css = Bundle.INSTANCE.css();
		this.css.ensureInjected();
	}

	@Override
	public void start() {

		this.symbol = D3.svg().symbol();

		Button button = new Button();
		button.setIcon(IconType.PLUS_SIGN);
		button.setText("Add Symbol");

		button.addClickHandler(this);

		this.add(button);

		this.svg = D3.select(this)
				.append("svg")
				.attr("width", DEFAULT_WIDTH)
				.attr("height", DEFAULT_HEIGHT)
				.append("g");

	}// End of start()

	@Override
	public void stop() {
		POINTS.clear();
	}

	@Override
	public void onClick(ClickEvent clickEvent) {

		Symbol.Type[] symbols = Symbol.Type.values();
		int count = symbols.length;
		this.symbol.type(symbols[Random.nextInt(count)]);
		this.symbol.size(Random.nextInt(2500) + 25);

		this.svg.append("path").classed(this.css.symboldemo(), true)
				.attr("transform", Transform.parse("")
						.translate(Random.nextInt(DEFAULT_WIDTH),
								Random.nextInt(DEFAULT_HEIGHT)).toString())
				.attr("d", this.symbol.generate(1.0))
				.style("fill", Colors.rgb(Random.nextInt(255),
						Random.nextInt(255), Random.nextInt(255))
						.toHexaString());

	}// End of onClick(ClickEvent)

}///:~