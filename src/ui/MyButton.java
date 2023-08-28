package ui;

import java.awt.*;

public class MyButton {

    public int x, y, width, height;
    private int id;
    private String text;
    private Rectangle bounds;
    private boolean mouseOver, mousePressed;

    //for normal buttons
    public MyButton(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id= -1;
        initBounds();
    }

    //for tile buttons
    public MyButton(int x, int y, int width, int height, String text, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = id;
        initBounds();
    }

    public int getId() {
        return id;
    }

    private void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics graphics) {
        drawBody(graphics);
        drawBorder(graphics);
        drawText(graphics);
    }

    private void drawBorder(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, width, height);
        if (mousePressed) {
            graphics.drawRect(x + 1, y + 1, width - 2, height - 2);
            graphics.drawRect(x + 2, y + 2, width - 4, height - 4);
        }
    }

    private void drawBody(Graphics graphics) {
        if (mouseOver) {
            graphics.setColor(Color.GRAY);
        } else {
            graphics.setColor(Color.WHITE);
        }
        graphics.fillRect(x, y, width, height);
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    private void drawText(Graphics graphics) {
        int w = graphics.getFontMetrics().stringWidth(text);
        int h = graphics.getFontMetrics().getHeight();
        graphics.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 2);

    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void resetBooleans() {
        this.mouseOver = false;
        this.mousePressed = false;
    }
}
