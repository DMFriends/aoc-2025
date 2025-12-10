package day03;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Day3
{
	static int maxJoltage = 0;
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/main/java/day03/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/main/java/day03/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String[] bank = input.nextLine().split("");
				
				int largest = findLargest(bank);
				
				maxJoltage += largest;
			}
			
			System.out.println("The answer is " + maxJoltage);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static int findLargest(String[] numbers)
	{
		int best = -1;
		
		for (int i = 0; i < numbers.length - 1; i++)
		{
			int first = Integer.parseInt(numbers[i]);
			
			int maxSecond = -1;
		    
		    for (int j = i + 1; j < numbers.length; j++)
		    {
				if (Integer.parseInt(numbers[j]) > maxSecond)
		        {
		            maxSecond = Integer.parseInt(numbers[j]);
		        }
		    }
		    
		    int twoDigit = first * 10 + maxSecond;
		    if (twoDigit > best) 
		    {
		        best = twoDigit;
		    }
		}

        System.out.println(best);
        
        return best;
    }
}
