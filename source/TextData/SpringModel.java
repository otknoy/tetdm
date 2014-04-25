//
// Utility Program for TETDM 
// SpringModel.java Version 0.50
// Copyright(C) 2013-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source.TextData;


import java.io.*;
import java.util.*;

import java.awt.*;


public class SpringModel
{
	////SPRING MODEL
	public double springK;		//Spring constant value	K 0.2
	public double minus;		//Repulsion rate against the gravity 0.8
    
    public double realDistance[][];
    public double idealDistance[][];
    
	public boolean springModelMoving;
    
    public DisplayObject object[];
    public int objectNumber;
    
    public int screenSizeX, screenSizeY;
    public double value[][];
    public boolean link[][];
    
    public SpringModel(){}
    
    public SpringModel(DisplayObject object[])
    {
        this.object = object;
        objectNumber = object.length;
        
        realDistance = new double[objectNumber][objectNumber];
        idealDistance = new double[objectNumber][objectNumber];
    }
    
	//Execute spring model : REQUIRES link[][] and value[][] before start SPRING MODEL
	public void startSpringModel(int screenSizeX, int screenSizeY, double value[][], boolean link[][])
	{
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;
        this.value = value;
        this.link = link;
        
		calculateIdealDistance();
        
		springModelMoving = true;
        
        springK = 0.2;
        minus = 0.8;
        
        executeSpringModel();
	}
    
    public void executeSpringModel()
    {
        while(springModelMoving)
        {
			springStep();
			reArrange();
			springK -= 0.001;
			if(springK < 0)
            {
                springModelMoving = false;
                return;
			}
        }
    }
    
	public void stopSpringModel()
	{
		springModelMoving = false;
	}
    
	//Ideal distance calculation
	public void calculateIdealDistance()
	{
        double minimumDistance = screenSizeX / 10;  //Minimum distance between nodes (for spring model)
        
		for(int i=0;i<objectNumber;i++)
			for(int j=0;j<objectNumber;j++)
			{
				if(link[i][j])
					idealDistance[i][j] = minimumDistance;
				else
					idealDistance[i][j] = Math.max(minimumDistance*5/value[i][j], object[i].fontSize*2 + minimumDistance);
				
				idealDistance[i][j] = Math.min(idealDistance[i][j], minimumDistance+screenSizeX/2);
			}
	}
    
	//Move nodes by spring model, calculated from relation values and ideal distances
	public void springStep()	//Calculate power effect on each node
	{
		double tx,ty,dx,dy,tdx,tdy;
		double powerLimit;
        
		powerLimit = 100;
        
		for(int i=0;i<objectNumber;i++)
		{
			dx = dy = 0;
			if(object[i].linkNumber > 0)
				for(int j=0;j<objectNumber;j++)
					if(i != j && object[j].linkNumber > 0)
					{
						tx = object[i].x-object[j].x;
						ty = object[i].y-object[j].y;
						realDistance[i][j] = Math.sqrt(Math.pow(tx,2)+Math.pow(ty,2));
                        
						if(realDistance[i][j] ==0)
							continue;
                        
						tdx = tx*springK*(1.0 - idealDistance[i][j]/realDistance[i][j]);
						tdy = ty*springK*(1.0 - idealDistance[i][j]/realDistance[i][j]);
                        
                        if(realDistance[i][j] < object[i].fontSize*3)  // for the nearest nodes
                        {
                            if(link[i][j] == true)
                            {
                                tdx *= 10;
                                tdy *= 10;
                            }
                        }
                        
						if(tdx > powerLimit)
							tdx = powerLimit;
						if(tdx < -powerLimit)
							tdx = -powerLimit;
						if(tdy > powerLimit)
							tdy = powerLimit;
						if(tdy < -powerLimit)
							tdy = -powerLimit;
                        
						if(link[i][j] == true)  //Gravity
						{
							//Power to X direction and to Y direction
							dx += tdx;
							dy += tdy;
						}
						else	//Repulsion
						{
							dx += minus*tdx;
							dy += minus*tdy;
						}
					}
			object[i].x -= dx;
			object[i].y -= dy;
            
			if(object[i].x < 0)
				object[i].x = object[i].fontSize;
			if(object[i].y < 0)
				object[i].y = object[i].fontSize;
			if(object[i].x > screenSizeX)
				object[i].x = screenSizeX - object[i].sizeX;
			if(object[i].y > screenSizeY)
				object[i].y = screenSizeY - object[i].sizeY;
		}
	}
    
	public void reArrange()	 //Re-arrangement of nodes: Place to the center of the field
	{
		int i,minx=0,maxx=0,miny=0,maxy=0,dx,dy;
        
		//Optimize display position
		for(i=0;i<objectNumber;i++)//Initial values
			if(object[i].linkNumber > 0)
			{
				minx = maxx = (int)object[i].x;
				miny = maxy = (int)object[i].y;
				break;
			}
        
		for(;i<objectNumber;i++)//Measure range of existance nodes
			if(object[i].linkNumber > 0)
			{
				if(object[i].x < minx)
					minx = (int)object[i].x;
				if(object[i].x > maxx)
					maxx = (int)object[i].x;
				if(object[i].y < miny)
					miny = (int)object[i].y;
				if(object[i].y > maxy)
					maxy = (int)object[i].y;
			}
        
		if(maxx == minx && maxy == miny)
			return;
        
		dx = screenSizeX/2 - (maxx+minx)/2;
		dy = screenSizeY/2 - (maxy+miny)/2;
        
		for(i=0;i<objectNumber;i++)
			if(object[i].linkNumber > 0)
			{
				object[i].x += dx;
				object[i].y += dy;
			}
	}
}
