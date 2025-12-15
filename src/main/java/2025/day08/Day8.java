package day08;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day8
{
	static ArrayList<ArrayList<Integer>> coords = new ArrayList<ArrayList<Integer>>();
	static ArrayList<ArrayList<ArrayList<Integer>>> circuits = new ArrayList<ArrayList<ArrayList<Integer>>>();
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/main/java/day08/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/main/java/day08/input_test.txt")))
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
	    
	    for (int k = 0; k < Math.min(1000, edges.size()); k++)
	    {
	        Edge e = edges.get(k);
	        union(parent, e.box1, e.box2);
	    }
	    
	    HashMap<Integer, Integer> groups = new HashMap<>();
	    for (int i = 0; i < n; i++)
	    {
	        int root = find(parent, i);
	        groups.put(root, groups.getOrDefault(root, 0) + 1);
	    }
	    
	    ArrayList<Integer> sizes = new ArrayList<>(groups.values());
	    sizes.sort((a, b) -> b - a);
	    
	    int result = sizes.get(0) * sizes.get(1) * sizes.get(2);
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
