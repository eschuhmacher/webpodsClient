package webpods.client.client.webpods.widgets;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import webpods.client.client.webpods.Alarm;
import webpods.client.client.webpods.PV;
import webpods.client.client.webpods.PvDisplay;
import webpods.client.client.webpods.PvValue;
import webpods.client.client.webpods.widgets.Spinner.SpinnerResources;

import java.lang.Exception;import java.lang.Integer;import java.lang.Long;import java.lang.String;

/**
 * A {@link ValueSpinner} is a combination of a {@link TextBox} and a
 * {@link Spinner} to allow spinning <h3>CSS Style Rules</h3> <ul class='css'>
 * <li>.gwt-ValueSpinner { primary style }</li> <li>.gwt-ValueSpinner .textBox {
 * the textbox }</li> <li>.gwt-ValueSpinner .arrows { the spinner arrows }</li>
 * </ul>
 */
public class ValueSpinner extends HorizontalPanel {

    /**
     * Resources used.
     */
    public interface ValueSpinnerResources {
        @ImageBundle.Resource("bg_textbox.png")
        DataResource background();
    }
    private static ValueSpinnerResources defaultResources;

    private static final String STYLENAME_DEFAULT = "gwt-ValueSpinner";

    private PV pv;
    private Spinner spinner;
    private final TextBox valueBox = new TextBox();

    private SpinnerListener spinnerListener = new SpinnerListener() {
        public void onSpinning(long value) {
            if (getSpinner() != null) {
                getSpinner().setValue(value, false);
            }
            valueBox.setText(formatValue(value));
            updatePV();
        }
    };

    private KeyPressHandler keyPressHandler = new KeyPressHandler() {

        public void onKeyPress(KeyPressEvent event) {
            int index = valueBox.getCursorPos();
            String previousText = valueBox.getText();
            String newText;
            if (valueBox.getSelectionLength() > 0) {
                newText = previousText.substring(0, valueBox.getCursorPos())
                        + event.getCharCode()
                        + previousText.substring(valueBox.getCursorPos()
                        + valueBox.getSelectionLength(), previousText.length());
            } else {
                newText = previousText.substring(0, index) + event.getCharCode()
                        + previousText.substring(index, previousText.length());
            }
            valueBox.cancelKey();
            try {
                long newValue = parseValue(newText);
                if (spinner.isConstrained()
                        && (newValue > spinner.getMax() || newValue < spinner.getMin())) {
                    return;
                }
                spinner.setValue(newValue, true);
            } catch (Exception e) {
                // valueBox.cancelKey();
            }
        }
    };

    /**
     * @param value initial value
     * @param styles the styles and images used by this widget
     * @param images the images used by the spinner
     */
    public ValueSpinner(long value, ValueSpinnerResources styles,
                        SpinnerResources images, PV pv) {
        this(value, 0, 0, 1, 99, false, styles, images, pv);
    }

    /**
     * @param value initial value
     * @param min min value
     * @param max max value
     */
    public ValueSpinner(long value, int min, int max, PV pv) {
        this(value, min, max, 1, 99, true, pv);
    }

    /**
     * @param value initial value
     * @param min min value
     * @param max max value
     * @param minStep min value for stepping
     * @param maxStep max value for stepping
     */
    public ValueSpinner(long value, int min, int max, int minStep, int maxStep, PV pv) {
        this(value, min, max, minStep, maxStep, true, pv);
    }

    /**
     * @param value initial value
     * @param min min value
     * @param max max value
     * @param minStep min value for stepping
     * @param maxStep max value for stepping
     * @param constrained if set to false min and max value will not have any
     *          effect
     */
    public ValueSpinner(long value, int min, int max, int minStep, int maxStep,
                        boolean constrained, PV pv) {
        this(value, min, max, minStep, maxStep, constrained, null, pv);
    }

    /**
     * @param value initial value
     * @param min min value
     * @param max max value
     * @param minStep min value for stepping
     * @param maxStep max value for stepping
     * @param constrained if set to false min and max value will not have any
     *          effect
     * @param resources the styles and images used by this widget
     */
    public ValueSpinner(long value, int min, int max, int minStep, int maxStep,
                        boolean constrained, ValueSpinnerResources resources, PV pv) {
        this(value, min, max, minStep, maxStep, constrained, resources, null, pv);
    }



    /**
     * @param value initial value
     * @param min min value
     * @param max max value
     * @param minStep min value for stepping
     * @param maxStep max value for stepping
     * @param constrained if set to false min and max value will not have any
     *          effect
     * @param resources the styles and images used by this widget
     * @param images the images used by the spinner
     */
    public ValueSpinner(long value, int min, int max, int minStep, int maxStep,
                        boolean constrained, ValueSpinnerResources resources,
                        SpinnerResources images, PV pv) {
        super();
        this.pv = pv;
        addCallBack(pv);
        setStylePrimaryName(STYLENAME_DEFAULT);
        if (images == null) {
            spinner = new Spinner(spinnerListener, value, min, max, minStep, maxStep,
                    constrained);
        } else {
            spinner = new Spinner(spinnerListener, value, min, max, minStep, maxStep,
                    constrained, images);
        }
        valueBox.setStyleName("textBox");
        valueBox.addKeyPressHandler(keyPressHandler);
        setVerticalAlignment(ALIGN_MIDDLE);
        add(valueBox);
        VerticalPanel arrowsPanel = new VerticalPanel();
        arrowsPanel.setStylePrimaryName("arrows");
        arrowsPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        arrowsPanel.add(spinner.getIncrementArrow());
        arrowsPanel.add(spinner.getDecrementArrow());
        add(arrowsPanel);
    }

    /**
     * @return the Spinner used by this widget
     */
    public Spinner getSpinner() {
        return spinner;
    }

    /**
     * @return the SpinnerListener used to listen to the {@link Spinner} events
     */
    public SpinnerListener getSpinnerListener() {
        return spinnerListener;
    }

    /**
     * @return the TextBox used by this widget
     */
    public TextBox getTextBox() {
        return valueBox;
    }

    /**
     * @return whether this widget is enabled.
     */
    public boolean isEnabled() {
        return spinner.isEnabled();
    }

    /**
     * Sets whether this widget is enabled.
     *
     * @param enabled true to enable the widget, false to disable it
     */
    public void setEnabled(boolean enabled) {
        spinner.setEnabled(enabled);
        valueBox.setEnabled(enabled);
    }

    /**
     * @param value the value to format
     * @return the formatted value
     */
    protected String formatValue(long value) {
        return String.valueOf(value);
    }

    /**
     * @param value the value to parse
     * @return the parsed value
     */
    protected long parseValue(String value) {
        return Long.valueOf(value);
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
            instance.@webpods.client.client.webpods.widgets.ValueSpinner::update(Lwebpods/client/client/webpods/PvValue;)(pvValue);
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
        getSpinner().setValue(Integer.valueOf((String) value.getValue()), true);
        getTextBox().setText(formatValue(Integer.valueOf((String) value.getValue())));
        updateStype(value.getAlarm(), value.getDisplay());
    }

    private void updateStype(Alarm alarm, PvDisplay display) {

        if(alarm.getSeverity() == "MAJOR") {
            valueBox.setStyleName("input-text-fatal");
        } else if (alarm.getSeverity() == "MINOR"){
            valueBox.setStyleName("input-text-warning");
        } else {
            valueBox.setStyleName("input-text-normal");
        }
        if(display != null) {
            getSpinner().setMax(display.getHighDisplay());
            getSpinner().setMin(display.getLowDisplay());
        }

    }

    public void refresh(PV pv1) {
        this.pv = pv1;
        addCallBack(pv1);
    }

    public void updatePV() {
        this.pv.setValue(valueBox.getValue());
    }
}
