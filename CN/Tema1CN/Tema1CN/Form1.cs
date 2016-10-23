using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Tema1CN
{
    public partial class Form1 : Form
    {
        static double u, x, y, z;
        public Form1()
        {
            InitializeComponent();
        }

        private void EX1_Click(object sender, EventArgs e)
        {
            double m = 0;
            while (Math.Pow(10, -m) + 1 != 1)
            {
                m++;
            }
            m--;
            u = Math.Pow(10, -m);
            textBox1.Text = (u.ToString());
            textBox2.Text = ((m).ToString());
            EX2.Enabled = true;
        }

        private void EX2_Click(object sender, EventArgs e)
        {
            y = u;
            z = u;
            Random r = new Random();
            do
            {
                x = r.Next(-99999, 99999);
            }
            while ((x * y) * z == x * (y * z));
            textBox3.Text = ("Operatia de inmultire facuta de calculator este neasociativa! pentru x="+x);
            
          /*  if ((x + y) + z != x + (y + z))
            {
                textBox3.Text = ("Operatia de adunare facuta de calculator este neasociativa!");
            }
            else
            {
                textBox3.Text = ("Operatia de adunare facuta de calculator este asociativa!");
            }*/
        }

        private void EX3_Click(object sender, EventArgs e)
        {
            textBox4.ResetText();
            textBox5.ResetText();
            textBox6.ResetText();
            textBox7.ResetText();
            textBox8.ResetText();
            textBox9.ResetText();
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
            a = new int[n, n];
            b = new int[n, n];
            double logn = Math.Log(n, 2);
            A = new int[(int)Math.Ceiling(n / logn), n, (int)Math.Ceiling(logn)];
            B = new int[(int)Math.Ceiling(n / logn), (int)Math.Ceiling(logn), n];
            int r = 0, t = 0;//indecsi pt parcurgerea matricilor a si b
            int k = 1;//index pentru parcurgerea liniilor ctite din fisier
            for (int i = 0; i < n; i++)
            {
                string[] f = lines[k].Split(' ');
                k++;
                for (int j = 0; j < n; j++)
                {
                    a[i, j] = Int32.Parse(f[j]);
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
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    textBox4.Text += a[i, j].ToString()+" ";
                }
                textBox4.Text += "\r\n";

            }
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    textBox5.Text += b[i, j].ToString() + " ";
                }
                textBox5.Text += "\r\n";

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

                        t++;//indexul de linie din a
                    }
                    t = 0;
                    r++;//indexul de coloana din a

                }
            }
            for (int i = 0; i < (int)Math.Ceiling(n / logn); i++)
            {
                for (int q = 0; q < n; q++)
                {
                    for (int w = 0; w < (int)Math.Ceiling(logn); w++)
                    {
                        textBox6.Text += A[i, q, w].ToString()+" ";
                    }
                    textBox6.Text += "\r\n";
                }
                textBox6.Text += "\r\n";
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
                        textBox7.Text += B[i, q, w].ToString() + " ";
                    }
                    textBox7.Text += "\r\n";
                }
                textBox7.Text += "\r\n";
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
                        textBox8.Text += C[i, q, w].ToString() + " ";
                    }
                    textBox8.Text += "\r\n";
                }
                textBox8.Text += "\r\n";
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
                    textBox9.Text += c[q, w].ToString() + " ";
                }
                textBox9.Text += "\r\n";
            }
        }
    }
}
