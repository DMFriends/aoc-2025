package day08;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day8Part2
{
	static ArrayList<ArrayList<Integer>> coords = new ArrayList<ArrayList<Integer>>();
	static ArrayList<ArrayList<ArrayList<Integer>>> circuits = new ArrayList<ArrayList<ArrayList<Integer>>>();
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day8/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day8/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String[] line = input.nextLine().split(",");
				
				ArrayList<Integer> row = new ArrayList<Integer>();
			    for (int i = 0; i < line.length; i++)
			    {
			    	row.add(Integer.parseInt(line[i]));
			    }
			    
			    coords.add(row);
			}
			
			connect();
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
		double dz = p1.get(2) - p2.get(2);
	    
	    return Math.sqrt(dx*dx + dy*dy + dz*dz);
	}
	
	public static void connect()
	{
	    int n = coords.size();
	    int[] parent = new int[n];
	    for (int i = 0; i < n; i++)
	    {
	        parent[i] = i;
	    }
	    
	    ArrayList<Edge> edges = new ArrayList<>();
	    for (int i = 0; i < n; i++)
	    {
	        for (int j = i + 1; j < n; j++)
	        {
	            double dist = calculateDistance(coords.get(i), coords.get(j));
	            edges.add(new Edge(i, j, dist));
	        }
	    }
	    
	    edges.sort((a, b) -> Double.compare(a.distance, b.distance));
	    
	    int lastBox1 = -1;
	    int lastBox2 = -1;
	    
	    for (Edge e : edges)
	    {
	        if (find(parent, e.box1) != find(parent, e.box2))
	        {
	            union(parent, e.box1, e.box2);
	            lastBox1 = e.box1;
	            lastBox2 = e.box2;
	        }
	    }
	    
	    long x1 = coords.get(lastBox1).get(0);
	    long x2 = coords.get(lastBox2).get(0);
	    
	    long result = x1 * x2;
	    System.out.println("The answer is " + result);
	}

	public static int find(int[] parent, int x)
	{
	    if (parent[x] != x)
	    {
	        parent[x] = find(parent, parent[x]);
	    }
	    return parent[x];
	}

	public static void union(int[] parent, int x, int y)
	{
	    int rootX = find(parent, x);
	    int rootY = find(parent, y);
	    parent[rootX] = rootY;
	}
	
	public static class Edge
	{
	    int box1, box2;
	    double distance;
	    
	    public Edge(int b1, int b2, double d)
	    {
	        box1 = b1;
	        box2 = b2;
	        distance = d;
	    }
	}
}
