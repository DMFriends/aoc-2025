package day02;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day2
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
			
			System.out.println("The answer is " + sum);
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
	    
	    // The number must have even length to be split into two equal halves
	    if (len % 2 != 0)
	    {
	        return false;
	    }
	    
	    int halfLen = len / 2;
	    String firstHalf = numStr.substring(0, halfLen);
	    String secondHalf = numStr.substring(halfLen);
	    
	    // Check if the two halves are identical
	    return firstHalf.equals(secondHalf);
	}
}
