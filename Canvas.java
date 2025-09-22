import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kölling
 *
 * @version 7.0 (modificado con drawString)
 */
public class Canvas
{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas()
    {
        if(canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Picture Demo", 500, 300, 
                                         Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, Object> shapes; // puede guardar formas o textos

    /**
     * Create a Canvas.
     * @param title    title to appear in Canvas Frame
     * @param width    the desired width for the canvas
     * @param height   the desired height for the canvas
     * @param bgColor the desired background color of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColor)
    {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        frame.setLocation(30,30);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColor = bgColor;
        frame.pack();
        objects = new ArrayList<Object>();
        shapes = new HashMap<Object, Object>();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false) 
     */
    public void setVisible(boolean visible)
    {
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background color
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
    public void draw(Object referenceObject, String color, Shape shape)
    {
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }

    /**
     * Draw a given text string onto the canvas with color and reference.
     * @param referenceObject  an object to define identity for this text
     * @param color            the color of the text
     * @param text             the string to be drawn
     * @param x                the x coordinate
     * @param y                the y coordinate
     */
    public void drawString(Object referenceObject, String color, String text, int x, int y)
    {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescriptionText(text, color, x, y, 14)); // tamaño por defecto
        redraw();
    }

    /**
     * Draw a given text string onto the canvas with custom font size.
     * @param referenceObject  an object to define identity for this text
     * @param color            the color of the text
     * @param text             the string to be drawn
     * @param x                the x coordinate
     * @param y                the y coordinate
     * @param fontSize         the font size to use
     */
    public void drawString(Object referenceObject, String color, String text, int x, int y, int fontSize)
    {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescriptionText(text, color, x, y, fontSize));
        redraw();
    }

    /**
     * Draw a given text string onto the canvas (default black color).
     * @param text   the string to be drawn
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    public void drawString(String text, int x, int y)
    {
        Object referenceObject = new Object(); // identificador temporal
        shapes.put(referenceObject, new ShapeDescriptionText(text, "black", x, y, 14));
        objects.add(referenceObject);
        redraw();
    }

    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased 
     */
    public void erase(Object referenceObject)
    {
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground color of the Canvas.
     * @param  newColor   the new color for the foreground of the Canvas 
     */
    public void setForegroundColor(String colorString)
    {
        if(colorString.equals("red")) {
            graphic.setColor(new Color(235, 25, 25));
        }
        else if(colorString.equals("black")) {
            graphic.setColor(Color.black);
        }
        else if(colorString.equals("blue")) {
            graphic.setColor(new Color(30, 75, 220));
        }
        else if(colorString.equals("yellow")) {
            graphic.setColor(new Color(255, 230, 0));
        }
        else if(colorString.equals("green")) {
            graphic.setColor(new Color(80, 160, 60));
        }
        else if(colorString.equals("magenta")) {
            graphic.setColor(Color.magenta);
        }
        else if(colorString.equals("white")) {
            graphic.setColor(Color.white);
        }
        else {
            graphic.setColor(Color.black);
        }
    }

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds)
    {
        try { Thread.sleep(milliseconds); } 
        catch (Exception e) { }
    }

    /**
     * Redraw all shapes currently on the Canvas.
     */
    private void redraw()
    {
        erase();
        for(Object obj : objects) {
            Object desc = shapes.get(obj);
            if (desc instanceof ShapeDescription) {
                ((ShapeDescription)desc).draw(graphic);
            } else if (desc instanceof ShapeDescriptionText) {
                ((ShapeDescriptionText)desc).draw(graphic);
            }
        }
        canvas.repaint();
    }

    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase()
    {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        Dimension size = canvas.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel
    {
        public void paint(Graphics g)
        {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    /************************************************************************
     * Inner class ShapeDescription - associates a shape with its color.
     */
    private class ShapeDescription
    {
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color)
        {
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic)
        {
            setForegroundColor(colorString);
            graphic.fill(shape);
        }
    }

    /************************************************************************
     * Inner class ShapeDescriptionText - associates a text string with its color.
     */
    private class ShapeDescriptionText
    {
        private String text;
        private String colorString;
        private int x, y, fontSize;

        public ShapeDescriptionText(String text, String color, int x, int y, int fontSize)
        {
            this.text = text;
            this.colorString = color;
            this.x = x;
            this.y = y;
            this.fontSize = fontSize;
        }

        public void draw(Graphics2D graphic)
        {
            setForegroundColor(colorString);
            graphic.setFont(new Font("Arial", Font.BOLD, fontSize));
            graphic.drawString(text, x, y);
        }
    }
}

