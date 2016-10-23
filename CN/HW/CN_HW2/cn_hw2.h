#ifndef CN_HW2_H
#define CN_HW2_H

#include <QMainWindow>
#include <matrice.h>

namespace Ui {
class CN_HW2;
}

class CN_HW2 : public QMainWindow
{
    Q_OBJECT

public:
    explicit CN_HW2(QWidget *parent = 0);
    ~CN_HW2();
    void ex3_4(int);

private slots:
    void on_calculateSol_clicked();

private:
    Ui::CN_HW2 *ui;
};

#endif // CN_HW2_H
