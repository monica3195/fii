/********************************************************************************
** Form generated from reading UI file 'cn_hw5.ui'
**
** Created by: Qt User Interface Compiler version 5.6.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_CN_HW5_H
#define UI_CN_HW5_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QTextBrowser>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_CN_HW5
{
public:
    QWidget *centralWidget;
    QTextBrowser *textBrowser;
    QPushButton *pushButton;
    QPushButton *pushButton_2;
    QPushButton *pushButton_3;
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *CN_HW5)
    {
        if (CN_HW5->objectName().isEmpty())
            CN_HW5->setObjectName(QStringLiteral("CN_HW5"));
        CN_HW5->resize(1093, 658);
        centralWidget = new QWidget(CN_HW5);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        textBrowser = new QTextBrowser(centralWidget);
        textBrowser->setObjectName(QStringLiteral("textBrowser"));
        textBrowser->setGeometry(QRect(0, 0, 1091, 541));
        pushButton = new QPushButton(centralWidget);
        pushButton->setObjectName(QStringLiteral("pushButton"));
        pushButton->setGeometry(QRect(10, 550, 99, 27));
        pushButton_2 = new QPushButton(centralWidget);
        pushButton_2->setObjectName(QStringLiteral("pushButton_2"));
        pushButton_2->setGeometry(QRect(140, 550, 99, 27));
        pushButton_3 = new QPushButton(centralWidget);
        pushButton_3->setObjectName(QStringLiteral("pushButton_3"));
        pushButton_3->setGeometry(QRect(270, 550, 99, 27));
        CN_HW5->setCentralWidget(centralWidget);
        menuBar = new QMenuBar(CN_HW5);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 1093, 25));
        CN_HW5->setMenuBar(menuBar);
        mainToolBar = new QToolBar(CN_HW5);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        CN_HW5->addToolBar(Qt::TopToolBarArea, mainToolBar);
        statusBar = new QStatusBar(CN_HW5);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        CN_HW5->setStatusBar(statusBar);

        retranslateUi(CN_HW5);

        QMetaObject::connectSlotsByName(CN_HW5);
    } // setupUi

    void retranslateUi(QMainWindow *CN_HW5)
    {
        CN_HW5->setWindowTitle(QApplication::translate("CN_HW5", "CN_HW5", 0));
        pushButton->setText(QApplication::translate("CN_HW5", "Matrix A", 0));
        pushButton_2->setText(QApplication::translate("CN_HW5", "vec B", 0));
        pushButton_3->setText(QApplication::translate("CN_HW5", "Compute", 0));
    } // retranslateUi

};

namespace Ui {
    class CN_HW5: public Ui_CN_HW5 {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_CN_HW5_H
