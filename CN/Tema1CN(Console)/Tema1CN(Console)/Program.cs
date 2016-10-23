using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tema1CN_Console_
{
    class Program
    {
        static double u, x, y, z;
        static void Main(string[] args)
        {
            //EX1
            double m = 0;
            while (Math.Pow(10,-m)+1 != 1)
            {
                m++;
            }
            m--;
            u = Math.Pow(10, -m);
            Console.WriteLine("Numarul cu proprietatrea ceruta:{0},Prcezia:{1}",Math.Pow(10, -m),m);
            //EX2
            y = u;
            z = u;
            x = 1.0;
            if ((x + y) + z != x + (y + z))
            {
                Console.WriteLine("Operatia de adunare facuta de calculator este neasociativa!");
            }
            //EX3
            int[,] a;
            int[,] b;
            int[, ,] A;
            int[, ,] B;
            int[, ,] C;
            int[,] c;
            int[] o;
            int n;
            string[] lines = System.IO.File.ReadAllLines("Matrix.txt");
            n = Int32.Parse(lines[0]);
            a=new int[n,n];
            b = new int[n, n];
            for (int i = 0;i< lines.Length; i++)
            {
                Console.WriteLine(lines[i]);
            }
            double logn = Math.Log(n, 2);
            A = new int[(int)Math.Ceiling(n/logn), n, (int)Math.Ceiling(logn)];
            B = new int[(int)Math.Ceiling(n / logn), (int)Math.Ceiling(logn), n];
            int r = 0, t = 0;//indecsi pt parcurgerea matricilor a si b
            int k = 1, R=0,RR=0;
            for (int i = 0; i < n; i++)
            {
               string[]f = lines[k].Split(' ');
               k++;
               for (int j = 0; j < n; j++)
               {
                   a[i,j] = Int32.Parse(f[j]);
               }
            }
            //cand ies din primul for k o sa fie pe pozitia goala dintre cele doua matrici care este in fisier!
            k++;
            for (int i = 0; i < n; i++)
            {
                string[] f = lines[k].Split(' ');
                k++;
                for (int j = 0; j < n; j++)
                {
                    b[i, j] = Int32.Parse(f[j]);
                }
            }
            for (int i = 0; i < (int)Math.Ceiling(n / logn); i++)
            {

                for (int q = 0; q < (int)Math.Ceiling(logn); q++) //coloanele
                {

                    for (int w = 0; w < n; w++)//liniile
                    {
                        if (r < n)
                        {
                            A[i, w, q] = a[t, r];
                        }
                        else
                        {
                            A[i, w, q] = 0;
                        }

                        t++;
                    }
                    t = 0;
                    r++;
                    
                }
            }
            for (int i = 0; i < (int)Math.Ceiling(n / logn); i++)
            {
                for (int q = 0; q < n; q++)
                {
                    for (int w = 0; w < (int)Math.Ceiling(logn); w++)
                    {
                        Console.Write(A[i, q, w]);
                    }
                    Console.WriteLine();
                }
                Console.WriteLine();
            }
            t = 0;
            r = 0;
            for (int i = 0; i < (int)Math.Ceiling(n / logn); i++)
            {

                for (int q = 0; q < (int)Math.Ceiling(logn); q++)//liniile
                {

                    for (int w = 0; w < n; w++)//coloanele
                    {
                        if (t < n)
                        {
                            B[i, q, w] = b[t, r];
                        }
                        else
                        {
                            B[i, q, w] = 0;
                        }

                        r++;
                    }
                    t++;
                    r = 0;
                }
                r = 0;
            }
            for (int i = 0; i < (int)Math.Ceiling(n / logn); i++)
            {
                for (int q = 0; q < (int)Math.Ceiling(logn); q++)
                {
                    for (int w = 0; w < n; w++)
                    {
                        Console.Write(B[i, q, w]);
                    }
                    Console.WriteLine();
                }
                Console.WriteLine();
            }
            o = new int[n];
            C = new int[(int)Math.Ceiling(n / logn), n, n];
            for (int i = 0; i < (int)Math.Ceiling(n / logn); i++)
            {
                for (int j = 0; j < n; j++)//liniile din A
                {
                    for (int h = 0; h < (int)Math.Ceiling(logn); h++)//coloanele din A
                    {
                        if (A[i, j, h] == 1)
                        {
                            for (int q = 0; q < n; q++)
                                o[q] = o[q] | B[i, h, q];
                        }
                    }
                    for (int h = 0; h < n; h++)
                    {
                        C[i, j, h] = o[h];
                        o[h] = 0;
                    }
                }
            }
            for (int i = 0; i < (int)Math.Ceiling(n / logn); i++)
            {
                for (int q = 0; q < n; q++)
                {
                    for (int w = 0; w < n; w++)
                    {
                        Console.Write(C[i, q, w]);
                    }
                    Console.WriteLine();
                }
                Console.WriteLine();
            }
            c = new int[n, n];
            for (int i = 0; i < (int)Math.Ceiling(n / logn); i++)
            {
                for (int q = 0; q < n; q++)
                {
                    for (int w = 0; w < n; w++)
                    {
                        c[q, w] = c[q, w] | C[i, q, w];
                    }
                }
            }
            for (int q = 0; q < n; q++)
            {
                for (int w = 0; w < n; w++)
                {
                    Console.Write(c[q, w]);
                }
                Console.WriteLine();
            }
        }
    }
}
