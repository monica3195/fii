using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CNTema2
{
	public class Sistem
	{
		public Array initialB;
		public Array B;
		public Matrix A;
		public Array X;
		public Matrix reverseB;
		public Matrix reverseA;
		public Matrix Identity;
		public Matrix Aux;
		public Boolean Singularity;
		public int dimension;
		public double precision;

		public Sistem()
		{
			B = new Array();
			A = new Matrix();
			X = new Array();
			reverseA = new Matrix();
			dimension = 0;
			IdentityInitialization();
			Aux = new Matrix(A);
			Singularity=false;
		} 

		public Sistem(int dimension, Matrix A, Array B, double precision)
		{
			initialB = new Array (B.length,B.array);
			this.dimension = dimension;
			this.A = new Matrix(A);
			this.B = new Array(dimension,B.array);
			X = new Array(dimension);
			reverseA = new Matrix (dimension,dimension);
			IdentityInitialization();
			Aux = new Matrix(A);
			Singularity=false;
			reverseB = new Matrix(Identity);
			this.precision = precision;
		}

		private void IdentityInitialization() //matricea de identitate
		{
			Identity = new Matrix(this.dimension,this.dimension);
			for(int i=0; i<this.dimension;i++)
				(for int j=0;j<this.dimension;j++)
					if(i==j)
						Identity.matrix[i,j] = 1;
					else
						Identity.matrix[i,j] = 0;
		}

		private int Pivot(int column) 
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
				M.matrix[line1,i] = M.matrix[lin2,i];
				M.matrix[line2,i] = aux;

				aux = reverseB.matrix[lin1,i];
				reverseB.matrix[line1,i] = reverseB.matrix[line2,i];
				reverseB.matrix[line2,i] = aux;
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

					//Si pentru inversa
					for(int k=0;k<this.dimension;k++)
						if(Math.Abs(Aux.matrix[currentLine,currentLine]) > precision)
							reverseB.matrix[i,k] = reverseB.matrix[i,k] - Aux.matrix[i,currentLine] / Aux.matrix[currentLine,currentLine] * reverseB.matrix[currentLine,k];
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

						//Si pentru B invers
						for(int i=0;i<this.dimension;i++)
							temporary.array[i]=0;
						for(int j=0;j<this.dimension;j++)
						{
							for(int i=currentLine+1;i<this.dimension;i++)
								temporary.array[j]+= Aux.matrix[currentLine,i] * reverseA.matrix[i,j];
							if(Math.Abs(Aux.matrix[currentLine,currentLine]) > this.precision)
								reverseA.matrix[currentLine,j] = (reverseB.matrix[currentLine,j] - temporary.array[j]) / Aux.matrix[currentLine,currentLine];
							else
								Program.MainForm.showMessageBox();
						}
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
					sum+=A.matrix[i,j] * X.array[j];
				if(sum - initialB.array[i] !=0 )
					return "Wrong solution!";
			}
			return "Good solution!";
		}

		public bool checkReverse()
		{
			Matrix m1 = new Matrix(this.A.multiply(reverseA));
			return m1.Equality(Identity);
		}

		public double maxError()
		{
			Matrix m1 = new Matrix(this.A.multiply(reverseA));
			Matrix temp = new Matrix(m1.Substract(Identity));
			double max = 0;
			for(int i=0;i<this.dimension;i++)
			{
				double lineSum = 0;
				for(int j=0;j<this.dimension;j++)
					lineSum+=temp.matrix[i,j];
				if(lineSum > max)
					max= lineSum;
			}
			return max;
		}
	}
}