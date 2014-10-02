package webpods.client.client.webpods;

/**
 * Created by eschuhmacher on 10/1/14.
 */
public class PvDisplay {
    public int lowAlarm;
    public int highAlarm;
    public int lowDisplay;
    public int highDisplay;
    public int lowWarning;
    public int highWarning;

    public PvDisplay(int lowAlarm, int highAlarm, int lowDisplay, int highDisplay, int lowWarning, int highWarning) {
        this.lowAlarm = lowAlarm;
        this.highAlarm = highAlarm;
        this.lowDisplay = lowDisplay;
        this.highDisplay = highDisplay;
        this.lowWarning = lowWarning;
        this.highWarning = highWarning;
    }

    public int getLowAlarm() {
        return lowAlarm;
    }

    public void setLowAlarm(int lowAlarm) {
        this.lowAlarm = lowAlarm;
    }

    public int getHighAlarm() {
        return highAlarm;
    }

    public void setHighAlarm(int highAlarm) {
        this.highAlarm = highAlarm;
    }

    public int getLowDisplay() {
        return lowDisplay;
    }

    public void setLowDisplay(int lowDisplay) {
        this.lowDisplay = lowDisplay;
    }

    public int getHighDisplay() {
        return highDisplay;
    }

    public void setHighDisplay(int highDisplay) {
        this.highDisplay = highDisplay;
    }

    public int getLowWarning() {
        return lowWarning;
    }

    public void setLowWarning(int lowWarning) {
        this.lowWarning = lowWarning;
    }

    public int getHighWarning() {
        return highWarning;
    }

    public void setHighWarning(int highWarning) {
        this.highWarning = highWarning;
    }
}
