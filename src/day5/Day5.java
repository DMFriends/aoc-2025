package day5;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day5
{
	static ArrayList<Long> availableIDs = new ArrayList<Long>();
	static ArrayList<ArrayList<Long>> ranges = new ArrayList<ArrayList<Long>>();
	static int numFreshIDs;
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day5/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day5/input_test.txt")))
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
				else
				{
					availableIDs.add(Long.parseLong(range[0]));
				}
			}
			
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
			
			for (long id : availableIDs)
			{
			    for (ArrayList<Long> r : ranges)
			    {
			        if (checkFresh(r, id))
			        {
			            numFreshIDs++;
			            break;
			        }
			    }
			}
			
			System.out.println("The password is " + numFreshIDs);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static boolean checkFresh(ArrayList<Long> range, long id)
	{
		if(range.get(0) <= id && id <= range.get(1))
		{
			return true;
		}
		else
		{
			return false;
		}
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
