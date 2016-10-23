/********************************************************************************
** Form generated from reading UI file 'cn_hw6.ui'
**
** Created by: Qt User Interface Compiler version 5.6.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_CN_HW6_H
#define UI_CN_HW6_H

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

class Ui_CN_HW6
{
public:
    QWidget *centralWidget;
    QTextBrowser *textBrowser;
    QPushButton *loadMatrixA;
    QPushButton *compute;
    QLineEdit *sInput;
    QLabel *label;
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *CN_HW6)
    {
        if (CN_HW6->objectName().isEmpty())
            CN_HW6->setObjectName(QStringLiteral("CN_HW6"));
        CN_HW6->resize(1195, 668);
        centralWidget = new QWidget(CN_HW6);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        textBrowser = new QTextBrowser(centralWidget);
        textBrowser->setObjectName(QStringLiteral("textBrowser"));
        textBrowser->setGeometry(QRect(0, 0, 1201, 581));
        loadMatrixA = new QPushButton(centralWidget);
        loadMatrixA->setObjectName(QStringLiteral("loadMatrixA"));
        loadMatrixA->setGeometry(QRect(30, 580, 99, 27));
        compute = new QPushButton(centralWidget);
        compute->setObjectName(QStringLiteral("compute"));
        compute->setGeometry(QRect(370, 580, 99, 27));
        sInput = new QLineEdit(centralWidget);
        sInput->setObjectName(QStringLiteral("sInput"));
        sInput->setGeometry(QRect(250, 580, 113, 27));
        label = new QLabel(centralWidget);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(230, 580, 16, 17));
        CN_HW6->setCentralWidget(centralWidget);
        menuBar = new QMenuBar(CN_HW6);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 1195, 25));
        CN_HW6->setMenuBar(menuBar);
        mainToolBar = new QToolBar(CN_HW6);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        CN_HW6->addToolBar(Qt::TopToolBarArea, mainToolBar);
        statusBar = new QStatusBar(CN_HW6);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        CN_HW6->setStatusBar(statusBar);

        retranslateUi(CN_HW6);

        QMetaObject::connectSlotsByName(CN_HW6);
    } // setupUi

    void retranslateUi(QMainWindow *CN_HW6)
    {
        CN_HW6->setWindowTitle(QApplication::translate("CN_HW6", "CN_HW6", 0));
        loadMatrixA->setText(QApplication::translate("CN_HW6", "Matrix A", 0));
        compute->setText(QApplication::translate("CN_HW6", "Compute", 0));
        label->setText(QApplication::translate("CN_HW6", "s:", 0));
    } // retranslateUi

};

namespace Ui {
    class CN_HW6: public Ui_CN_HW6 {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_CN_HW6_H
