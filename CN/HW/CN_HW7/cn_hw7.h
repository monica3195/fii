#ifndef CN_HW7_H
#define CN_HW7_H

#include <QMainWindow>
#include<iostream>
#include<fstream>
#include<complex.h>
#include<cmath>
#include<stdlib.h>
#include<time.h>
#include<iomanip>
using namespace std;

namespace Ui {
class CN_HW7;
}

class CN_HW7 : public QMainWindow
{
    Q_OBJECT

public:
    explicit CN_HW7(QWidget *parent = 0);
    ~CN_HW7();

private slots:
    void on_InputFile_clicked();

    void on_Calculate_clicked();

private:
    //UI
    Ui::CN_HW7 *ui;
    QString inputFile;

    /// HW Datas
    int n,i;
    double p[20];
    double sol[20];

    /// Functions
    double f(double v){
        double b0=p[0];
            for (int i=1; i<=n; ++i) b0=p[i]+b0*v;
            return b0;
    }
    /*-----------------------------------------------------------------------*/
    double random_float(double l, double r) {
        double d=rand()%10000;
        return l+d*(r-l)/10000.0;
    }
    /*-----------------------------------------------------------------------*/
    double F(double x) {

        //return x*x-4.0*x+3.0;
         return x*x+exp(x);

    }
    /*-----------------------------------------------------------------------*/
    double g1(double x, double h) {
       return (3.0*F(x)-4.0*F(x-h)+F(x-2.0*h))/2.0*h;
    }
    /*-----------------------------------------------------------------------*/
    double g2(double x, double h) {
       return ( -F(x+2.0*h)+8.0*F(x+h)-8.0*F(x-h)+F(x-2*h) )/(12.0*h) ;
    }
    /*-----------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------*/
};

#endif // CN_HW7_H
