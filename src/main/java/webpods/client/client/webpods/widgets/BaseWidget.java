package webpods.client.client.webpods.widgets;

import webpods.client.client.webpods.PV;

/**
 * Created by eschuhmacher on 6/9/14.
 */
public class BaseWidget {

    private PV pv;

    private boolean alarm;

    public PV getPv() {
        return pv;
    }

    public void setPv(PV pv) {
        this.pv = pv;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }
}
