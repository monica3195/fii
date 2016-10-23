#ifndef CN_HW8_H
#define CN_HW8_H

#include <QMainWindow>
#include <iostream>
#include <fstream>
#include <matrice.h>
using namespace std;

namespace Ui {
class CN_HW8;
}

class CN_HW8 : public QMainWindow
{
    Q_OBJECT

public:
    explicit CN_HW8(QWidget *parent = 0);
    ~CN_HW8();

private slots:
    void on_fileDialog_clicked();

    double newton_aproximation(double x);
    double c2_aproximation(double x);

    void on_calculate_clicked();

private:
    Ui::CN_HW8 *ui;
    QString inputFile;
    double x[105], y[105], ya[105];
    int n,i;
    double xb=0;
};

#endif // CN_HW8_H
