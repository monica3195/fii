using System;
using System.Collections.Generic;
using System.Lynq;
using System.Text;
using System.IO;

namespace CNTema2
{
	public class Array
	{
		public double[] array;
		public int length;

		public Array()
		{
			array=null;
			this.length=0;
		}

		public Array(int dimension)
		{
			this.length=dimension;
			this.array=new double[dimension];
		}

		public Array(int dimension, double[] initializationArray)
		{
			this.length=dimension;
			this.array=new double[dimension];
			for(int i=0;i<dimension;i++)
				this.array[i]=initializationArray[i];
		}

		public void Parse(string str)
		{
			string[] elements = str.Split(' ');
			for(int i=0;i<this.length;i++)
			{
				double aux = Double.Parse(elements[i], System.Globalization.NumberStyles.Float);
				this.array[i]=aux;
			}				
		}

		public void readFromFile(string path)
		{
			FileStream fileStream = new FileStream(path, FileMode.Open);
			StreamReader str = new StreamReader(fileStream);
			String input ="";
			try
			{
				input = str.ReadLine();
				this.length = int.Parse(input);
				this.array = new double[length];
				string data = str.ReadLine();
				string[] fields = data.Split(' ');
				for(int j=0; j<this.length; j++)
				{
					double aux = Double.Parse(fields[j],System.Globalization.NumberStyles.Float);
					this.array[j]=aux;
				}
			}
			finally
			{
				flieStream.Close();
			}
		}
	}
}
