using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Tema4CN
{
    public partial class Form1 : Form
    {
        public Vector b=null;
        public Matrix A=null;
        public Matrix A2 = null;
        public double Precision = 0.000001f;
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                this.Precision = double.Parse(this.textBox1.Text);
                Functions.Load(openFileDialog1.FileName, out b, out A, out A2, Precision);
                MessageBox.Show("Matrix was read succesfully");
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Vector Xgs = Functions.SolveSOR(A, b, Precision);
            this.textBox2.Text +="\n\rThe Norm:\n\r"+ Functions.checkSolution(A, b, Xgs, Xgs.n);
        }

        private void button3_Click(object sender, EventArgs e)
        {
            this.textBox2.Text += "\n\r\n\r";
            this.textBox2.Text += "\n\rThe Fist 50 elements from the main diagonal(of Matrix A) before the multiplication\n\r:";
            Matrix rezultat = new Matrix();
            for (int i = 0; i < 50; i++)
                this.textBox2.Text += A.valori[i] + " ";
            this.textBox2.Text += "\n\r\n\r";
            this.textBox2.Text += "\n\rThe Fist 50 elements from the main diagonal(of Matrix A) after the multiplication\n\r:";
            this.textBox2.Text += "\n\r\n\r";
            rezultat = A.Multiply(A2, Precision);
            for (int i = 0; i < 50; i++)
                this.textBox2.Text += rezultat.valori[i] + " ";
            MessageBox.Show("Operation Succesfull");
        }
    }
}
