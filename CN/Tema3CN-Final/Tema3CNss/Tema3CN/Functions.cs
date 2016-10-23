using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MathNet.Numerics;
using MathNet.Numerics.LinearAlgebra.Double;
using System.IO;
using MathNet.Numerics.LinearAlgebra.Double.Factorization;
//using ILNumerics;
using System.Diagnostics;

namespace Tema3CN
{
    class Functions
    {
        public double precision;
        public int n;
        public DenseMatrix A;
        public DenseMatrix InitialA;
        public int fiftyN;
        public DenseMatrix fiftyA;
        public DenseMatrix InitialFiftyA;
        public DenseMatrix qbar;
        public DenseVector fiftyB;
        public DenseVector InitialFiftyB;
        public DenseVector fiftyX1;
        public DenseVector fiftyX2;
        public DenseVector InitialB;
        public DenseVector b;//pt EX1
        public DenseMatrix I;
        public DenseMatrix fiftyI;
        public DenseMatrix fiftyQbar;
        public DenseMatrix Pr;
        public DenseMatrix fiftyPr;
        public DenseMatrix Q;
        public DenseMatrix fiftyQ;
        public DenseVector x1; //ex3
        public DenseVector x2;
        public DenseMatrix AInverse;
        public DenseMatrix AInversebibl;
        public double Norm1, Norm2, Norm3, Norm4;
        public DenseVector S;
        public double InverseNormaa;

        public DenseMatrix readMatrixFromFile(string path, DenseMatrix M)
        {
            FileStream fileStream = new FileStream(path, FileMode.Open);
            StreamReader stream = new StreamReader(fileStream);
            String input = "";
            try
            {
                input = stream.ReadLine();
                int size = Int32.Parse(input);
                // this.linNumber = int.Parse(input);
                //input = stream.ReadLine();
                //this.colNumber = int.Parse(input);
                //this.matrix = new double[this.linNumber, this.colNumber];
                M = new DenseMatrix(size, size);
                for (int i = 0; i < size; i++)
                {
                    input = stream.ReadLine();
                    string[] fields = input.Split(' ');
                    for (int j = 0; j < size; j++)
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
        public string ToString(DenseMatrix A)
        {
            StringBuilder mat = new StringBuilder();
            mat.Append("\r\n");
            for (int i = 0; i < A.RowCount; i++)
            {
                for (int j = 0; j < A.ColumnCount; j++)
                    if(A[i,j] > precision || -A[i,j] > precision)
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
        public string computeArrayb(ref DenseVector B ,ref DenseVector IntialB,DenseMatrix A,int n)//Ex1
        {
            B = new DenseVector(n);
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    B[i] += (j) * A[i, j];
                }
                B[i] = B[i] / 3;
            }
            IntialB = B;
            return ToString(B);
        }
        public DenseMatrix matrixI(DenseMatrix m, int size)
        {
            m = new DenseMatrix(size, size);
            for (int i = 0; i < size; i++)
            {
                m[i, i] = 1;
            }
            return m;
        }

        public void Householder(ref DenseMatrix qbar,ref DenseMatrix A,ref DenseVector B,ref DenseMatrix Q,DenseMatrix I,int n)
        {
            qbar = I;
            double sigma = 0;
            double k = 0;
            double beta = 0;
            for (int r = 0; r < n - 1; r++)
            {
                DenseMatrix u = new DenseMatrix(n, 1);
                sigma = 0;
                k = 0;
                beta = 0;
                for (int i = r; i < n; i++)
                {
                    sigma += Math.Pow(A[i, r], 2);
                }
                if (sigma <= this.precision)
                {
                    break;
                    //A singulara(hailei)
                }
                if (A[r, r] >= 0)
                    k = -Math.Sqrt(sigma);
                else
                    k = Math.Sqrt(sigma);

                beta = sigma - k * A[r, r];

                for (int i = 0; i < r; i++)
                {
                    u[i, 0] = 0;
                }
                u[r, 0] = A[r, r] - k;
                for (int i = r + 1; i < n; i++)
                {
                    u[i, 0] = A[i, r];
                }


                Pr = new DenseMatrix(n, n);
                DenseMatrix uTransposed = new DenseMatrix(u.Transpose());
                Pr = I - (1 / beta) * (u * uTransposed);
                 A = Pr * A;
                B = Pr * B;
                qbar = Pr * qbar;
                Q = new DenseMatrix(qbar.Transpose());
            }
        }
      

        //-----------------------------------EX3---------------
        public QR qr1, qr2;
        public void QRDecomposition()
        {
            x1 = new DenseVector(this.n);
            qr1 = InitialA.QR();
            this.x1 = (DenseVector)qr1.Solve(this.b);

            x2 = new DenseVector(this.n);
            DenseMatrix inversaR = new DenseMatrix(A.Inverse());
            x2 = inversaR * this.qbar * this.b;
        }

        public Stopwatch sw1;
        public Stopwatch sw2;

        public void Ex3b()
        {
            this.InitialFiftyA = fiftyA;

            sw1 = new Stopwatch();
            sw1.Start();
            Householder(ref fiftyQbar, ref fiftyA, ref fiftyB, ref fiftyQ, fiftyI, fiftyN);
            fiftyX1 = new DenseVector(this.fiftyN);
            DenseMatrix fiftyR = new DenseMatrix(fiftyA.Inverse());
         //   fiftyX1 = fiftyR * this.fiftyQbar * this.fiftyB;
            double sum = 0;
            for (int i = this.fiftyN - 1; i >= 0; i--)
            {
                sum = 0;
                for (int t = i + 1; t < this.fiftyN; t++)
                {
                    sum += this.fiftyA[i, t] * fiftyX1[t];
                }
                fiftyX1[i] = (fiftyB[i] - sum) / this.fiftyA[i, i];
            }
            sw1.Stop();


            sw2 = new Stopwatch();
            sw2.Start();
            fiftyX2 = new DenseVector(this.fiftyN);
            qr2 = InitialFiftyA.QR();
            this.fiftyX2 = (DenseVector)qr2.Solve(this.InitialFiftyB);
            sw2.Stop();


        }

        //---------------------------------------------- EX 4----------------------------------

        public double computeNormAXB(DenseMatrix MAT, DenseVector SOL, DenseVector VEC, int n)
        {
            DenseVector aux = new DenseVector(n);
            double sum = 0;
            for (int i = 0; i < n; i++)
            {
                sum = 0;
                for (int j = 0; j < n; j++)
                    sum += MAT[i, j] * SOL[j];
                aux[i] = sum - VEC[i];
            }

            double norm = 0;
            for (int i = 0; i < n; i++)
                norm += Math.Pow(Math.Abs(aux[i]), 2);
            return Math.Sqrt(norm);
        }

        public double computeNormXS(DenseVector X, DenseVector S, int n)
        {
            DenseVector aux = new DenseVector(n);
            aux = X - S;
            double norm = 0;
            for(int i=0;i<n;i++)
                norm += Math.Pow(Math.Abs(aux[i]), 2);
            return Math.Sqrt(norm);
        }

        public double computeNormS(DenseVector S, int n)
        {
            double norm = 0;
            for(int i=0;i<n;i++)
                norm += Math.Pow(Math.Abs(S[i]), 2);
            return Math.Sqrt(norm);
        }

        public void Norms()
        {
            Norm1 = computeNormAXB(this.InitialFiftyA, this.fiftyX1, this.InitialFiftyB,this.fiftyN); //Cu Xhouse
            Norm2 = computeNormAXB(this.InitialFiftyA, this.fiftyX2, this.InitialFiftyB,this.fiftyN); //CU Xqr
            S = new DenseVector(this.fiftyN);
            for (int i = 0; i < this.fiftyN; i++)
                S[i] = i / 3.0;
            Norm3 = computeNormXS(this.fiftyX2, S,this.fiftyN) / computeNormS(S, this.fiftyN);
            Norm4 = computeNormXS(this.fiftyX1, S, this.fiftyN) / computeNormS(S, this.fiftyN);

        }

        //-------------------------------------------------------Ex5 -------------------------------------------

        public DenseMatrix Inverse()
        {
            DenseMatrix Qt = new DenseMatrix(this.Q.Transpose());
            DenseVector b = new DenseVector(this.n);
            DenseVector sol = new DenseVector(this.n);
            DenseMatrix AInverse = new DenseMatrix(this.n);
            double sum = 0;
            for (int j = 0; j < this.n; j++)
            {
                sum = 0;
                for (int x = 0; x < this.n; x++)
                {
                    b[x] = Qt[x, j];
                }
                for (int i = this.n-1; i >= 0; i--)
                {
                    for (int t = i+1; t < this.n; t++)
                    {
                        sum += this.A[i, t] * sol[t];
                    }
                    sol[i] = (b[i] - sum) / this.A[i, i];
                }
                for (int i = 0; i < this.n; i++)
                {
                    AInverse[i, j] = sol[i];
                }
            }
            return AInverse;
        }

        public void InverseNorma()
        {
            this.AInverse = Inverse();
            this.AInversebibl = new DenseMatrix(this.InitialA.Inverse());
            DenseMatrix sub = this.AInverse - this.AInversebibl;
            double max = -100000000000000;
            for (int i = 0; i < this.n; i++)
            {
                for (int j = 0; j < this.n; j++)
                {
                   if(sub[i,j] < 0)
                       if(-sub[i,j] > max)
                           max = sub[i, j];
                   if (sub[i, j] > 0)
                       if (sub[i, j] > max)
                           max = sub[i, j];

                }
            }
            this.InverseNormaa = max;
        }

    }
}
