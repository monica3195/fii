using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Tema2CN;

namespace Tema2CN
{
    public partial class Form1 : Form
    {
        private Matrix matrix;
        private Array array;
        private Sistem sistem;
        private double precision;

        public Form1()
        {
            InitializeComponent();
            matrix = new Matrix();
            array = new Array();
            sistem = new Sistem();

        }

        private void button1_Click(object sender, System.EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                System.IO.StreamReader sr = new
                   System.IO.StreamReader(openFileDialog1.FileName);
                sr.Close();
                matrix.readFromFile(openFileDialog1.FileName);
                MessageBox.Show("Matrix was read succesfully");
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                System.IO.StreamReader sr = new
                   System.IO.StreamReader(openFileDialog1.FileName);
                sr.Close();
                array.readFromFile(openFileDialog1.FileName);
                MessageBox.Show("Array was read succesfully");        
            }
        }

        private void button3_Click(object sender, EventArgs e)//calculeazaa solutia
        {
            if (!double.TryParse(this.textBox2.Text, out precision))
            {
                MessageBox.Show("Introduceti un double valid!","Eroare");
                return;
            }
            this.textBox1.ResetText();
            this.textBox1.AppendText("\r\nMatrix A :" + matrix.ToString());
            this.textBox1.AppendText("\r\nArray b :" + array.ToString());
            this.textBox1.AppendText("\r\nPrecision :" + this.precision.ToString());
            sistem = new Sistem(array.length, matrix, array, precision);
            sistem.GaussAlgorythm();
            this.textBox1.AppendText("\r\nUpper triangular Matrix:" + sistem.Aux.ToString());
            this.textBox1.AppendText("\r\nSolution:" + sistem.X.ToString());

        }

        private void button4_Click(object sender, EventArgs e)
        {
            this.textBox1.AppendText(sistem.checkSolution());
        }
        public void showMessageBox()
        {
            MessageBox.Show("Division Error");
        }

    }
}
