/****************************************************************************
** Meta object code from reading C++ file 'cn_hw6.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.6.0)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../CN_HW6/cn_hw6.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'cn_hw6.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.6.0. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
struct qt_meta_stringdata_CN_HW6_t {
    QByteArrayData data[4];
    char stringdata0[50];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_CN_HW6_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_CN_HW6_t qt_meta_stringdata_CN_HW6 = {
    {
QT_MOC_LITERAL(0, 0, 6), // "CN_HW6"
QT_MOC_LITERAL(1, 7, 22), // "on_loadMatrixA_clicked"
QT_MOC_LITERAL(2, 30, 0), // ""
QT_MOC_LITERAL(3, 31, 18) // "on_compute_clicked"

    },
    "CN_HW6\0on_loadMatrixA_clicked\0\0"
    "on_compute_clicked"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_CN_HW6[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       2,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

 // slots: name, argc, parameters, tag, flags
       1,    0,   24,    2, 0x08 /* Private */,
       3,    0,   25,    2, 0x08 /* Private */,

 // slots: parameters
    QMetaType::Void,
    QMetaType::Void,

       0        // eod
};

void CN_HW6::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        CN_HW6 *_t = static_cast<CN_HW6 *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->on_loadMatrixA_clicked(); break;
        case 1: _t->on_compute_clicked(); break;
        default: ;
        }
    }
    Q_UNUSED(_a);
}

const QMetaObject CN_HW6::staticMetaObject = {
    { &QMainWindow::staticMetaObject, qt_meta_stringdata_CN_HW6.data,
      qt_meta_data_CN_HW6,  qt_static_metacall, Q_NULLPTR, Q_NULLPTR}
};


const QMetaObject *CN_HW6::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *CN_HW6::qt_metacast(const char *_clname)
{
    if (!_clname) return Q_NULLPTR;
    if (!strcmp(_clname, qt_meta_stringdata_CN_HW6.stringdata0))
        return static_cast<void*>(const_cast< CN_HW6*>(this));
    return QMainWindow::qt_metacast(_clname);
}

int CN_HW6::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QMainWindow::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 2)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 2;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 2)
            *reinterpret_cast<int*>(_a[0]) = -1;
        _id -= 2;
    }
    return _id;
}
QT_END_MOC_NAMESPACE
