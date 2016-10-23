using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace Tema2CN
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
		
        public override string ToString()
        {
            string str = "\r\n";
            foreach (double i in array)
                str += "  " + i.ToString();
            str += "\r\n";
            return str;
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
                fileStream.Close();
			}
		}
	}
}
