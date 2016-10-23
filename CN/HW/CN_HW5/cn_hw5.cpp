#include "cn_hw5.h"
#include "ui_cn_hw5.h"
#include <QFileDialog>

CN_HW5::CN_HW5(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::CN_HW5)
{
    ui->setupUi(this);
}

CN_HW5::~CN_HW5()
{
    delete ui;
}

void CN_HW5::on_pushButton_clicked()
{
    matrixFileA = QFileDialog::getOpenFileName(this, "Select matrix file A", ".", "Text files (*.txt);; All files (*.*)");
    ifstream cinA(matrixFileA.toLatin1().data());
    A = SparseMatrix(cinA, 0);
}

void CN_HW5::on_pushButton_2_clicked()
{
    vectorFileB = QFileDialog::getOpenFileName(this, "Select vector file B", ".", "Text files (*.txt);; All files (*.*)");
    ifstream cinb(vectorFileB.toLatin1().data());
    b = SparseMatrix(cinb, 1);
}

void CN_HW5::on_pushButton_3_clicked()
{
    if (A.verifyDiagonal(0.000000001)) {
        cout<<"Diagonal is ok\n";
        ui->textBrowser->append("Diagonal is ok\n");
    }else {
        cout<<"Matrix A has NULL elements on main diagonal\n";
        ui->textBrowser->append("Matrix A has NULL elements on main diagonal\n");
    }

    cout<<"SOR Method:\n";
    ui->textBrowser->append("SOR Method:");


    //Initializing X_SOR
    SparseMatrix X_SOR;
    X_SOR.allocEmpty(b.getN());

    for (int i=1; i<=X_SOR.getN(); ++i) {
        lista v=new celula;
        v->col=1; v->val=0;
        X_SOR.insertEl(i,v);
    }

    int maxk=10000, k=0;
    double delta=1000000000, eps=0.00000001;

    do {

        ++k;
        delta=0;

        for (int i=1; i<=X_SOR.getN(); ++i) {
            double newValue=b.getLine(i)->val;
            double coef=1.2;

            for (lista p=A.getLine(i); p!=NULL; p=p->next)
                if (p->col==i) coef/=p->val;
                else newValue-=X_SOR.getLine(p->col)->val*p->val;

            newValue*=coef;
            newValue-=0.2*X_SOR.getLine(i)->val;

            double diff=newValue-X_SOR.getLine(i)->val;
            delta+=diff*diff;

            X_SOR.getLine(i)->val=newValue;
        }

        delta=sqrt(delta);

    } while (delta>eps && delta<1000000000 && k<maxk);

    QString outputQString = "Delta = ";
    cout<<setprecision(10)<<fixed;
    cout<<"Delta="<<delta<<"\n";
    outputQString.append(QString::number(delta));
    ui->textBrowser->append(outputQString);

    outputQString.clear();
    outputQString.append("Nr steps=");
    outputQString.append(QString::number(k));
    cout<<"Nr steps="<<k<<"\n";
    ui->textBrowser->append(outputQString);

    SparseMatrix err=A*X_SOR-b;
    double eSor=err.norm();
    cout<<"ERR="<<eSor<<"\n";
    outputQString.clear();
    outputQString.append("ERR=");
    outputQString.append(QString::number(eSor));
    ui->textBrowser->append(outputQString);

    //-------------------------
    cout<<"BICGSTAB method:\n";
    ui->textBrowser->append("BICGSTAB method:");


    SparseMatrix x;
    x.allocEmpty(A.getN()); //initial guess
    for (int i=1; i<=x.getN(); ++i) {
        lista v=new celula;
        v->val=1;
        v->col=1;
        x.insertEl(i,v);
    }

    SparseMatrix r=b-A*x;
    SparseMatrix r1=r;
    int n=r.getN();
    double ro=1, w=1, alpha=1;

    SparseMatrix v,p,h,s,t;
    v.allocEmpty(n);
    p.allocEmpty(n);
    h.allocEmpty(n);
    s.allocEmpty(n);
    t.allocEmpty(n);

    k=0;

    while (k<=maxk) {

        //1 ro=(r,r1)
        double roc=0;
        for (int i=1; i<=n; ++i) {
            double v1=0;
            if (r.getLine(i)!=NULL) v1=r.getLine(i)->val;

            roc+=v1*r1.getLine(i)->val;
        }

        //2
        double beta=(roc/ro)*(alpha/w);

        //3 pi = ri-1 + \DF(pi-1 - wi-1*vi-1)
        p=r+(p-v*w)*beta;

        //4 vi = A*pi
        v=A*p;

        //5 a = roi/(r1, v)
        alpha=roc;
        double sub=0;
        for (int i=1; i<=n; ++i) {
            double v1=0;
            if (v.getLine(i)!=NULL) v1=v.getLine(i)->val;

            sub+=v1*r1.getLine(i)->val;
        }
        alpha/=sub;

        //6 h = xi-1 + api
        h=x+p*alpha;

        //7 If h is accurate enough, then set xi = h and quit
        /*double eBic=(A*h-b).norm();
            if (eBic<=eSor) {
                x=h;
                break;
            }*/

        //8 s = ri-1 - avi
        s=r-v*alpha;

        //9 t = As
        t=A*s;

        //10 wi = (t, s)/(t, t)
        double sus=0, jos=0;
        for (int i=1; i<=n; ++i) {

            double v1=0;
            if (t.getLine(i)!=NULL) v1=t.getLine(i)->val;

            double v2=0;
            if (s.getLine(i)!=NULL) v2=s.getLine(i)->val;

            sus+=v1*v2;
            jos+=v1*v1;

        }

        w=sus/jos;

        //11 xi = h + wis
        x=h+s*w;

        //12 If xi is accurate enough, then quit
        double eBic=(A*x-b).norm();
        if (eBic<=eSor) {
            ++k;
            break;
        }

        //13  ri = s - wit
        r=s-t*w;
        ro=roc;

        /*if(k > 3){
            break;
        }*/

        ++k;
        //cout<<k<<" "<<eBic<<"\n";
        outputQString.clear();
        outputQString.append(QString::number(k));
        outputQString.append("  ");
        outputQString.append(QString::number(eBic));
        ui->textBrowser->append(outputQString);
    }

    double eBic=(b-A*x).norm();
    cout<<k<<" "<<eBic<<"\n";
    outputQString.clear();
    outputQString.append(QString::number(k));
    outputQString.append("  ");
    outputQString.append(QString::number(eBic));
    ui->textBrowser->append(outputQString);

}
