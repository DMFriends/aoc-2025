package day2;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day2Part2
{
	static ArrayList<Long> invalidIDs = new ArrayList<Long>();
	static long sum = 0;
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day2/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day2/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String[] range = input.nextLine().split("-");
				long start = Long.parseLong(range[0]);
		        long end = Long.parseLong(range[1]);
		        
		        for(long num = start; num <= end; num++)
	            {
	                if(hasRepeatingDigits(num))
	                {
	                	System.out.println(num);
	                    invalidIDs.add(num);
	                }
	            }
			}
				
			for(long i : invalidIDs)
			{
				sum += i;
			}								
			
			System.out.println("The password is " + sum);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}

	// Helper method to check if a number has repeating digits
	public static boolean hasRepeatingDigits(long num)
	{
	    String numStr = Long.toString(num);
	    int len = numStr.length();
	    
	    // Try all possible pattern lengths from 1 to half the string length
	    for (int patternLen = 1; patternLen <= len / 2; patternLen++)
	    {
	        // Only check if the pattern could repeat evenly
	        if (len % patternLen == 0)
	        {
	            String pattern = numStr.substring(0, patternLen);
	            boolean matches = true;
	            
	            // Check if this pattern repeats throughout the entire number
	            for (int i = patternLen; i < len; i += patternLen)
	            {
	                String nextSegment = numStr.substring(i, i + patternLen);
	                if (!pattern.equals(nextSegment))
	                {
	                    matches = false;
	                    break;
	                }
	            }
	            
	            if (matches)
	            {
	                return true;
	            }
	        }
	    }
	    
	    return false;
	}
}
