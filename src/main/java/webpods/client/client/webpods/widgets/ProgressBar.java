package webpods.client.client.webpods.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import webpods.client.client.webpods.Alarm;
import webpods.client.client.webpods.PV;
import webpods.client.client.webpods.PvDisplay;
import webpods.client.client.webpods.PvValue;import java.lang.Double;import java.lang.Math;import java.lang.Override;import java.lang.String;

/**
 * A widget that displays progress on an arbitrary scale.
 *
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.gwt-ProgressBar-shell { primary style } </li>
 * <li>.gwt-ProgressBar-shell .gwt-ProgressBar-bar { the actual progress bar }
 * </li>
 * <li>.gwt-ProgressBar-shell .gwt-ProgressBar-text { text on the bar } </li>
 * <li>.gwt-ProgressBar-shell .gwt-ProgressBar-text-firstHalf { applied to text
 * when progress is less than 50 percent } </li>
 * <li>.gwt-ProgressBar-shell .gwt-ProgressBar-text-secondHalf { applied to
 * text when progress is greater than 50 percent } </li>
 * </ul>
 */
public class ProgressBar extends Widget implements ResizableWidget {

    private  final String DEFAULT_TEXT_CLASS_NAME =
            "gwt-ProgressBar-text";

    private  String textClassName = DEFAULT_TEXT_CLASS_NAME;
    private  String textFirstHalfClassName = DEFAULT_TEXT_CLASS_NAME + "-firstHalf";
    private  String textSecondHalfClassName = DEFAULT_TEXT_CLASS_NAME + "-secondHalf";

    /**
     * A formatter used to format the text displayed in the progress bar widget.
     */
    public abstract class TextFormatter {
        /**
         * Generate the text to display in the ProgressBar based on the current
         * value.
         *
         * Override this method to change the text displayed within the ProgressBar.
         *
         * @param curProgress the current progress
         * @return the text to display in the progress bar
         */
        protected abstract String getText(double curProgress);
    }

    /**
     * The bar element that displays the progress.
     */
    protected Element barElement;

    /**
     * The current progress.
     */
    protected double curProgress;

    /**
     * The maximum progress.
     */
    protected double maxProgress;

    /**
     * The minimum progress.
     */
    protected double minProgress;

    /**
     * A boolean that determines if the text is visible.
     */
    protected boolean textVisible = true;

    /**
     * The element that displays text on the page.
     */
    protected Element textElement;

    /**
     * The current text formatter.
     */
    protected TextFormatter textFormatter;

    /**
     * Create a progress bar with default range of 0 to 100.
     */
    public ProgressBar() {
        this(0.0, 100.0, 0.0, null);
    }

    /**
     * Create a progress bar with an initial progress and a default range of 0 to
     * 100.
     *
     * @param curProgress the current progress
     */
    public ProgressBar(double curProgress, final PV pv) {
        this(0.0, 100.0, curProgress, pv);
    }

    /**
     * Create a progress bar within the given range.
     *
     * @param minProgress the minimum progress
     * @param maxProgress the maximum progress
     */
    public ProgressBar(double minProgress, double maxProgress, final PV pv) {
        this(minProgress, maxProgress, 0.0, pv);
    }

    /**
     * Create a progress bar within the given range starting at the specified
     * progress amount.
     *
     * @param minProgress the minimum progress
     * @param maxProgress the maximum progress
     * @param curProgress the current progress
     */
    public ProgressBar(double minProgress, double maxProgress, double curProgress, final PV pv) {
        this(minProgress, maxProgress, curProgress, null, pv);
    }

    /**
     * Create a progress bar within the given range starting at the specified
     * progress amount.
     *
     * @param minProgress the minimum progress
     * @param maxProgress the maximum progress
     * @param curProgress the current progress
     * @param textFormatter the text formatter
     */
    public ProgressBar(double minProgress, double maxProgress,
                       double curProgress, TextFormatter textFormatter, final PV pv) {
        this.minProgress = minProgress;
        this.maxProgress = maxProgress;
        this.curProgress = curProgress;
        addCallBack(pv);
        setTextFormatter(textFormatter);

        // Create the outer shell
        setElement(DOM.createDiv());
        DOM.setStyleAttribute(getElement(), "position", "relative");
        setStyleName("gwt-ProgressBar-shell");

        // Create the bar element
        barElement = DOM.createDiv();
        DOM.appendChild(getElement(), barElement);
        DOM.setStyleAttribute(barElement, "height", "100%");
        setBarStyleName("gwt-ProgressBar-bar");

        // Create the text element
        textElement = DOM.createDiv();
        DOM.appendChild(getElement(), textElement);
        DOM.setStyleAttribute(textElement, "position", "absolute");
        DOM.setStyleAttribute(textElement, "top", "0px");

        // Set the current progress
        setProgress(curProgress);
    }

    /**
     * Get the maximum progress.
     *
     * @return the maximum progress
     */
    public double getMaxProgress() {
        return maxProgress;
    }

    /**
     * Get the minimum progress.
     *
     * @return the minimum progress
     */
    public double getMinProgress() {
        return minProgress;
    }

    /**
     * Get the current percent complete, relative to the minimum and maximum
     * values. The percent will always be between 0.0 - 1.0.
     *
     * @return the current percent complete
     */
    public double getPercent() {
        // Calculate the relative progress
        double percent = curProgress;
        return Math.max(0.0, Math.min(100.0, percent));
    }

    /**
     * Get the current progress.
     *
     * @return the current progress
     */
    public double getProgress() {
        return curProgress;
    }

    /**
     * Get the text formatter.
     *
     * @return the text formatter
     */
    public TextFormatter getTextFormatter() {
        return textFormatter;
    }

    /**
     * Check whether the text is visible or not.
     *
     * @return true if the text is visible
     */
    public boolean isTextVisible() {
        return textVisible;
    }

    /**
     * This method is called when the dimensions of the parent element change.
     * Subclasses should override this method as needed.
     *
     * Move the text to the center of the progress bar.
     *
     * @param width the new webpods.client width of the element
     * @param height the new webpods.client height of the element
     */
    public void onResize(int width, int height) {
        if (textVisible) {
            int textWidth = DOM.getElementPropertyInt(textElement, "offsetWidth");
            int left = (width / 2) - (textWidth / 2);
            DOM.setStyleAttribute(textElement, "left", left + "px");
        }
    }



    public void setBarStyleName(String barClassName) {
        DOM.setElementProperty(barElement, "className", barClassName);
    }


    /**
     * Set the current progress.
     *
     * @param curProgresss the current progress
     */
    public void setProgress(final double curProgresss) {
        curProgress =  curProgresss;

        // Calculate percent complete
        int percent = (int) (getPercent());
        DOM.setStyleAttribute(barElement, "width", percent + "%");
        DOM.setElementProperty(textElement, "innerHTML", generateText(curProgress));
        updateTextStyle(percent);

    }

    public void setTextFirstHalfStyleName(String textFirstHalfClassName) {
        this.textFirstHalfClassName = textFirstHalfClassName;
        onTextStyleChange();
    }

    /**
     * Set the text formatter.
     *
     * @param textFormatter the text formatter
     */
    public void setTextFormatter(TextFormatter textFormatter) {
        this.textFormatter = textFormatter;
    }

    public void setTextSecondHalfStyleName(String textSecondHalfClassName) {
        this.textSecondHalfClassName = textSecondHalfClassName;
        onTextStyleChange();
    }

    public void setTextStyleName(String textClassName) {
        this.textClassName = textClassName;
        onTextStyleChange();
    }

    /**
     * Sets whether the text is visible over the bar.
     *
     * @param textVisible True to show text, false to hide it
     */
    public void setTextVisible(boolean textVisible) {
        this.textVisible = textVisible;
        if (this.textVisible) {
            DOM.setStyleAttribute(textElement, "display", "");
        } else {
            DOM.setStyleAttribute(textElement, "display", "none");
        }
    }

    /**
     * Generate the text to display within the progress bar. Override this
     * function to change the default progress percent to a more informative
     * message, such as the number of kilobytes downloaded.
     *
     * @param curProgress the current progress
     * @return the text to display in the progress bar
     */
    protected String generateText(double curProgress) {
        if (textFormatter != null) {
            return textFormatter.getText(curProgress);
        } else {
            return (int) (getPercent()) + "";
        }
    }

    /**
     * Get the bar element.
     *
     * @return the bar element
     */
    protected Element getBarElement() {
        return barElement;
    }

    /**
     * Get the text element.
     *
     * @return the text element
     */
    protected Element getTextElement() {
        return textElement;
    }

    /**
     * This method is called immediately after a widget becomes attached to the
     * browser's document.
     */
    @Override
    protected void onLoad() {
        // Reset the position attribute of the parent element
        DOM.setStyleAttribute(getElement(), "position", "relative");
        ResizableWidgetCollection.get().add(this);
    }

    @Override
    protected void onUnload() {
        ResizableWidgetCollection.get().remove(this);
    }

    /**
     * Reset the progress text based on the current min and max progress range.
     */
    protected void resetProgress() {
        setProgress(getProgress());
    }

    private void onTextStyleChange() {
        int percent = (int) (getPercent());
        updateTextStyle(percent);

    }

    private void updateTextStyle(final int percent) {
        // Set the style depending on the size of the bar
        if (percent < 50) {
            DOM.setElementProperty(textElement, "className",
                    textClassName + " " + textFirstHalfClassName);
        } else {
            DOM.setElementProperty(textElement, "className",
                    textClassName + " " + textSecondHalfClassName);
        }
    }


    protected native void addCallBack(final PV pv)
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
            instance.@webpods.client.client.webpods.widgets.ProgressBar::update(Lwebpods/client/client/webpods/PvValue;)(pvValue);
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


    private void update(PvValue pvValue) {
        String value = (String) pvValue.getValue();
        setProgress(Double.valueOf(value));
        updateStype(pvValue.getAlarm(), pvValue.getDisplay());
    }

    private void updateStype(Alarm alarm, PvDisplay display) {

        if(alarm.getSeverity() == "MAJOR") {
            barElement.getStyle().setBackgroundColor("red");
        } else if (alarm.getSeverity() == "MINOR"){
            barElement.getStyle().setBackgroundColor("orange");
        } else {
            barElement.getStyle().setBackgroundColor("green");
        }
    }

}