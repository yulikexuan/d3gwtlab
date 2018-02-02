//: com.yuli.d3gwtlab.client.democases.svg.SymbolDemo.java


package com.yuli.d3gwtlab.client.democases.svg;


import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtd3.api.Colors;
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


public class SymbolDemo extends FlowPanel implements IDemoCase, ClickHandler {

	private Button startButton;
	private Button stopButton;

	interface ICSSResources extends CssResource {
		String symboldemo();
	}

	interface IResourceBundle extends ClientBundle {

		IResourceBundle RESOURCE_BUNDLE = GWT.create(IResourceBundle.class);

		@Source("SymbolDemo.gss")
		ICSSResources css();
	}

	/*
	 * A selection is an array of elements pulled from the current document
	 * D3 uses CSS3 to select elements
	 *   - See D3.select(String) and D3#select(com.google.gwt.dom.client.Node)
	 *     methods for creating Selection
	 *
	 * After selecting elements, you apply operators to them to do stuff
	 * These operators can get or set attributes, styles, properties, HTML and
	 * text content
	 * Attribute values and such are specified as either constants or functions
	 *   - the latter are evaluated for each element in the selection
	 *
	 * You can also join selections to data; this data is available to operators
	 * for data-driven transformations. In addition, joining to data produces
	 * enter and exit subselections, so that you may add or remove elements in
	 * response to changes in data
	 *
	 * You won't generally need to use for loops or recursive functions to
	 * modify the document with D3
	 *   - That's because you operate on entire selections at once, rather than
	 *     looping over individual elements
	 *   - However, you can still loop over elements manually if you wish:
	 *     - there's an each(DatumFunction) operator which invokes an arbitrary
	 *       function, and (TODO) selections are arrays, so elements can be
	 *       accessed directly (e.g., selection[0][0])
	 *   - D3 supports method chaining for brevity when applying multiple
	 *     operators: the operator return value is the selection
	 *
	 * Creating selections, D3 provides two top-level methods for selecting
	 * elements:
	 *   - D3.select(String)
	 *   - D3.selectAll(String)
	 * These methods accept selector strings
	 *   - the former selects only the first matching element
	 *   - the latter selects all matching elements in document traversal order
	 * There are also variant of these methods which accept nodes, which is
	 * useful to integrate with GWT Element API
	 *
	 * Operating on selections: Selections are arrays of elements—literally
	 *   - D3 binds additional methods to the array so that you can apply
	 *     operators to the selected elements, such as setting an attribute on
	 *     all the selected elements
	 *   - One nuance is that selections are grouped: rather than a
	 *     one-dimensional array, each selection is an array of arrays of
	 *     elements, this preserves the hierarchical structure of subselections
	 *   - Most of the time, you can ignore this detail, but that's why a
	 *     single-element selection looks like [[node]] rather than [node]
	 *   - For more on nested selections, see Nested Selections
	 */
	private Selection symbolGroup;
	private Symbol symbol;

	private final ICSSResources css;

	public SymbolDemo() {
		super();
		this.css = IResourceBundle.RESOURCE_BUNDLE.css();
		this.css.ensureInjected();
	}

	@Override
	public void start() {

		/*
		 * D3 class is an entry point for D3 api modules
		 * A lot of methods of this class allow access to other classes:
		 *   - select(Element) methods and Selection - manipulate elements in
		 *     the current document
		 *   - The array methods are in Array and Arrays classes
		 *   - The interpolators methods are in Array and Arrays classes
		 *   - Interpolation methods are in Interpolators provide access to symbolGroup
		 *     routines
		 *
		 * D3.symbolGroup() is used to get SVG class which support d3 generators
		 *   - Arc generator
		 *   - Area generator
		 *   - Axis generator
		 *   - Brush generator
		 *   - Chord generator
		 *   - Diagonal generator
		 *   - Line generator
		 *   - RadiaLine generator
		 *   - Symbol generator
		 *
		 * Symbol class is a PathDataGenerator which can generate symbols shapes
		 */
		this.symbol = D3.svg().symbol(); // Get a symbol generator

		this.startButton = new Button();
		this.startButton.setIcon(IconType.PLUS_SIGN);
		this.startButton.setText("Add Symbol");
		this.startButton.addClickHandler(this);

		this.stopButton = new Button();
		this.stopButton.setIcon(IconType.REMOVE);
		this.stopButton.setText("Clear");
		this.stopButton.addClickHandler(this);

		FlowPanel buttonPanel = new FlowPanel();
		buttonPanel.add(this.startButton);
		buttonPanel.add(this.stopButton);

		FlowPanel svgPanel = new FlowPanel();
		this.symbolGroup = D3.select(svgPanel)
				.append("svg")
				.attr("width", DEFAULT_WIDTH)
				.attr("height", DEFAULT_HEIGHT)
				.style("border", "1px solid #A2A9AF")
				.append("g")
				.attr("id", "symbolG");

		this.add(svgPanel);
		this.add(buttonPanel);

	}// End of start()

	@Override
	public void stop() {
		this.symbolGroup.selectAll("path").remove();
	}

	@Override
	public void onClick(ClickEvent clickEvent) {
		if (clickEvent.getSource() == this.startButton) {
			this.addSymbol();
		} else {
			this.stop();
		}
	}// End of onClick(ClickEvent)

	private void addSymbol() {
		Symbol.Type[] symbols = Symbol.Type.values();
		int count = symbols.length;
		this.symbol.type(symbols[Random.nextInt(count)]);
		this.symbol.size(Random.nextInt(2500) + 25); // Maxium: 50 * 50 + 25

		/*
		 * Selection.classed(classNames, boolean add):
		 *   - add: true to add, false to remove the class(es) from all the
		 *     elements of the selection
		 */
		this.symbolGroup.append("path")
				.classed(this.css.symboldemo(), true)
				/*
				 * A Transform is a representation of a SVG transform attribute
				 * It allows parsing the svg transform attribute with
				 * parse(String), manipulation using setters, such as
				 * rotate(double), then generation of the transform attribute
				 * with JavaScriptObject.toString()
				 */
				.attr("transform", Transform.parse("")
						.translate(Random.nextInt(DEFAULT_WIDTH),
								Random.nextInt(DEFAULT_HEIGHT)).toString())
				.attr("d", this.symbol.generate(1.0))
				.style("fill", Colors.rgb(Random.nextInt(255),
						Random.nextInt(255), Random.nextInt(255))
						.toHexaString());
	}

}///:~