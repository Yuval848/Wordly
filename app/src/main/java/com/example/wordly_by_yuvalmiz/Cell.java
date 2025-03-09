package com.example.wordly_by_yuvalmiz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {
    int cellId;  // Unique ID for each cell
    float left, top, right, bottom;  // Coordinates of the cell
    private int backgroundColor;  // Background color of the cell

    char Char;

    // Constructor for creating a cell
    public Cell(int cellId, float left, float top, float right, float bottom) {
        this.cellId = cellId;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

    }

    // Method to set the background color of the cell
    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
    }
    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    // Method to draw the cell on the canvas
    public void draw(Canvas canvas, Paint paint) {

        paint.setColor(Color.BLACK);  // Black border color
        paint.setStyle(Paint.Style.STROKE);  // Only draw the border (not filled)
        paint.setStrokeWidth(4);  // Line width for grid borders
        canvas.drawRect(left, top, right, bottom, paint);
        // Set the background color

        paint.setStyle(Paint.Style.FILL);  // Only draw the border (not filled)
        paint.setColor(backgroundColor);
        canvas.drawRect(left+4, top+4, right-4, bottom-4, paint);


        Paint p = new Paint();
        p.setTextSize(100);
        canvas.drawText(""+Char,left+50,bottom-50,p);


    }

    public void setChar(char c) {
        this.Char = c;
    }
}