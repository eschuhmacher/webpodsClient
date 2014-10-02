package webpods.client.client.webpods.widgets;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import webpods.client.client.webpods.Alarm;
import webpods.client.client.webpods.PV;
import webpods.client.client.webpods.PvDisplay;
import webpods.client.client.webpods.PvValue;


import java.lang.Override;import java.util.ArrayList;

/**
 * A popup panel with some extra functionality specialized for use as a drop
 * down.
 */
public class DropDownPanel extends PopupPanel {

    // Set of open panels so we can close them on window resize, because resizing
    // the window is equivalent to the user clicking outside the widget.
    private ArrayList<DropDownPanel> openPanels;
    private ResizeHandler resizeHandler = new ResizeHandler() {

        public void onResize(ResizeEvent event) {
            if (openPanels != null) {
                ArrayList<DropDownPanel> old = openPanels;
                openPanels = null;
                for (DropDownPanel panel : old) {
                    assert (panel.isShowing());
                    if (panel.currentAnchor != null) {
                        panel.showRelativeTo(panel.currentAnchor);
                    }
                }
                old.clear();
                openPanels = old;
            }
        }
    };

    private Widget currentAnchor;

    /**
     * Creates a new drop down panel.
     */
    public DropDownPanel() {
        super(true);
        setStyleName("gwt-DropDownPanel");
        setPreviewingAllNativeEvents(true);
    }

    @Override
    public final void hide() {
        hide(false);
    }

    @Override
    public void hide(boolean autohide) {
        if (!isShowing()) {
            return;
        }
        super.hide(autohide);

        // Removes this from the list of open panels.
        if (openPanels != null) {
            openPanels.remove(this);
        }
    }

    @Override
    public void show() {
        if (isShowing()) {
            return;
        }
        // Add this to the set of open panels.
        if (openPanels == null) {
            openPanels = new ArrayList<DropDownPanel>();
            Window.addResizeHandler(resizeHandler);
        }
        openPanels.add(this);
        super.show();
    }

    public void showRelativeTo(Widget anchor) {
        setCurrentAnchor(anchor);
        super.showRelativeTo(anchor);
    }

    private void setCurrentAnchor(Widget anchor) {
        if (currentAnchor != null) {
            this.removeAutoHidePartner(currentAnchor.getElement());
        }
        if (anchor != null) {
            this.addAutoHidePartner(anchor.getElement());
        }
        currentAnchor = anchor;
    }

    public native void addCallBack(PV pv)
    /*-{
        var instance = this;
        pv.addCallback(function(evt, pv) {
            switch (evt.type) {
            case "connection": //connection state changed
                break;
            case "value": //value changed
                //PV's value is usually a data structure that has timestamp, value, alarm and meta information.
                //Here is an example on how to extract the timestamp and
                //numeric value from PV if the PV holds numeric value
                var pvValue = pv.getValue();
                pvValue = evt.value;
                instance.@webpods.webpods.client.webpods.client.webpods.widgets.ValueSpinner::update(Lorg/webpods/client/webpods/PvValue;)(pvValue);
                break;
            case "error": //error happened
                break;
            case "writePermission":	// write permission changed.
                break;
            case "writeFinished": // write finished.
                break;
            default:
                break;
            }
        });
	}-*/;


    private void update(PvValue value) {

    }




}