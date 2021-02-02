package ru.stqa.prt.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {
    Point p1 = new Point(1.0, 2.0);
    Point p2 = new Point(3.8, 4.9);
    System.out.println("Расстояние между точками " + "x1 = " + p1.x + "," + "y1 = " + p1.y + " и " + "x2 = " + p2.x + "," + "y2 = " + p2.y + " = " + distance(p1, p2));
  }

  public static double distance(Point p1, Point p2) {
    double d = Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    return d;
  }
}