#ifndef CN_HW1_H
#define CN_HW1_H

#include <QMainWindow>

namespace Ui {
class cn_hw1;
}

class cn_hw1 : public QMainWindow
{
    Q_OBJECT

public:
    explicit cn_hw1(QWidget *parent = 0);
    ~cn_hw1();

private slots:
    void on_computeTangenta_clicked();

    void on_push_button_1_clicked();

private:
    Ui::cn_hw1 *ui;
};

#endif // CN_HW1_H
