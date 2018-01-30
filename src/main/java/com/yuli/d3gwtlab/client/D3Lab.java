//: com.yuli.d3gwtlab.client.D3Lab.java


package com.yuli.d3gwtlab.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;


public class D3Lab implements EntryPoint {

    public void onModuleLoad() {

        RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        rootLayoutPanel.add(new HTML("GWT D3.js Lab"));

    }//: End of onModuleLoad

}///:~
