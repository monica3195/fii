using MathNet.Numerics.LinearAlgebra.Double;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Tema5CN
{
    public partial class Form1 : Form
    {
        Functions F = new Functions();
        [DllImport("kernel32.dll", SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        static extern bool AllocConsole();
        public Form1()
        {
            InitializeComponent();
            AllocConsole();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            F.precision = Double.Parse(this.textBox3.Text);
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                System.IO.StreamReader sr = new
                   System.IO.StreamReader(openFileDialog1.FileName);
                sr.Close();
                F.A = F.readMatrixFromFile(openFileDialog1.FileName, F.A,ref F.p,ref F.n);
                MessageBox.Show("Matrix was read succesfully");
                this.textBox1.Text += "The Matrix A:\n\r"+F.ToString(F.A);
                
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            F.SingularValueDecomposition(F.A);
            this.textBox1.Text += "The Singular Values:\n\r" + F.ToString(F.singularValues);
            this.textBox1.Text += "\n\rThe Matrix Rank:\n\r" + F.Rank.ToString() + "\n\r";
            this.textBox1.Text += "\n\rThe Condition Number:\n\r" + F.ConditionNumber.ToString() + "\n\r";
            this.textBox1.Text += "\n\rThe Norm:\n\r" + F.norm.ToString() + "\n\r";
            this.textBox1.Text += "\n\rThe Solution:\n\r" + F.ToString(F.XI) + "\n\r";
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                System.IO.StreamReader sr = new
                   System.IO.StreamReader(openFileDialog1.FileName);
                sr.Close();
                F.b = F.readVectorFromFile(openFileDialog1.FileName, F.b);
                MessageBox.Show("Vector was read succesfully");
                this.textBox1.Text += "\n\rThe Vector b:\n\r" + F.ToString(F.b)+"\n\r";

            }
        }

        private void button4_Click(object sender, EventArgs e)
        {
            int n = 5001;
            F.P = new Matrix(n);
            MessageBox.Show("Matrix Generated");
        }

        private void button5_Click(object sender, EventArgs e)
        {
            F.P.powerMethod(5001, F.precision);
            MessageBox.Show("Method execution was succesfull");
        }

      
    }
}
