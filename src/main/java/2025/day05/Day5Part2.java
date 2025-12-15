package day05;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day5Part2
{
	static ArrayList<ArrayList<Long>> ranges = new ArrayList<ArrayList<Long>>();
	static long numFreshIDs;
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/main/java/day05/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/main/java/day05/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String[] range = input.nextLine().split("-");

				if(range.length == 2)
				{
					ArrayList<Long> row = new ArrayList<Long>();
				    for (int i = 0; i < range.length; i++)
				    {
				        row.add(Long.parseLong(range[i]));
				    }
				    
				    ranges.add(row);
				}
			}
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
		
	    ranges.sort((a, b) -> Long.compare(a.get(0), b.get(0)));
		
		int i = 0;
		while (i < ranges.size() - 1)
		{
		    ArrayList<Long> current = ranges.get(i);
		    ArrayList<Long> next = ranges.get(i + 1);

		    if (rangesOverlap(current, next))
		    {
		        long mergedStart = Math.min(current.get(0), next.get(0));
		        long mergedEnd = Math.max(current.get(1), next.get(1));

		        current.set(0, mergedStart);
		        current.set(1, mergedEnd);
		        ranges.remove(i + 1);
		    }
		    else
		    {
		        i++;
		    }
		}

		numFreshIDs = countFreshIDs();
		
		System.out.println("The answer is " + numFreshIDs);
	}
	
	public static long countFreshIDs()
	{
	    long count = 0;
	    
	    for (ArrayList<Long> range : ranges)
	    {
	        long start = range.get(0);
	        long end = range.get(1);
	        count += (end - start + 1);
	    }
	    
	    return count;
	}

	public static boolean rangesOverlap(ArrayList<Long> r1, ArrayList<Long> r2)
	{
	    long r1Start = r1.get(0);
	    long r1End   = r1.get(1);

	    long r2Start = r2.get(0);
	    long r2End   = r2.get(1);

	    return r1Start <= r2End && r2Start <= r1End;
	}
}
