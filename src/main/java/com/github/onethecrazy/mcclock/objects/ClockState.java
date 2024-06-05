package com.github.onethecrazy.mcclock.objects;

import com.github.onethecrazy.mcclock.statics.BackgroundColor;

public class ClockState {
    public int left;
    public int right;
    public int top;
    public int bottom;
    public float size;
    public long backgroundColor; //BackgroundColor-class
    public int textColor;
    public boolean isChroma;
    public int chromaVariation;
    public boolean isEnabled;
    public boolean showDate;
    public String datePattern;
    public boolean showWeekday;
    public boolean useTwentyFourHourFormat;
    public int heightMargin;
    public int widthMargin;


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
        this.heightMargin = 2;
        this.widthMargin = 4;
        this.chromaVariation = 0;
    }
}

