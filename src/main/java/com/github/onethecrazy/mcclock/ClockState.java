package com.github.onethecrazy.mcclock;

public class ClockState {
    public int left;
    public int right;
    public int top;
    public int bottom;
    public float size;
    public long backgroundColor; //BackgroundColor-class
    public int textColor;
    public boolean isChroma;
    public boolean isEnabled;
    public boolean showDate;
    public String datePattern;
    public boolean showWeekday;
    public boolean useTwentyFourHourFormat;

    public ClockState(){
        this.left = 0;
        this.right = 100;
        this.top = 0;
        this.bottom = 20;
        this.size = 1f;
        this.backgroundColor = BackgroundColor.pleasingGreyTransparent;
        this.textColor = 0xFFFFFF;
        this.isChroma = true;
        this.isEnabled = true;
        this.showDate = false;
        this.datePattern = "dd/MMMM/yyyy";
        this.showWeekday = false;
        this.useTwentyFourHourFormat = true;
    }
}

