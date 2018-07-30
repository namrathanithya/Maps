package com.example.namra.mvvm_map_new.model;

import java.util.ArrayList;
import java.util.List;

public class OpeningHours {

    //@SerializedName("open_now")
    //@Expose
    private Boolean open_now;
    //@SerializedName("weekday_text")
    //@Expose
    private List<Object> weekday_text = new ArrayList<Object>();

    /**
     *
     * @return
     * The openNow
     */
    public Boolean getOpenNow() {
        return open_now;
    }

    /**
     *
     * @param openNow
     * The open_now
     */
    public void setOpenNow(Boolean openNow) {
        this.open_now = openNow;
    }

    /**
     *
     * @return
     * The weekdayText
     */
    public List<Object> getWeekdayText() {
        return weekday_text;
    }

    /**
     *
     * @param weekdayText
     * The weekday_text
     */
    public void setWeekdayText(List<Object> weekdayText) {
        this.weekday_text = weekdayText;
    }

}