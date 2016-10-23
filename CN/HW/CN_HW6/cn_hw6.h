#ifndef CN_HW6_H
#define CN_HW6_H

#include <QMainWindow>
#include <sparsematrix.h>

namespace Ui {
class CN_HW6;
}

class CN_HW6 : public QMainWindow
{
    Q_OBJECT

public:
    explicit CN_HW6(QWidget *parent = 0);
    ~CN_HW6();

private slots:
    void on_loadMatrixA_clicked();

    void on_compute_clicked();

private:
    Ui::CN_HW6 *ui;
    QString matrixFileA;
    SparseMatrix A;
};

#endif // CN_HW6_H
