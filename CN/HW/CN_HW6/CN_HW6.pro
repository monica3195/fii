#-------------------------------------------------
#
# Project created by QtCreator 2016-03-29T21:36:46
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = CN_HW6
TEMPLATE = app


SOURCES += main.cpp\
        cn_hw6.cpp \
    sparsematrix.cpp

HEADERS  += cn_hw6.h \
    sparsematrix.h

FORMS    += cn_hw6.ui

LIBS += -llapack -lblas -larmadillo
