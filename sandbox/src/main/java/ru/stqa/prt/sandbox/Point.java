package ru.stqa.prt.sandbox;

public class Point {
  public double x;
  public double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;

  }

  public double distance(Point p) {
    double d = Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
    return d;
  }
}
