/**
 */

package webpods.client.client;

import java.lang.Override;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import webpods.client.client.webpods.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * The Class Index.
 *
 * Here you can see an example for using the GWT jWebSocket Client.
 *
 * @author kosmal
 */
public class Index implements EntryPoint {
    PV pv;
    /**
     * The output div. Here we can see the output of information log.
     * */
    static FlowPanel outputDiv;

    /**
     * The scroll panel. Makes us able to scroll through the output
     * */
    static ScrollPanel scrollPanel;

    /**
     * The container. Container for the sample's buttons and the output.
     * */
    static FlowPanel container;
    static FlowPanel container1;

    /**
     * The JSONClient.
     *
     * Instance of the JWebSocketJSONClient to deal with.
     * */

    /** The header panel. */
    static FlowPanel headerPanel;

    /**
     * The connect handler.
     *
     * Instance of the JWebSocketLogonHandler. Here you can do the connect and
     * handle the different callbacks.
     * */

    /** The user name text box. */
    private static TextBox userNameTextBox;

    /** The password text box. */
    private static PasswordTextBox passwordTextBox;

    /** The url text box. */
    private static TextBox urlTextBox;

    /** The connect button. */
    // private final Button connectButton;

    private static Button logonButton;
    private int page = 0;

    private final WebppodsClient webppodsClient = new WebppodsClient(Logger.getLogger("TokensPlugin"));
    private Demo1 demo1;
    private Demo2 demo2;
    private Demo3 demo3;
    private Demo4 demo4;



	/*
	 * #######################--- CONSTRUCTOR ---##############################
	 */

    /**
     * Instantiates a new sample.
     */
    public Index() {
        headerPanel = new FlowPanel();
    }

	/*
	 * #######################--- MODULE-LOAD---##############################
	 */

    /*
     * (non-Javadoc)
     *
     * @see com.google.gwt.core.webpods.client.EntryPoint#onModuleLoad()
     */
    @Override
    public void onModuleLoad() {


        webppodsClient.connect("ws://localhost:8080/webpods-client/socket");
        initButtons();
        RootPanel.get().add(headerPanel);


    }

    /**
     * Inits the buttons.
     */
    private void initButtons() {
        Button multiplePVs = new Button("10 PVs");
        multiplePVs.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                removePVs();
                page = 1;
                demo1 = new Demo1(webppodsClient);
                RootPanel.get().clear();
                RootPanel.get().add(demo1);
                RootPanel.get().add(headerPanel);

            }
        });
        this.headerPanel.add(multiplePVs);

        Button multiWidgets = new Button("same PV name multiple Widgets");
        multiWidgets.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                removePVs();
                page = 2;
                demo2 = new Demo2(webppodsClient);
                RootPanel.get().clear();
                RootPanel.get().add(demo2);
                RootPanel.get().add(headerPanel);
            }
        });
        this.headerPanel.add(multiWidgets);

        Button optionsPvs = new Button("test multiple pv options");
        optionsPvs.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                removePVs();
                page = 3;
                demo3 = new Demo3(webppodsClient);
                RootPanel.get().clear();
                RootPanel.get().add(demo3);
                RootPanel.get().add(headerPanel);
            }
        });

        this.headerPanel.add(optionsPvs);
        Button charts = new Button("Charts");
        charts.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                removePVs();
                page = 4;
                demo4 = new Demo4(webppodsClient);
                RootPanel.get().clear();
                RootPanel.get().add(demo4);
                RootPanel.get().add(headerPanel);
            }
        });
        this.headerPanel.add(charts);
    }

    private void removePVs() {
        if (page == 1) {
            for (PV pv : demo1.getPvs()) {
                pv.close();
            }
        } else  if (page == 2) {
            for (PV pv : demo2.getPvs()) {
                pv.close();
            }
        } else  if (page == 3) {
            for (PV pv : demo3.getPvs()) {
                pv.close();
            }
        } else  if (page == 4) {
            for (PV pv : demo4.getPvs()) {
                pv.close();
            }
        }

    }

}
