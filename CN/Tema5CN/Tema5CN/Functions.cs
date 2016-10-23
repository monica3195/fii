using MathNet.Numerics.LinearAlgebra.Double;
using MathNet.Numerics.LinearAlgebra.Double.Factorization;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tema5CN
{
    class Functions
    {
        public double precision;
        public int p, n;
        public DenseMatrix A;
        public Svd svd;
        public DenseVector singularValues;
        public int Rank;
        public double ConditionNumber;
        public double norm;
        //public DenseMatrix x;
        public DenseVector XI;
        public DenseMatrix NormMatrix;
        public DenseVector b;
        public Matrix P;

        public DenseMatrix readMatrixFromFile(string path, DenseMatrix M,ref int p,ref int n)
        {
            FileStream fileStream = new FileStream(path, FileMode.Open);
            StreamReader stream = new StreamReader(fileStream);
            String input = "";
            try
            {
                input = stream.ReadLine();
                p = Int32.Parse(input);
                input = stream.ReadLine();
                n = Int32.Parse(input);
                M = new DenseMatrix(p, n);
                for (int i = 0; i < p; i++)
                {
                    input = stream.ReadLine();
                    string[] fields = input.Split(' ');
                    for (int j = 0; j < n; j++)
                    {
                        float aux = float.Parse(fields[j], System.Globalization.NumberStyles.Float);
                        M[i, j] = aux;
                    }
                }
                return M;
            }
            finally
            {
                fileStream.Close();
            }
        }

        public DenseVector readVectorFromFile(string path, DenseVector V)
        {
            FileStream fileStream = new FileStream(path, FileMode.Open);
            StreamReader stream = new StreamReader(fileStream);
            String input = "";
            try
            {
                V = new DenseVector(this.p);
                input = stream.ReadLine();
                string[] fields = input.Split(' ');
                for (int i = 0; i < p; i++)
                {
                        float aux = float.Parse(fields[i], System.Globalization.NumberStyles.Float);
                        V[i] = aux;
                }
                return V;
            }
            finally
            {
                fileStream.Close();
            }
        }

        public string ToString(DenseMatrix A)
        {
            StringBuilder mat = new StringBuilder();
            mat.Append("\r\n");
            for (int i = 0; i < A.RowCount; i++)
            {
                for (int j = 0; j < A.ColumnCount; j++)
                    if (A[i, j] > precision || -A[i, j] > precision)
                        mat.Append(A[i, j] + " ");
                    else
                    {
                        mat.Append(0 + " ");
                    }
                mat.Append("\r\n");
            }
            return mat.ToString();
        }

        public string ToString(DenseVector V)
        {
            string str = "\r\n";
            foreach (double i in V)
                str += "  " + i.ToString();
            str += "\r\n";
            return str;
        }

        public void SingularValueDecomposition(DenseMatrix A)
        {
            this.svd = A.Svd(true);
            
            this.singularValues = (DenseVector)svd.S();
            
            this.Rank = svd.Rank;

            this.ConditionNumber = svd.ConditionNumber;

           // this.norm = svd.Norm2;
            DenseMatrix U,S, VT;
            U = (DenseMatrix)svd.U();
            VT = (DenseMatrix)svd.VT();
            S = new DenseMatrix(p, n);
            for (int i = 0; i < this.singularValues.Count; i++)
            {
                S[i, i] = this.singularValues[i];
            }
            this.NormMatrix = A - U * S * VT;
            double sum, maxsum = -99999999999999999;
            for (int i = 0; i < this.NormMatrix.RowCount; i++)
            {
                sum = 0;
                for (int j = 0; j < this.NormMatrix.ColumnCount; j++)
                {
                    sum += this.NormMatrix[i, j];
                }
                if (maxsum < sum)
                {
                    maxsum = sum;
                }
            }
            this.norm = maxsum;

            //this.x=(DenseMatrix)svd.Solve(this.A);
            DenseMatrix SI;
            SI = new DenseMatrix(n, p);
            int h = 0;
            for (int i = 0; i < this.singularValues.Count && h < this.singularValues.Count; i++)
            {
                if (this.singularValues[h] > 0)
                {
                    SI[i, i] = 1 / this.singularValues[h];
                    h++;
                }             
            }
            this.XI = (DenseVector)(svd.VT().Transpose() * SI * svd.U().Transpose() * this.b);
        }

    }

    public class Matrix
    {
        public double value;
        public int line;
        public int n;
        public int[] index_col;
        public double[] valori;
        public int nn;
        int ok = 1;
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
                        valori[i] = elem.Value + type;
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
                if (Math.Abs(valori[i]) < precision)
                    throw new ArithmeticException("Matricea are elemente nule pe diagoanala.");
            }
        }

        public Matrix(int n)
        {
            this.n = n;
            int column;
            int nn = 0;

            Dictionary<int, double>[] aux = new Dictionary<int, double>[n];

            for (int i = 0; i < n; i++)
            {
                aux[i] = new Dictionary<int, double>();
            }



            Random r = new Random();

            for (int i = 0; i < n; i++)
            {
                aux[i][i] = r.Next(1, 2000);
                nn++;
            }

            for (int i = 0; i < n; i++)
            {
                line = r.Next(0, n);
                column = r.Next(0, n);
                value = r.Next(0, 2000);


                if (aux[line].ContainsKey(column))
                {
                    aux[line][column] += value;
                    aux[column][line] += value;
                }
                else
                {
                    aux[line][column] = value;
                    aux[column][line] = value;
                    nn += 2;
                }
            }

            int index = n + 1;

            index_col = new int[nn * 2];
            valori = new double[nn * 2];

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

        }

        public void powerMethod(int length, double precision)
        {
            double[] vectGen = new double[length];

            for (int i = 0; i < length; i++)
                vectGen[i] = Math.Sqrt(1.0 / (double)length);

            double[,] vect = new double[length, 1];
            for (int i = 0; i < length; i++)
                vect[i, 0] = vectGen[i];

            double matrixVal;
            double[,] w = new double[length, 1];
            matrixVal = 0;
            

                for (int i = 0; i < length; i++)
                {
                    matrixVal = 0;
                    //matrixVal += this[i, k] * vect[k, 0];
                    for (int j = this.index_col[i]; j < this.index_col[i + 1]; j++)
                    {
                        matrixVal += this.valori[j] * vect[i, 0];
                    }
                    matrixVal += this.valori[i] * vect[i, 0];
                    w[i, 0] = matrixVal;
                }
                         
            double lambda = 0;
            for (int i = 0; i < length; i++)
                lambda = lambda + vectGen[i] * w[i, 0];

            double norm_aux;
            int K = 0;
            do
            {
                norm_aux = 0;
                for (int i = 0; i < length; i++)
                    norm_aux = norm_aux + (w[i, 0] - lambda * vect[i, 0]) * (w[i, 0] - lambda * vect[i, 0]);

                norm_aux = Math.Sqrt(norm_aux);
                double norm_w = 0;
                for (int i = 0; i < w.Length; i++)
                    norm_w = norm_w + w[i, 0] * w[i, 0];
                norm_w = Math.Sqrt(norm_w);

                for (int i = 0; i < length; i++)
                    vect[i, 0] = 1.0 / norm_w * w[i, 0];

                for (int i = 0; i < length; i++)
                {
                    matrixVal = 0;
                    //matrixVal += this[i, k] * vect[k, 0];
                    for (int j = this.index_col[i]; j < this.index_col[i + 1]; j++)
                    {
                        matrixVal += this.valori[j] * vect[index_col[j], 0];
                    }
                    matrixVal += this.valori[i] * vect[i, 0];
                    w[i, 0] = matrixVal;
                }

                lambda = 0;

                for (int i = 0; i < length; i++)
                {
                    lambda = lambda + vect[i, 0] * w[i, 0];    
                    
                }
                
                ok = 0;     
                K++;
                Console.WriteLine(lambda);
            } while (norm_aux > (length * precision) && K <= 10000);
            
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
    }
}
