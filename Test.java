package test;
import java.io.*;
public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Please enter an interger between 1 and 15:");
		String ch;
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		ch=buf.readLine();
		int c=Integer.parseInt(ch);
		for(int i=0;i<(int)c;i++)
		{
			int k=i+1;
			for(int j=(int)c-i+1;j>0;j--)
				System.out.print("  ");
			for(int j=0;j<i*2+1;j++)
			{
				if(j<i)
				{
					System.out.print(" "+k);
					k--;
				}
				else
				{
					System.out.print(" "+k);
					k++;
				}
			}
			System.out.println("");
		}
		
	}

}
