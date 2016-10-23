#-------------------------------------------------
#
# Project created by QtCreator 2016-03-15T18:18:29
#
#-------------------------------------------------

QT       += core gui

CONFIG += c++14
QMAKE_CXXFLAGS += -std=c++14

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = CN_HW4
TEMPLATE = app


SOURCES += main.cpp\
        cn_hw4.cpp \
    sparsematrix.cpp

HEADERS  += cn_hw4.h \
    sparsematrix.h

FORMS    += cn_hw4.ui
