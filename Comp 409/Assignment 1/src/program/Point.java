package program;

import java.util.Random;

/**
 * Created by tim on 14-09-17.
 * Point container class
 */
public final class Point implements Comparable<Point> {
    public int x;
    public int y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point random() {
        Random r = new Random();
        int x = r.nextInt();
        int y = r.nextInt();
        return new Point(x, y);
    }
    
    public double r() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double angle() {
        double val = Math.atan((double) y / (double) x);
        return val >= 0 ? val : Math.PI - val;
    }

    @Override
    public int hashCode() {
        return (Long.toString(x) + "," + Long.toString(y)).hashCode();
    }

    @Override
    public int compareTo(Point other) {
        if (this.equals(other)) {
            return 0;
        } else {
            if (this.angle() > other.angle()) {
                return 1;
            } else if (this.angle() < other.angle()) {
                return -1;
            } else {
                return Double.compare(this.r(), other.r());
            }
        }
    }

    public int compareByY(Point other) {

        if (this.y < other.y) {
            return -1;
        } else if (this.y > other.y) {
            return 1;
        } else {
            return Long.compare(other.x, this.x);
        }
    }

    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
}
