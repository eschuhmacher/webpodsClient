/**
 * ---------------------------------------------------------------------------

 */
package webpods.client.client.webpods;

import java.lang.String;import java.util.logging.Level;
import java.util.logging.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class WebppodsClient.
 */
public class WebppodsClient {

	/** The logger. */
	private final Logger logger;

	/**
	 * Instantiates a new tokens.
	 * 
	 * @param logger
	 *            the logger
	 */
	public WebppodsClient(final Logger logger) {
		this.logger = logger;
	}


    public void connect(String url) {
        WebppodsClient.client(url);
    }

    public PV subscribePV(String name, boolean readOnly, String type, Integer version, Integer maxRate) {
        return WebppodsClient.createPV(name, readOnly, type, version, maxRate);
    }

    public PV subscribePV(String name, boolean readOnly, String type, Integer version) {
        return WebppodsClient.createPV(name, readOnly, type, version);
    }
    public PV subscribePV(String name, boolean readOnly) {
        return WebppodsClient.createPV(name, readOnly);
    }

    private static native void client(String url)/*-{
        $wnd.lWSC = new $wnd.Client(url);
	}-*/;


    private static native PV createPV(String name, boolean readOnly, String type, Integer version, Integer maxRate)/*-{
		var pv = $wnd.lWSC.subscribePV(name, readOnly, type, version, maxRate);
		return pv;
	}-*/;


    private static native PV createPV(String name, boolean readOnly, String type, Integer version)/*-{
		var pv = $wnd.lWSC.subscribePV(name, readOnly, type, version);
		return pv;
	}-*/;

    private static native PV createPV(String name, boolean readOnly)/*-{
		var pv = $wnd.lWSC.subscribePV(name, readOnly);
		return pv;
	}-*/;

	/**
	 * Log.
	 * 
	 * @param message
	 *            the message
	 */
	private void log(final String message) {
		this.logger.log(Level.INFO, message);
	}

}
