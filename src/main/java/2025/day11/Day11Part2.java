package day11;

import java.io.*;
import java.util.*;

public class Day11Part2
{
	static final String SVR = "svr", OUT = "out", DAC = "dac", FFT = "fft";

	static int OUT_ID, DAC_ID, FFT_ID;
	static int[][] adj;
	static long[][] memo;

	static long dfs(int u, int mask)
	{
		if (u == OUT_ID) return (mask == 3) ? 1L : 0L;

		long cached = memo[u][mask];
		if (cached != -1) return cached;

		long total = 0L;
	    for (int v : adj[u]) {
	      int m = mask;
	      if (v == DAC_ID) m |= 1;
	      if (v == FFT_ID) m |= 2;
	      total += dfs(v, m);
	    }
	
	    memo[u][mask] = total;
	    return total;
	}

	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/main/java/day11/input.txt")))
		{
			List<String> lines = new ArrayList<>();
			while (input.hasNextLine())
			{
				String line = input.nextLine().trim();
				if (!line.isEmpty()) lines.add(line);
			}

			Set<String> names = new HashSet<>();
			List<String[]> parsed = new ArrayList<>();

			for (String line : lines)
			{
				String[] parts = line.split(":");
				String from = parts[0].trim();
				String[] tos = parts[1].trim().split("\\s+");
		
				parsed.add(new String[] { from, String.join(" ", tos) });
				names.add(from);
				for (String t : tos)
				{
					names.add(t);
				}
			}
		
			Map<String, Integer> id = new HashMap<>();
			int idx = 0;
			for (String s : names)
			{
				id.put(s, idx++);
			}
		
			OUT_ID = id.get(OUT);
			DAC_ID = id.get(DAC);
			FFT_ID = id.get(FFT);
			int SVR_ID = id.get(SVR);
		
			adj = new int[id.size()][];
			for (String[] row : parsed)
			{
				String from = row[0];
				String[] tos = row[1].isEmpty() ? new String[0] : row[1].split("\\s+");
				int[] out = new int[tos.length];
				for (int i = 0; i < tos.length; i++)
				{
					out[i] = id.get(tos[i]);
				}
				adj[id.get(from)] = out;
			}
			
			for (int i = 0; i < adj.length; i++)
			{
				if (adj[i] == null)
				{
					adj[i] = new int[0];
				}
			}
		
			memo = new long[adj.length][4];
			for (int i = 0; i < adj.length; i++)
			{
				Arrays.fill(memo[i], -1L);
			}
		
			long answer = dfs(SVR_ID, 0);
			System.out.println("The answer is " + answer);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
}
