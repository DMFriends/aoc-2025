package day12;

import java.util.Scanner;
import java.util.Set;
import java.util.regex.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Day12
{
	static ArrayList<ArrayList<String>> shapes = new ArrayList<ArrayList<String>>();
	static ArrayList<String> shape = new ArrayList<String>();
	static List<List<List<String>>> allShapeVariants = new ArrayList<>();
	
	static class RegionSpec
	{
	    final int w;
	    final int h;
	    final int[] counts;

	    RegionSpec(int w, int h, int[] counts)
	    {
	        this.w = w;
	        this.h = h;
	        this.counts = counts;
	    }
	    
	    @Override
	    public String toString()
	    {
	        return w + "x" + h + ": " + Arrays.toString(counts);
	    }
	}

	static List<RegionSpec> regionsList = new ArrayList<>();
	static Pattern p = Pattern.compile("(\\d+)x(\\d+):\\s*(.*)");
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/main/java/day12/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/main/java/day12/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String line = input.nextLine().trim();
				
				Matcher m = p.matcher(line.trim());
				if (m.matches())
				{
				    int w = Integer.parseInt(m.group(1));
				    int h = Integer.parseInt(m.group(2));
				    int[] counts = Arrays.stream(m.group(3).trim().split("\\s+"))
				                         .filter(s -> !s.isEmpty())
				                         .mapToInt(Integer::parseInt)
				                         .toArray();
				    regionsList.add(new RegionSpec(w, h, counts));
				}
				else if (line.startsWith("#") || line.startsWith("."))
			    {
			        shape.add(line);
			    }
			    else
			    {
			        if (!shape.isEmpty())
			        {
			            shapes.add(new ArrayList<>(shape));
			            shape.clear();
			        }
			    }
			}
			
			if (!shape.isEmpty())
			{
			    shapes.add(new ArrayList<>(shape));
			}

			for (List<String> s : shapes)
			{
			    allShapeVariants.add(getAllVariants(s));
			}
			
			System.out.println("The answer is " + countRegions());
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static int countRegions()
	{
        int count = 0;
        for (RegionSpec region : regionsList)
        {
            if (canPackRegion(region)) {
                count++;
            }
        }
        return count;
    }
    
	public static boolean canPackRegion(RegionSpec region)
	{
	    int totalArea = 0;
	    for (int i = 0; i < region.counts.length && i < shapes.size(); i++)
	    {
	        totalArea += region.counts[i] * getShapeArea(shapes.get(i));
	    }
	    
	    if (totalArea > region.w * region.h)
	    {
	        return false;
	    }

	    List<Integer> toPlace = new ArrayList<>();
	    for (int i = 0; i < region.counts.length && i < shapes.size(); i++)
	    {
	        for (int j = 0; j < region.counts[i]; j++)
	        {
	            toPlace.add(i);
	        }
	    }
	    
	    toPlace.sort((a, b) -> {
	        int areaA = getShapeArea(shapes.get(a));
	        int areaB = getShapeArea(shapes.get(b));
	        return Integer.compare(areaB, areaA);
	    });
	    
	    char[][] grid = new char[region.h][region.w];
	    for (char[] row : grid) Arrays.fill(row, '.');
	    
	    boolean result = backtrack(grid, toPlace, 0, System.currentTimeMillis() + 60000);
	    return result;
	}

	public static int getShapeArea(List<String> shape)
	{
	    int count = 0;
	    for (String row : shape)
	    {
	        for (char c : row.toCharArray())
	        {
	            if (c == '#') count++;
	        }
	    }
	    return count;
	}
    
	public static boolean backtrack(char[][] grid, List<Integer> toPlace, int idx, long deadline)
    {
        if (System.currentTimeMillis() > deadline) return false;
        if (idx == toPlace.size()) return true;
        
        int shapeIdx = toPlace.get(idx);
        
        for (List<String> variant : allShapeVariants.get(shapeIdx))
        {
            int sh = variant.size();
            int sw = variant.get(0).length();
            
            for (int r = 0; r <= grid.length - sh; r++)
            {
                for (int c = 0; c <= grid[0].length - sw; c++)
                {
                    if (canPlace(grid, variant, r, c))
                    {
                        place(grid, variant, r, c, (char)('A' + shapeIdx));
                        if (backtrack(grid, toPlace, idx + 1, deadline)) return true;
                        unplace(grid, variant, r, c);
                    }
                }
            }
        }
        
        return false;
    }
    
	public static boolean canPlace(char[][] grid, List<String> shape, int r, int c)
    {
        for (int i = 0; i < shape.size(); i++)
        {
            for (int j = 0; j < shape.get(i).length(); j++)
            {
                if (shape.get(i).charAt(j) == '#' && grid[r + i][c + j] != '.')
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
	public static void place(char[][] grid, List<String> shape, int r, int c, char mark)
    {
        for (int i = 0; i < shape.size(); i++)
        {
            for (int j = 0; j < shape.get(i).length(); j++)
            {
                if (shape.get(i).charAt(j) == '#') {
                    grid[r + i][c + j] = mark;
                }
            }
        }
    }
    
	public static void unplace(char[][] grid, List<String> shape, int r, int c)
    {
        place(grid, shape, r, c, '.');
    }
    
	public static List<List<String>> getAllVariants(List<String> shape)
    {
        Set<String> seen = new HashSet<>();
        List<List<String>> variants = new ArrayList<>();
        
        for (int rot = 0; rot < 4; rot++) {
            addIfUnique(variants, seen, shape);
            addIfUnique(variants, seen, flip(shape));
            shape = rotate90(shape);
        }
        
        return variants;
    }
    
	public static void addIfUnique(List<List<String>> list, Set<String> seen, List<String> shape)
    {
        String key = String.join("|", shape);
        if (seen.add(key))
        {
            list.add(new ArrayList<>(shape));
        }
    }
    
	public static List<String> rotate90(List<String> shape)
    {
        int h = shape.size(), w = shape.get(0).length();
        List<String> rotated = new ArrayList<>();
        for (int c = 0; c < w; c++)
        {
            StringBuilder sb = new StringBuilder();
            for (int r = h - 1; r >= 0; r--)
            {
                sb.append(shape.get(r).charAt(c));
            }
            rotated.add(sb.toString());
        }
        
        return rotated;
    }
    
	public static List<String> flip(List<String> shape)
    {
        List<String> flipped = new ArrayList<>();
        for (String row : shape)
        {
            flipped.add(new StringBuilder(row).reverse().toString());
        }
        
        return flipped;
    }
}
