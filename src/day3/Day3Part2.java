package day3;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day3Part2
{
	static long maxJoltage = 0;
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day3/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day3/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				ArrayList<String> bank = new ArrayList<String>();
				
				String battery = input.nextLine();
				
				for(int i = 0; i < battery.length(); i++)
				{
					bank.add(Character.toString(battery.charAt(i)));
				}
				
				long largest = findLargest(bank);
				
				maxJoltage += largest;
			}
			
			System.out.println("The answer is " + maxJoltage);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static long findLargest(ArrayList<String> numbers)
	{
		ArrayList<Integer> chosen = new ArrayList<Integer>();
		long largestDigit = -1;
		int toRemove = numbers.size() - 12;

		for (String i : numbers)
		{
			int current = Integer.parseInt(i);
			
			while(toRemove > 0 && !chosen.isEmpty() && chosen.get(chosen.size() - 1) < current)
			{
				chosen.remove(chosen.size() - 1);
				toRemove--;
			}
			
			chosen.add(current);
		}
				
		String number = "";
		
		if(chosen.size() >= 12)
		{
			for(int i = 0; i < 12; i++)
			{
				number += Integer.toString(chosen.get(i));
				largestDigit = Long.parseLong(number);
			}
		}
		else
		{
			for(int i : chosen)
			{
				number += Integer.toString(i);
				largestDigit = Long.parseLong(number);
			}
		}

		System.out.println(largestDigit);
        
        return largestDigit;
    }
}
