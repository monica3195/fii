#ifndef CN_HW4_H
#define CN_HW4_H

#include <QMainWindow>
#include <QFileDialog>
#include <sparsematrix.h>

namespace Ui {
class CN_HW4;
}

class CN_HW4 : public QMainWindow
{
    Q_OBJECT

public:
    explicit CN_HW4(QWidget *parent = 0);
    ~CN_HW4();    

private slots:
    void on_pushButton_matrix1_clicked();
    void on_pushButton_matrix2_clicked();

    void on_sumMatrixFile_button_clicked();

    void on_prodMatrixFile_button_clicked();

    void on_pushButton_prod_clicked();

    void on_pushButton_sum_clicked();

    void on_pushButton_extraTest_clicked();

    void on_pushButton_fileButtonVecA_clicked();

private:
    QString matrixFile1, matrixFile2, sumMatrixFile, prodMatrixFile, vecAFile;
    Ui::CN_HW4 *ui;
    SparseMatrix *a, *b, *sumRef, *prodRef, *prodRes, *vecA;
    time_t start, end;

    void sum();
    void prod();

};

#endif // CN_HW4_H
