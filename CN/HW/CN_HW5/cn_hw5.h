#ifndef CN_HW5_H
#define CN_HW5_H

#include <QMainWindow>
#include <QString>
#include <sparsematrix.h>

namespace Ui {
class CN_HW5;
}

class CN_HW5 : public QMainWindow
{
    Q_OBJECT

private:
    QString matrixFileA, vectorFileB;

public:
    explicit CN_HW5(QWidget *parent = 0);
    ~CN_HW5();

private slots:
    void on_pushButton_clicked();

    void on_pushButton_2_clicked();

    void on_pushButton_3_clicked();

private:
    SparseMatrix A, b;
    Ui::CN_HW5 *ui;
};

#endif // CN_HW5_H
