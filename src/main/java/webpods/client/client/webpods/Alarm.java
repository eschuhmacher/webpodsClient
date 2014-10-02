package webpods.client.client.webpods;

/**
 * Created by eschuhmacher on 10/1/14.
 */
public class Alarm {

    String severity;
    String status;

    public Alarm(final String severity, final String status) {
        this.severity = severity;
        this.status = status;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
