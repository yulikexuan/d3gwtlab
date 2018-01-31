//: com.yuli.d3gwtlab.client.D3Lab.java


package com.yuli.d3gwtlab.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.yuli.d3gwtlab.client.democases.svg.SymbolDemo;


public class D3Lab implements EntryPoint {

    public void onModuleLoad() {

        SymbolDemo symbolDemo = new SymbolDemo();
        symbolDemo.start();

        RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        rootLayoutPanel.add(symbolDemo);

    }//: End of onModuleLoad

}///:~
