#-------------------------------------------------
#
# Project created by QtCreator 2016-03-29T20:44:25
#
#-------------------------------------------------

QT       += core gui

CONFIG += c++14
QMAKE_CXXFLAGS += -std=c++14

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = CN_HW5
TEMPLATE = app


SOURCES += main.cpp\
        cn_hw5.cpp \
    sparsematrix.cpp

HEADERS  += cn_hw5.h \
    sparsematrix.h

FORMS    += cn_hw5.ui
