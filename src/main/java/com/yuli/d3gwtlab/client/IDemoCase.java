//: com.yuli.d3gwtlab.client.IDemoCase.java


package com.yuli.d3gwtlab.client;


import com.google.gwt.user.client.ui.IsWidget;


public interface IDemoCase extends IsWidget {

	int DEFAULT_WIDTH = 500;
	int DEFAULT_HEIGHT = 500;

	void start();
	void stop();

}///:~