#-------------------------------------------------
#
# Project created by QtCreator 2016-02-28T20:27:29
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = CN_HW2
TEMPLATE = app


SOURCES += main.cpp\
        cn_hw2.cpp \
    matrice.cpp

HEADERS  += cn_hw2.h \
    matrice.h

FORMS    += cn_hw2.ui

LIBS += -llapack -lblas -larmadillo
