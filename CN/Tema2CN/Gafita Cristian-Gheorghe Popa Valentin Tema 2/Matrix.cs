using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace Tema2CN
{
	public class Matrix
	{
		public double[,] matrix;

		public int linNumber
		{get; set; }

		public int colNumber
		{get; set; }

		public Matrix()
		{}

		public Matrix(int lines, int columns)
		{
			this.linNumber=lines;
			this.colNumber=columns;
			this.matrix = new double[linNumber,colNumber];
		}

		public Matrix(Matrix m)
		{
			this.linNumber = m.linNumber;
			this.colNumber = m.colNumber;
			this.matrix = new double[linNumber,colNumber];
			for(int i=0;i<this.linNumber;i++)
				for(int j=0;j<this.colNumber;j++)
					this.matrix[i,j]=m.matrix[i,j];
		}

		public override string ToString()
		{
			StringBuilder mat = new StringBuilder();
			mat.Append("\r\n");
			for(int i=0;i<this.linNumber;i++)
			{
				for(int j=0;j<this.colNumber;j++)
					mat.Append(this.matrix[i,j]+" ");
				mat.Append("\r\n");
			}
			return mat.ToString();
		}

		public string getLine(int line) 
		{
			StringBuilder myLine = new StringBuilder();
			for(int j=0;j<this.colNumber;j++)
                myLine.Append(this.matrix[line, j] + " ");
			return myLine.ToString();
		}

		public void readFromFile(string path)
		{
			FileStream fileStream = new FileStream(path, FileMode.Open);
			StreamReader stream = new StreamReader(fileStream);
			String input = "";
			try
			{
				input = stream.ReadLine();
				this.linNumber = int.Parse(input);
				input = stream.ReadLine();
				this.colNumber = int.Parse(input);
				this.matrix = new double[this.linNumber,this.colNumber];
				for (int i=0;i<this.linNumber;i++)
				{
					input = stream.ReadLine();
					string[] fields = input.Split(' ');
					for(int j=0;j<this.colNumber;j++)
					{
						double aux = Double.Parse(fields[j],System.Globalization.NumberStyles.Float);
						this.matrix[i,j] = aux;						
					}
				}
			}
			finally
			{
				fileStream.Close();
			}
		}

		public Matrix Multiply (Matrix m2) 
		{
			Matrix M = new Matrix(this.linNumber, m2.colNumber);
			for(int i=0;i<M.linNumber;i++)
				for(int j=0;j<M.colNumber;j++)
					{
						M.matrix[i,j]=0;
						for(int k=0;k<M.colNumber;k++)
							M.matrix[i,j] += this.matrix[i,k] * m2.matrix[k,j];
					}
			return M;
		}
	}
}












