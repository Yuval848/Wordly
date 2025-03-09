package com.example.wordly_by_yuvalmiz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BoardGame extends View {
    private static final int ROWS = 6;  // 6 rows in the grid
    private static final int COLS = 5;  // 5 columns in the grid
    private static final float PADDING = 20f;  // Padding around the grid

    private Paint gridPaint;
    private Paint cellPaint;
    public Cell[][] gridCells;  // 2D array of cells
    private boolean firstTime = true;

    public BoardGame(Context context) {
        super(context);
        init();
    }


    private void init() {
        gridPaint = new Paint();
        gridPaint.setColor(0xFF000000);  // Set grid color (black)
        gridPaint.setStyle(Paint.Style.STROKE);  // Only draw the border (not filled)
        gridPaint.setStrokeWidth(4);  // Line width for grid borders

        cellPaint = new Paint();
        cellPaint.setColor(Color.WHITE);  // Default color is white for empty cells

        // Initialize the grid cells
        gridCells = new Cell[ROWS][COLS];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

         if(firstTime)
         {
             int width = getWidth();
             int height = getHeight();

             // Calculate the width and height for each cell in the grid
             float cellWidth = (width - 2 * PADDING) / COLS;
             float cellHeight = (height - 2 * PADDING) / ROWS;

             // Draw the grid and create Cell objects with unique IDs
             int cellId = 0;
             for (int row = 0; row < ROWS; row++) {
                 for (int col = 0; col < COLS; col++) {
                     // Calculate the position of each cell
                     float left = PADDING + col * cellWidth;
                     float top = PADDING + row * cellHeight;
                     float right = left + cellWidth;
                     float bottom = top + cellHeight;

                     // Create a new Cell with a unique ID
                     gridCells[row][col] = new Cell(cellId, left, top, right, bottom);


                     // Increment cell ID for the next cell
                     cellId++;
                 }
             }
             firstTime = false;
         }

         for (int row = 0; row < ROWS; row++) {
             for (int col = 0; col < COLS; col++) {
                 // Draw the rectangle for this cell
                 gridCells[row][col].draw(canvas, cellPaint);

             }
         }



    }

    // method to update the background color of a specific cell
    public void setCellBackgroundColor(int row, int col, int color) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            gridCells[row][col].setBackgroundColor(color);
            invalidate();  // Redraw the view to apply the new color
        }
    }


    public void setNewWord(int row,String userguess) {
        for (int i = 0; i < 5; i++) {
            gridCells[row][i].setChar(userguess.charAt(i));
            invalidate();
        }


    }
}