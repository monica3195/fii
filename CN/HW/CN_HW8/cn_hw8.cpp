#include "cn_hw8.h"
#include "ui_cn_hw8.h"
#include <QFileDialog>
#include <QPainter>
#include <QPixmap>
#include <QLabel>
#include <QBrush>





CN_HW8::CN_HW8(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::CN_HW8)
{
    ui->setupUi(this);
}

CN_HW8::~CN_HW8()
{
    delete ui;
}

void CN_HW8::on_fileDialog_clicked()
{
    inputFile = QFileDialog::getOpenFileName(this, "Select inputFile");

}

double CN_HW8::c2_aproximation(double xb){
    double h[105];
    for (i=0; i<n; ++i) h[i]=x[i+1]-x[i];

    Matrice H(n-1,n-1,0);

    H.setV(1,1,2.0*(h[0]+h[1]));
    H.setV(1,2,h[1]);

    H.setV(n-1,n-2,h[n-2]);
    H.setV(n-1,n-1,2.0*(h[n-2]+h[n-1]));

    for (i=2; i<n-1; ++i) {

        H.setV(i,i-1,h[i-1]);
        H.setV(i,i,2.0*(h[i-1]+h[i]));
        H.setV(i,i+1,h[i]);

    }

    Matrice f(n-1,1,0);
    for (i=1; i<n; ++i) f.setV(i,1,6.0*( (y[i+1]-y[i])/h[i] - (y[i]-y[i-1])/h[i-1] ));

    Matrice A(n-1,1,0);

    Matrice::solveSystem(H,f,A,0.000001,n-1);

    double alfa[105], beta[105];

    for (i=0; i<n; ++i) {

        double ai, ai1;
        if (i==0) ai=0; else ai=A.getV(i,1);
        if (i==n-1) ai1=0; else ai1=A.getV(i+1,1);

        alfa[i]=(y[i+1]-y[i])/h[i] - h[i]*(ai1-ai)/6.0;
        beta[i]=(x[i+1]*y[i]-x[i]*y[i+1])/h[i] - h[i]*(x[i+1]*ai-x[i]*ai1)/6.0;

    }

    for (i=0; i<n; ++i)
        if (xb>=x[i] && xb<=x[i+1]) {
            double ai, ai1;
            if (i==0) ai=0; else ai=A.getV(i,1);
            if (i==n-1) ai1=0; else ai1=A.getV(i+1,1);
            static double spResult = 0;
            spResult = (xb-x[i])*(xb-x[i])*(xb-x[i])*ai1/(6.0*h[i]) + (x[i+1]-xb)*(x[i+1]-xb)*(x[i+1]-xb)*ai/(6.0*h[i])+xb*alfa[i]+beta[i];
            //cout<<(xb-x[i])*(xb-x[i])*(xb-x[i])*ai1/(6.0*h[i]) + (x[i+1]-xb)*(x[i+1]-xb)*(x[i+1]-xb)*ai/(6.0*h[i])+xb*alfa[i]+beta[i];
            return spResult;
            break;

        }
   return 0;

}

double CN_HW8::newton_aproximation(double xb)
{
    double h1=(x[n]-x[0])/(double)n;
    double t=(xb-x[0])/h1;
    double sk=1.0;
    double ln=y[0];

    for (int k=1; k<=n; ++k) {

        sk=sk*(t-double(k)+1.0)/(double)k;
        double aux=y[k-1];
        //calculez pasul i din schema lui Atiken si pastrez valorile in y
        for (i=k; i<=n; ++i) {
            double aux2=y[i];
            y[i]=y[i]-aux;
            aux=aux2;
        }

        ln+=sk*y[k];
    }

    for (i=0; i<=n; ++i) y[i]=ya[i];
    return ln;
}

void CN_HW8::on_calculate_clicked()
{
    if(inputFile.isEmpty()){
        ui->textBrowser->append("Chose input file first\n");
        return;
    }
    //metoda newton
    std::ifstream cin(inputFile.toLatin1().data());
    double xb=0;
    cin>>n;
    for (i=0; i<=n; ++i) { cin>>x[i]>>y[i]; ya[i]=y[i]; }
    cin>>xb;
    //aproximez valoarea polinomului in xb

    cout<<"----Metoda Newton----\n";
    ui->textBrowser->append("----Metoda Newton----\n");
    cout<<newton_aproximation(xb)<<"\n";
    ui->textBrowser->append(QString::number(newton_aproximation(xb)));
    ui->textBrowser->append("---------\n");
    cout<<"---------\n";

    //metoda functii splinice

    cout<<"\n\n----Metoda fuctii splinice C2----\n";
    ui->textBrowser->append("\n\n----Metoda fuctii splinice C2----\n");
    cout << c2_aproximation(xb);
    ui->textBrowser->append(QString::number(c2_aproximation(xb)));
    cout<<"\n-------\n";
    ui->textBrowser->append("\n-------\n");


    ui->drawGraphics->resize(500,500);
    QPixmap pixMap(500,500);
    QPixmap pixMap2(500,500);
    QPen qPen;
    QPainter qPainter(&pixMap);
    QPainter qPainter2(&pixMap2);

    qPen.setColor(Qt::red);
    qPainter.setPen(qPen);
    qPainter.fillRect(0,0,500,500,Qt::white);
    qPainter.drawLine(0,250,500,250);
    qPainter.drawLine(250,0,250,500);

    qPainter2.fillRect(0,0,500,500,Qt::white);
    qPainter2.drawLine(0,250,500,250);
    qPainter2.drawLine(250,0,250,500);

    qPen.setColor(Qt::blue);
    qPainter.setPen(qPen);

    //Newton
    double maxim = newton_aproximation(0.0);
    for(double i = 0; i < 5; i+=0.1){
        double yC = newton_aproximation(i);
        if(fabs(yC) > maxim) {maxim = fabs(yC);}


    }
    for(double i = 0; i< 5; i+=0.05){
        double yC = newton_aproximation(i);
        double yNext = newton_aproximation(i+0.1);
        std::cout << "Draw line [" << i << "," << yC << "][" << i+0.1 << " "<< yNext << "]\n";
        qPainter.drawLine(i*50+250,250-(yC/maxim)*250, 50*(i+0.1)+250, 250-(yNext/maxim)*250);
    }

    //C2
    qPen.setColor(Qt::green);
    qPainter2.setPen(qPen);
    maxim = c2_aproximation(0.0);
    for(double i = 0; i < 5; i+=0.1){
        double yC = c2_aproximation(i);
        if(fabs(yC) > maxim) {maxim = fabs(yC);}


    }
    for(double i = 0; i< 5; i+=0.05){
        double yC = c2_aproximation(i);
        double yNext = c2_aproximation(i+0.1);
        std::cout << "Draw line [" << i << "," << yC << "][" << i+0.1 << " "<< yNext << "]\n";
        qPainter2.drawLine(i*50+250,250-(yC/maxim)*250, 50*(i+0.1)+250, 250-(yNext/maxim)*250);
    }


    ui->drawGraphics->setPixmap(pixMap);
    ui->drawGraphics->show();

    ui->drawGraphics2->setPixmap(pixMap2);
    ui->drawGraphics2->show();
}
