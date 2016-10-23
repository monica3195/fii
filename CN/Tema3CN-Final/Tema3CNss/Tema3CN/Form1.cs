using MathNet.Numerics.LinearAlgebra.Double;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Tema3CN
{
    public partial class Form1 : Form
    {
        private Functions F;
        public Form1()
        {
            InitializeComponent();
            F = new Functions();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.textBox1.AppendText("The Array b:\n\r");
            this.textBox1.AppendText(F.computeArrayb(ref F.b,ref F.InitialB,F.A,F.n));

        }

        private void button2_Click(object sender, EventArgs e)
        {
            F.n = Int32.Parse(this.textBox2.Text);
            F.precision = Double.Parse(this.textBox3.Text);
            F.A = new DenseMatrix(F.n, F.n);
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                System.IO.StreamReader sr = new
                   System.IO.StreamReader(openFileDialog1.FileName);
                sr.Close();
                F.A = F.readMatrixFromFile(openFileDialog1.FileName, F.A);
                MessageBox.Show("Matrix was read succesfully");
            }
            F.InitialA = F.A;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            F.I = F.matrixI(F.I, F.n);
            Random rnd = new Random();
            F.fiftyN = rnd.Next(10) + 50;
            F.fiftyI = F.matrixI(F.fiftyI, F.fiftyN);
            this.F.fiftyA = new DenseMatrix(this.F.fiftyN, this.F.fiftyN);
            this.F.fiftyB = new DenseVector(this.F.fiftyN);

            for (int i = 0; i < this.F.fiftyN; i++)
            {
                for (int j = 0; j < this.F.fiftyN; j++)
                {
                    F.fiftyA[i, j] = rnd.NextDouble() * 1000;
                }
            }

            this.F.InitialFiftyA = F.fiftyA;
            this.F.computeArrayb(ref this.F.fiftyB,ref this.F.InitialFiftyB,this.F.fiftyA,this.F.fiftyN);


            F.fiftyQbar = F.fiftyI;
            this.textBox1.AppendText("\n\rThe A Matrix:\n\r");
            this.textBox1.AppendText(F.ToString(F.A));
            F.Householder(ref F.qbar,ref F.A,ref F.b,ref F.Q,F.I,F.n);
            this.textBox1.AppendText("\n\rThe R Matrix:\n\r");
            this.textBox1.AppendText(F.ToString(F.A));
            this.textBox1.AppendText("\n\rThe Q Matrix:\n\r");
            this.textBox1.AppendText(F.ToString(F.Q));

        }

        private void button4_Click(object sender, EventArgs e)
        {
            F.QRDecomposition();
            this.textBox1.AppendText("\n\rThe A Matrix:\n\r");
            this.textBox1.AppendText(F.ToString(F.InitialA) + "\n\r");
            this.textBox1.AppendText("\n\rThe R Matrix:\n\r");
            this.textBox1.AppendText("\n\r" + F.qr1.R.ToString() + "\n\r");
            this.textBox1.AppendText("\n\rThe Q Matrix:\n\r");
            this.textBox1.AppendText("\n\r" + F.qr1.Q.ToString() + "\n\r");
            this.textBox1.AppendText("\n\r \n\r Library QR \n\r" + F.x1.ToString() + "\n\r");
            this.textBox1.AppendText("\n\r \n\r My Qr \n\r" + F.x2.ToString() + "\n\r");

        }

        private void button5_Click(object sender, EventArgs e)
        {
            F.Ex3b();
            this.textBox1.AppendText("\n\r Asta este A initial: \n\r"+F.fiftyA.ToString() + "\n\r");
            this.textBox1.AppendText("\n\r Solutiiii: \n\r" + F.fiftyX1.ToString() + "\n\r \n\r" + F.fiftyX2.ToString() + "\n\r");
            this.textBox1.AppendText("\n\r Timpul diferenta este: " + (F.sw1.ElapsedMilliseconds) + " si " + (F.sw2.ElapsedMilliseconds) + "\n\r");
        }

        private void button6_Click(object sender, EventArgs e)
        {
            F.Norms();
            this.textBox1.AppendText("\n\r \n\r Norms: \n\r \n\r" + F.Norm1 + "\n\r \n\r" + F.Norm2 + "\n\r \n\r" + F.Norm3 + "\n\r \n\r" + F.Norm4 + "\n\r \n\r");
        }

        private void button7_Click(object sender, EventArgs e)
        {
            F.InverseNorma();
            this.textBox1.AppendText("\n\r\n\r The Inverse Norma is: " + F.InverseNormaa.ToString() + "\n\r");
        }
    }
}
