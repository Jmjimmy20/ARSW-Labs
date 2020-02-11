/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.model;

/**
 *
 * @author hcadavid
 */
public class Point {
   
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }    
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object b){
        boolean flag = false;
        if (!b.getClass().getSimpleName().equals("Point")){
            flag = false;
        } else {
            Point c = (Point) b;
            if (this.x == c.getX() && this.y == c.getY()) {
                flag = true;
            }
        }
        return flag;
    }
    
    
}
