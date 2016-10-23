using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tema4CN
{
    public class Vector
    {
        public int n;
        public double[] vector;
        public Vector(int n, List<String> numbers, int offset)
        {
            vector = new double[n];
            this.n = n;
            while (n-- > 0)
                vector[n] = double.Parse(numbers[n + offset]);
        }

        public double this[int i]
        {
            get { return this.vector[i]; }
        }

        public Vector()
        { 
            
        }
    }

    public class Matrix
    {
        public double value;
        public int line;
        public int n ;
        public int[] index_col ;
        public double[] valori ;
        public int nn ;
        public Matrix(int n, List<String> element, int offset, double precision, int type)
        {
            this.n = n;
            int column;
            int nn = 0;

            Dictionary<int, double>[] aux = new Dictionary<int, double>[n];

            for (int i = 0; i < n; i++)
            {
                aux[i] = new Dictionary<int, double>();
            }

            for (int i = offset; i < element.Count; i++)
            {
                string[] elems = element[i].Split(new char[] { ',', ' ' }, StringSplitOptions.RemoveEmptyEntries);

                value = double.Parse(elems[0]);
                line = int.Parse(elems[1]);
                column = int.Parse(elems[2]);

                if (aux[line].ContainsKey(column))
                {
                    aux[line][column] += value;
                }
                else
                {
                    aux[line][column] = value;
                    nn++;
                }

            }

            int index = n + 1;

            index_col = new int[nn + 1];
            valori = new double[nn + 1];

            for (int i = 0; i < n; i++)
            {
                index_col[i] = index;//la elementele de pe diag principala in index_col avem de fapt nr de elemente de pe linia lui
                //adica (i+1)-i o sa ne dea cate elemente avem pe linia i
                foreach (var elem in aux[i])
                {
                    if (i == elem.Key)
                    {
                        valori[i] = elem.Value;
                    }
                    else
                    {
                        valori[index] = elem.Value;
                        index_col[index] = elem.Key;
                        index++;
                    }
                }
            }

            index_col[n] = nn + 1;
            this.nn = nn;

            for (int i = 0; i < n; i++)
            {
                if (Math.Abs(valori[i]) <precision)
                   throw new ArithmeticException("Matricea are elemente nule pe diagoanala.");
            }
        }

        public double this[int i, int j]
        {
            get
            {
                if (i == j)
                    return this.valori[i];

                for (int k = this.index_col[i]; k < this.index_col[i + 1]; k++)
                {
                    if (this.index_col[k] == j)
                        return this.valori[k];
                }

                return 0;
            }
        }

        public Matrix()
        {

        }

        public Matrix Multiply(Matrix b, double precision)
        {
            Matrix rezultat = new Matrix();
            rezultat.n = this.n;
            rezultat.nn = 0;
            Dictionary<int, double>[] aux = new Dictionary<int, double>[this.n];
            for (int i = 0; i < n; i++)
                aux[i] = new Dictionary<int, double>();
            for (int i = 0; i < this.n; i++)
            {
                for (int j = 0; j < this.n; j++)
                {
                    double val = 0.0f;
                    for (int k = this.index_col[i]; k < this.index_col[i + 1]; k++)
                    {
                        val += this.valori[k] * b[this.index_col[k], j];
                    }
                    val += this.valori[i] * b[i, j];
                    if (i == j)
                    {
                        aux[i][j] = val;
                        rezultat.nn++;
                    }
                    else if (Math.Abs(val) > precision)
                    {
                        aux[i][j] = val;
                        rezultat.nn++;
                    }
                }
            }
            int index = n + 1;
            rezultat.index_col = new int[rezultat.nn + 1];
            rezultat.valori = new double[rezultat.nn + 1];

            for (int i = 0; i < n; i++)
            {
                rezultat.index_col[i] = index;

                foreach (var elem in aux[i])
                {
 
                    if (i == elem.Key)
                    {
                        rezultat.valori[i] = elem.Value;
                    }
                    else
                    {
                        rezultat.valori[index] = elem.Value;
                        rezultat.index_col[index] = elem.Key;
                        index++;
                    }
                }
            }
            rezultat.index_col[rezultat.n] = rezultat.nn + 1;

            return rezultat;
        }

    }

    static class Functions
    {
        public static double Kmax = 5000;
        public static Vector SolveSOR(Matrix a, Vector b, double precision)
        {
            double[] xgs = new double[a.n];
            int k = 0;
            double dx = 0;
			double suma1,suma2,suma3;
            double maxDx = (double)Math.Pow(10, 8);

            do
            {
                k++;
				suma3=0;
                for (int i = 0; i < a.n; i++)
                {
                    suma1=suma2=0;

                    for (int j = a.index_col[i]; j < a.index_col[i + 1]; j++)
                    {
                        if (a.index_col[j] < i)
                            suma1 += a.valori[j] * xgs[a.index_col[j]];
                        else if (a.index_col[j] > i)
                            suma2 += a.valori[j] * xgs[a.index_col[j]];						
                    }
					suma3+=Math.Pow(((b[i]-suma1-suma2)/a.valori[i])-xgs[i],2);
					xgs[i]=(b[i]-suma1-suma2)/a.valori[i];
					
                }

                dx = (double)Math.Sqrt(suma3);
            } while (!(Math.Abs(dx) < precision) && k < Kmax && dx <= maxDx);

            if ((Math.Abs(dx) < precision))
            {
                Vector temp = new Vector();
                temp.n = xgs.Length;
                temp.vector = new double[temp.n];
                temp.vector = xgs;
                return temp;
            }
            throw new ArithmeticException("Divergenta");
        }

        public static void Load(String input, out Vector b, out Matrix mat, out Matrix A2, double precision)
        {
            List<String> lines = new List<string>();

            using (StreamReader stream = new StreamReader(input))
            {
                String line = null;

                while ((line = stream.ReadLine()) != null)
                {
                    lines.Add(line);
                }
            }
            int n = int.Parse(lines[0]);
            b = new Vector(n, lines, 2);
            mat = new Matrix(n, lines, 3 + n,precision,0);
            A2 = new Matrix(n, lines, 3 + n, precision, 1);
        }

        public static string checkSolution(Matrix A,Vector b,Vector x,int n)
        {
            double sum;
            double[] z = new double[n];

            for (int i = 0; i < A.n; i++)
            {
                sum = 0;

                for (int j = A.index_col[i]; j < A.index_col[i + 1]; j++)
                {
                    sum += A.valori[j] * x[A.index_col[j]];
                }
                sum += A.valori[i] * x[i];
                z[i] = sum - b[i];
            }

            double norm = 0;
            for (int i = 0; i < n; i++)
            {
                norm += Math.Pow(Math.Abs(z[i]), 2);
            }
            return Math.Sqrt(norm).ToString();
        }
    }
}
