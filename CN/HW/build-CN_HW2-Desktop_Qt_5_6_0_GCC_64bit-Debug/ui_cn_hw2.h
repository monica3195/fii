/********************************************************************************
** Form generated from reading UI file 'cn_hw2.ui'
**
** Created by: Qt User Interface Compiler version 5.6.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_CN_HW2_H
#define UI_CN_HW2_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QTextBrowser>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_CN_HW2
{
public:
    QWidget *centralWidget;
    QLineEdit *matrixN;
    QTextBrowser *textBrowser;
    QPushButton *calculateSol;
    QLabel *label;
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *CN_HW2)
    {
        if (CN_HW2->objectName().isEmpty())
            CN_HW2->setObjectName(QStringLiteral("CN_HW2"));
        CN_HW2->resize(987, 582);
        centralWidget = new QWidget(CN_HW2);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        matrixN = new QLineEdit(centralWidget);
        matrixN->setObjectName(QStringLiteral("matrixN"));
        matrixN->setGeometry(QRect(140, 490, 113, 27));
        textBrowser = new QTextBrowser(centralWidget);
        textBrowser->setObjectName(QStringLiteral("textBrowser"));
        textBrowser->setGeometry(QRect(0, 0, 981, 481));
        calculateSol = new QPushButton(centralWidget);
        calculateSol->setObjectName(QStringLiteral("calculateSol"));
        calculateSol->setGeometry(QRect(260, 490, 99, 27));
        label = new QLabel(centralWidget);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(10, 490, 111, 17));
        CN_HW2->setCentralWidget(centralWidget);
        menuBar = new QMenuBar(CN_HW2);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 987, 25));
        CN_HW2->setMenuBar(menuBar);
        mainToolBar = new QToolBar(CN_HW2);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        CN_HW2->addToolBar(Qt::TopToolBarArea, mainToolBar);
        statusBar = new QStatusBar(CN_HW2);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        CN_HW2->setStatusBar(statusBar);

        retranslateUi(CN_HW2);

        QMetaObject::connectSlotsByName(CN_HW2);
    } // setupUi

    void retranslateUi(QMainWindow *CN_HW2)
    {
        CN_HW2->setWindowTitle(QApplication::translate("CN_HW2", "CN_HW2", 0));
        calculateSol->setText(QApplication::translate("CN_HW2", "Calculate", 0));
        label->setText(QApplication::translate("CN_HW2", "Matrix lines :", 0));
    } // retranslateUi

};

namespace Ui {
    class CN_HW2: public Ui_CN_HW2 {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_CN_HW2_H
