package ru.stqa.prt.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class PointTests {

  public void testCalculationDistanceWithoutFractionalPart() {
    Point p1 = new Point(1.0, 2.0);
    Point p2 = new Point(3.0, 4.0);
    Assert.assertEquals(p1.distance(p2), 2.8284271247461903);
  }

  public void testCalculationDistanceWithFractionalPart() {
    Point p1 = new Point(1.3, 2.6);
    Point p2 = new Point(3.5, 4.1);
    Assert.assertEquals(p1.distance(p2), 2.6627053911388696);
  }

  public void testCalculationDistanceReversible() {
    Point p1 = new Point(1.0, 2.0);
    Point p2 = new Point(3.0, 4.0);
    Assert.assertEquals(p1.distance(p2), 2.8284271247461903);
    Assert.assertEquals(p2.distance(p1), 2.8284271247461903);
  }

  public void testCalculationDistanceBetweenOnePoint() {
    Point p = new Point(1.0, 2.0);
    Assert.assertEquals(p.distance(p), 0.0);
  }

}
