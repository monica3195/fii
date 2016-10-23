using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Tema2CN
{
	public class Sistem
	{
		public Array initialB;
		public Array B;
		public Matrix initialA;
		public Array X;
		public Matrix Aux;
		public Boolean Singularity;
		public int dimension;
		public double precision;
        public Array Z;

		public Sistem()
		{
			B = new Array();
            Z = new Array();
			initialA = new Matrix();
			X = new Array();
			dimension = 0;
            Aux = new Matrix(initialA);
			Singularity=false;
		} 

		public Sistem(int dimension, Matrix A, Array B, double precision)
		{
			initialB = new Array (B.length,B.array);
            Z = new Array(dimension);
			this.dimension = dimension;
			this.initialA = new Matrix(A);
			this.B = new Array(dimension,B.array);
			X = new Array(dimension);
			Aux = new Matrix(A);
            Singularity = false;
			this.precision = precision;
		}

		private int Pivot(int column) //cel mai mare element de pe coloana
		{
			int max = column;
			for(int i = column+1; i<dimension;i++)
				if(Aux.matrix[i,column] > Aux.matrix[column,column])
					max=i;
			return max;
		}

		private void changeLines(int line1, int line2, Matrix M)
		{
			double aux;
			for(int i=0;i<dimension;i++)
			{
				aux = M.matrix[line1,i];
				M.matrix[line1,i] = M.matrix[line2,i];
				M.matrix[line2,i] = aux;
			}

			aux = B.array[line1];
			B.array[line1] = B.array[line2];
			B.array[line2] = aux;
		}

		public void GaussAlgorythm()
		{
			// Transformam prima coloana in superior triunghiulara
			int currentLine = 0;
			int pivot = Pivot(currentLine);
			if(pivot!=currentLine)
				changeLines(pivot,currentLine,Aux);

			//transformam si celelalte, pasul 7
			while ((currentLine < this.dimension - 1) && (Aux.matrix[currentLine,currentLine] != 0))
			{
				for(int i=currentLine+1;i<this.dimension;i++)
				{
					for(int j=currentLine+1;j<this.dimension;j++)
						if(Math.Abs(Aux.matrix[currentLine,currentLine]) > precision)
							Aux.matrix[i,j]=Aux.matrix[i,j] - (Aux.matrix[i,currentLine] / Aux.matrix[currentLine,currentLine]) * Aux.matrix[currentLine,j];
						else	
							Program.MainForm.showMessageBox();
				}

				//Pasul 8 din algoritm (cerinta temei)
				for(int i=currentLine+1;i<this.dimension;i++)
				{
					if(Math.Abs(Aux.matrix[currentLine,currentLine]) > precision)
						B.array[i] = B.array[i] - Aux.matrix[i,currentLine] / Aux.matrix[currentLine,currentLine] * B.array[currentLine];
					else
						Program.MainForm.showMessageBox();

				}

				// Pasul 9 din algoritm
				for(int i=currentLine+1;i<this.dimension;i++)
					Aux.matrix[i,currentLine]=0;
				currentLine++;
				pivot = Pivot(currentLine);
				if(pivot!=currentLine)
					changeLines(pivot,currentLine,Aux);
			}

			//Am transformat matricea in superior triunghiulara
			//Acum putem verifica daca este singulara

			if(Aux.matrix[currentLine,currentLine] == 0)
				this.Singularity=true;
			//Rezolvam sistemul
			else
				{
					double var;
					Array temporary = new Array(this.dimension);
					while(currentLine>=0)
					{
						var=0;
						for(int i=currentLine+1;i<this.dimension;i++)
							var+=Aux.matrix[currentLine,i] * X.array[i];
						if(Math.Abs(Aux.matrix[currentLine,currentLine]) > this.precision)
							X.array[currentLine] = (B.array[currentLine] - var) / Aux.matrix[currentLine,currentLine];
						else
							Program.MainForm.showMessageBox();
						currentLine--;	
					}
				}
		} // Sfarsit GaussAlgorythm

		public string checkSolution()
		{
			double sum;
			for(int i=0;i<this.dimension;i++)
			{
				sum=0;
				for(int j=0;j<this.dimension;j++)
                    sum += initialA.matrix[i, j] * X.array[j];
                Z.array[i] = sum - initialB.array[i];
			}
            double norm = 0;
            for (int i = 0; i < this.dimension; i++)
            {
                norm +=Math.Pow(Math.Abs(Z.array[i]),2);
            }
            return "\r\nThe Norm is:"+Math.Sqrt(norm).ToString();
		}

	}
}