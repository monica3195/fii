#include "cn_hw6.h"
#include "ui_cn_hw6.h"
#include <QFileDialog>
#include <iostream>
#include <stdio.h>

#define  ARMA_DONT_USE_WRAPPER
#define  ARMA_USE_LAPACK
#define ARMA_USE_BLAS

//#include <armadillo>
#include </usr/include/armadillo>
using namespace std;
using namespace arma;

CN_HW6::CN_HW6(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::CN_HW6)
{
    ui->setupUi(this);
}

CN_HW6::~CN_HW6()
{
    delete ui;
}

void CN_HW6::on_loadMatrixA_clicked()
{
    matrixFileA = QFileDialog::getOpenFileName(this, "Select matrix file A", ".", "Text files (*.txt);; All files (*.*)");
    ifstream cinA(matrixFileA.toLatin1().data());
    A = SparseMatrix(cinA, 0);
}

void CN_HW6::on_compute_clicked()
{
    int ps = 0;
    QString p = ui->sInput->text();
    ui->textBrowser->append("s value : ");

    ps = p.toUInt();
    ui->textBrowser->append(QString::number(ps));

    int n=500;
    n=A.getN();

    mat A_arma(n,n);

    for(unsigned int i = 0; i< n;i++){
        for(unsigned int j = 0; j< n;j++){
            A_arma.at(i,j) = 0;
        }
    }

    for (int i=1; i<=n; ++i){
        for (lista p=A.M[i]; p!=NULL; p=p->next){
            A_arma.at(i-1, p->col-1) = p->val;
        }
    }

    //A.generateSymetric(n);

    cout<<A.isSymetric()<<"\n";

    if(A.isSymetric()){
        ui->textBrowser->append("A.isSymetric = true");
    }else{
        ui->textBrowser->append("A.isSymetric = false");
    }

    SparseMatrix B=A.transpose();

    if (A==B) {
        cout<<"a==at\n";
        ui->textBrowser->append("a==at");
    }
    else {
        cout<<"a!=at\n";
        ui->textBrowser->append("a!=at");
    }

    //generez un vector aleator x

    SparseMatrix x;
    x.allocEmpty(n);

    double normx=0;

    for (int i=1; i<=n; ++i) {
        lista v=new celula;
        v->val=(double)rand()/10000.0;
        v->col=1;
        x.insertEl(i,v);

        normx+=v->val*v->val;

    }

    normx=sqrt(normx);

    x=x*(1.0/normx);

    SparseMatrix w=A*x;

    double prop_value=scalarProd(w,x);
    double eps=0.0001,err; //1992 --- eps=0.01, 2014 --- eps=0.001, 2015.95 -- eps=0.0001
    int kmax=10000;

    int k=0;

    do {

        x=w*(1.0/w.norm());
        w=A*x;
        prop_value=scalarProd(w,x);
        ++k;

        err=(w-x*prop_value).norm();

        cout<<setprecision(10)<<fixed<<err<<"\n";
        //ui->textBrowser->append(QString::number(err));

    }while (k<=kmax && err>(double)n*eps);

    if (k==kmax+1){
        cout<<"fail to aproximate proper value\n";
        ui->textBrowser->append("fail to aproximate proper value\n");
    }
    else {
        ui->textBrowser->append("prop_value " );
        ui->textBrowser->append(QString::number(prop_value));
        cout<<setprecision(10)<<fixed<<prop_value<<"\n";
    }
    arma::vec singularValues = arma::svd(A_arma);
    cout << "Valorile singulare " << singularValues << endl;
    ui->textBrowser->append("Valorile singulare :");
    for(int i = 0; i<singularValues.n_elem ;i++){
        ui->textBrowser->append(QString::number(singularValues.at(i)));
    }


    int rank_A_arma = arma::rank(A_arma);
    ui->textBrowser->append("Rank matrice A:");
    ui->textBrowser->append(QString::number(rank_A_arma));
    cout << "Rank matrice A:" << rank_A_arma << endl;

    int cond_A_arma = arma::cond(A_arma);
    ui->textBrowser->append("Cond. number matrice A:");
    ui->textBrowser->append(QString::number(cond_A_arma));
    cout << "Cond. number matrice A:" << cond_A_arma << endl;

    mat normMatrix;
    mat U;
    mat V;
    sp_mat X(A_arma);
    vec s;
    mat result, aux;


    //svds( mat U, vec s, mat V, sp_mat X, k )
    arma::svd(U,s,V, A_arma);
    normMatrix = (A_arma - U*diagmat(s)*V.t());
    double infNorm = 1231;
    infNorm = norm(normMatrix, "inf");
    double okDouble = infNorm;
    cout << "norma ||A − USV.t()||∞ inf \n";
    ui->textBrowser->append("norma ||A − USV.t()||∞ inf \n");
    ui->textBrowser->append(QString::number(okDouble));
    printf("%f \n", okDouble);

    cout << "Number : ";
    //cin >> ps;
    //ps = 10;
    if(ps >= rank_A_arma){
        ui->textBrowser->append("Error ! (p < rank(A))");
        return;
    }

    for(int i = 0; i< ps; i++){
        aux = U.col(i) * V.col(i).t();
        aux = s.at(i) * aux;

        if(i == 0){
            result = aux;
        }else{
            result = result + aux;
        }
    }
    normMatrix = A_arma - result;
    infNorm = norm(normMatrix, "inf");
    okDouble = infNorm;
    cout << "||A−As||∞ inf\n";
    ui->textBrowser->append("||A−As||∞ inf\n");
    ui->textBrowser->append(QString::number(okDouble));
    printf("%f \n", okDouble);
}
