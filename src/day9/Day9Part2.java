package day9;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day9Part2
{
	static ArrayList<ArrayList<Integer>> coords = new ArrayList<ArrayList<Integer>>();
	
	static char[][] grid;
	static int gridWidth, gridHeight;
	static int minX, minY, maxX, maxY;
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day9/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day9/input_test.txt")))
	    {
	        while(input.hasNextLine()) {
	            String[] line = input.nextLine().split(",");
	            ArrayList<Integer> row = new ArrayList<>();
	            for (int i = 0; i < line.length; i++) {
	                row.add(Integer.parseInt(line[i].trim()));
	            }
	            coords.add(row);
	        }
	        
	        System.out.println("The answer is " + findLargestRect());
	    }
		catch (FileNotFoundException ex)
		{
	        System.out.println("Error! File not found!");
	    }
	}
	
	public static double calculateDistance(ArrayList<Integer> p1, ArrayList<Integer> p2)
	{
		double dx = p1.get(0) - p2.get(0);
		double dy = p1.get(1) - p2.get(1);
	    
	    return Math.sqrt(dx*dx + dy*dy);
	}

	public static long findLargestRect()
	{
	    long maxArea = 0;
	    
	    HashSet<String> greenTiles = new HashSet<>();
	    for (int i = 0; i < coords.size(); i++)
	    {
	        ArrayList<Integer> p1 = coords.get(i);
	        ArrayList<Integer> p2 = coords.get((i + 1) % coords.size());
	        
	        int x1 = p1.get(0), y1 = p1.get(1);
	        int x2 = p2.get(0), y2 = p2.get(1);
	        
	        if (x1 == x2)
	        {
	            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++)
	            {
	                greenTiles.add(x1 + "," + y);
	            }
	        }
	        else if (y1 == y2)
	        {
	            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++)
	            {
	                greenTiles.add(x + "," + y1);
	            }
	        }
	    }

	    for (int i = 0; i < coords.size(); i++)
	    {
	        for (int j = i + 1; j < coords.size(); j++)
	        {
	            ArrayList<Integer> p1 = coords.get(i);
	            ArrayList<Integer> p2 = coords.get(j);
	            
	            if (isValidRectangleEfficient(p1, p2, greenTiles))
	            {
	                long width = Math.abs(p1.get(0) - p2.get(0)) + 1;
	                long height = Math.abs(p1.get(1) - p2.get(1)) + 1;
	                long area = width * height;
	                
	                if (area > maxArea)
	                {
	                    maxArea = area;
	                }

	            }
	        }
	    }
	    
	    return maxArea;
	}

	private static boolean isValidRectangleEfficient(ArrayList<Integer> p1, ArrayList<Integer> p2, HashSet<String> greenTiles)
	{
	    int minXRect = Math.min(p1.get(0), p2.get(0));
	    int maxXRect = Math.max(p1.get(0), p2.get(0));
	    int minYRect = Math.min(p1.get(1), p2.get(1));
	    int maxYRect = Math.max(p1.get(1), p2.get(1));
	    
	    long width = maxXRect - minXRect + 1;
	    long height = maxYRect - minYRect + 1;

	    int samples = (int)Math.min(100, Math.max(20, Math.sqrt(width * height) / 100));

	    for (int i = 0; i <= samples; i++)
	    {
	        int x = minXRect + (int)((long)(maxXRect - minXRect) * i / samples);
	        if (!isInsideOrOnBoundary(x, minYRect, greenTiles)) return false;
	        if (!isInsideOrOnBoundary(x, maxYRect, greenTiles)) return false;
	    }
	    
	    for (int i = 0; i <= samples; i++)
	    {
	        int y = minYRect + (int)((long)(maxYRect - minYRect) * i / samples);
	        if (!isInsideOrOnBoundary(minXRect, y, greenTiles)) return false;
	        if (!isInsideOrOnBoundary(maxXRect, y, greenTiles)) return false;
	    }
	    
	    for (int i = 1; i < samples; i++)
	    {
	        for (int j = 1; j < samples; j++)
	        {
	            int x = minXRect + (int)((long)(maxXRect - minXRect) * i / samples);
	            int y = minYRect + (int)((long)(maxYRect - minYRect) * j / samples);
	            if (!isInsideOrOnBoundary(x, y, greenTiles)) return false;
	        }
	    }
	    
	    return true;
	}

	private static boolean isInsideOrOnBoundary(int x, int y, HashSet<String> greenTiles)
	{
	    String key = x + "," + y;
	    for (ArrayList<Integer> coord : coords)
	    {
	        if (coord.get(0) == x && coord.get(1) == y) return true;
	    }
	    
	    if (greenTiles.contains(key)) return true;

	    return isInsidePolygon(x, y);
	}

	private static boolean isInsidePolygon(int x, int y)
	{
	    int crossings = 0;
	    for (int i = 0; i < coords.size(); i++)
	    {
	        ArrayList<Integer> p1 = coords.get(i);
	        ArrayList<Integer> p2 = coords.get((i + 1) % coords.size());
	        
	        int x1 = p1.get(0), y1 = p1.get(1);
	        int x2 = p2.get(0), y2 = p2.get(1);
	        
	        if ((y1 <= y && y < y2) || (y2 <= y && y < y1))
	        {
	            double xIntersect = x1 + (double)(y - y1) / (y2 - y1) * (x2 - x1);
	            if (x < xIntersect)
	            {
	                crossings++;
	            }
	        }
	    }
	    return crossings % 2 == 1;
	}
}
