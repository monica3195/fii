#include "cn_hw1.h"
#include "ui_cn_hw1.h"
#include "hw1.h"

cn_hw1::cn_hw1(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::cn_hw1)
{
    ui->setupUi(this);
}

cn_hw1::~cn_hw1()
{
    delete ui;
}

void cn_hw1::on_computeTangenta_clicked()
{
    ui->textBrowserInfo->clear();
    QString stringDouble = ui->lineEditTangentaValue->text();
    double value = stringDouble.toDouble();
    //double tangentValue = mytan(value, EPS);
    //ui->lineEditTangentaValue->setText(QString::number(mytan(value, EPS)));
    ui->textBrowserInfo->append("My tangent : ");
    ui->textBrowserInfo->append(QString::number(mytan(value, EPS)));

    ui->textBrowserInfo->append("Tangent : ");
    ui->textBrowserInfo->append(QString::number(tan(value)));
}

void cn_hw1::on_push_button_1_clicked()
{
    ui->textBrowserInfo->clear();
    double u=1.0, up=1.0;
    int m=0;
    while (1.0+u!=1.0) {
       ++m;
       up=u;
       u/=10.0;
    }


    cout<<"M="<<m<<"\n";
    QString info = "Machine precision : ";
    info.append(QString::number(m));
    cout<<setprecision(16)<<fixed<<"U="<<up<<"\n";
    ui->textBrowserInfo->append(info);

    //ex 2
    double a=1.0;
    double b=up;
    double c=up;

    info.clear();
    info = " (a+b)+c != a+(b+c) -> ";

    if ( (a+b)+c != a+(b+c) ){
        cout<<"Adunare neasocoativa\n";
        info.append("True");
    }
    else{
        cout<<"Adunare asociativa\n";
        info.append("False");
    }
    ui->textBrowserInfo->append(info);

    a=10;
    b=up;
    c=up;

    info.clear();
    info = " (a+b)+c != a+(b+c) -> ";

    if ( (a*b)*c != a*(b*c) ){
        cout<<"Inmultire neasociativa\n";
        info.append("True");
    }
    else {
        cout<<"Inmultire asociativa\n";
        info.append("False");
    }
    ui->textBrowserInfo->append(info);
}
