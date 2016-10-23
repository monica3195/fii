#include "cn_hw6.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    srand(time(NULL));
    QApplication a(argc, argv);
    CN_HW6 w;
    w.show();

    return a.exec();
}
