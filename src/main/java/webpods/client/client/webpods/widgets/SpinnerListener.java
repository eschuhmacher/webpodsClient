package webpods.client.client.webpods.widgets;

/**
 * Every widget that can be wired to {@link Spinner} must implement this interface
 */
public interface SpinnerListener {
    /**
     * Implement this method to listen to spinner changes
     * @param value the current spinner value
     */
    void onSpinning(long value);
}