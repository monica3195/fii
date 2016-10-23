#include "cn_hw2.h"
#include <QApplication>
#include <stdio.h>
#include <armadillo>
#include <matrice.h>

using namespace std;
using namespace arma;

void ex1(){
    Matrice s(cin);
    Matrice a(cin);

    cout << "###################\n";
    s.print(cout);
    cout << "###################\n";
    a.print(cout);
    cout << "###################\n";
    Matrice p = s * a;
    p.print(cout);
    cout << "###################\n";
}

void ex2(){
    Matrice A(3,3,1);
    Matrice I(3,3,0);

    for(int i =1; i<= 3; i++){
        I.setValue(i,i,1.0);
    }
    Matrice R(A);
    Matrice Qt = I;
    Matrice b(3,3,1);
    Matrice bi(b);

    Matrice::build_QR(R,Qt,b,0.01);

    cout << "Matricea A : \n";
    A.print(cout);
    cout << "###################\n";

    cout << "Matricea superior triunghiulara R : \n";
    R.print(cout);
    cout << "###################\n";

    cout << "Matricea Ortogonala Q : \n";
    Matrice Q = Qt.transpose();
    Q.print(cout);
    cout << "###################\n";

    cout << "Qt * Q : \n";
    Matrice prod = Q * Qt;
    prod.print(cout);
    cout << "###################\n";

    Matrice test = Qt * bi;
    cout << "QT * b = \n";
    b.print(cout);
    cout << "###################\n";

    cout << "QTest * b = \n";
    test.print(cout);
    cout << "###################\n";

}

void testOp() {

    srand(time(NULL));

    Matrice m(cin);

    cout << "M : \n";
    m.print(cout);

    Matrice m2(cin);

    cout << "M2 : \n";
    m2.print(cout);

    Matrice x = m + m2;
    cout << "Suma : \n";
    x.print(cout);

    x=m - m2;
    cout << "Diff : \n";
    x.print(cout);

    x=m*m2;
    cout << "Produs : \n";
    x.print(cout);
}

void _printErrorOne_Arma(mat err_test, int n){

    double norm = 0.0;
    for(int i = 0; i< n; i++){

        norm +=  err_test.at(i)*err_test.at(i);
    }
    norm = sqrt(norm);

    cout << "First type error Armadillo = " << setprecision(10) << fixed << norm << "\n";
}

void _printErrorTwo_Arma(mat err_test, vec s,int n){
    double up_norm = 0;
    for(int i = 1; i <=n; i++){
        up_norm += err_test.at(i) * err_test.at(i);
    }
    up_norm = sqrt(up_norm);

    double down_norm = 0;
    for(int i = 1; i <= n; i++){
        down_norm += s.at(i) * s.at(i);
    }
    down_norm = sqrt(down_norm);

    cout << "Second type error Armadillo = " << setprecision(10) << fixed << up_norm/down_norm << "\n";
}

void _printErrorOne(Matrice& err_test, int n){

    double norm = 0;
    for(int i = 1; i<=n; i++){
        norm +=  err_test.getValue(i,1)*err_test.getValue(i,1);
    }
    norm = sqrt(norm);

    cout << "First type error = " << setprecision(10) << fixed << norm << "\n";
}
void _printErrorTwo(Matrice& err_test, Matrice& s, int n){

    double up_norm = 0;
    for(int i = 1; i <=n; i++){
        up_norm += err_test.getValue(i,1) * err_test.getValue(i,1);
    }
    up_norm = sqrt(up_norm);

    double down_norm = 0;
    for(int i = 1; i <= n; i++){
        down_norm += s.getValue(i,1) * s.getValue(i,1);
    }
    down_norm = sqrt(down_norm);

    cout << "Second type error = " << setprecision(10) << fixed << up_norm/down_norm << "\n";
}

void ex3_4(){
    int n = 3;
    double eps = 0.1;
    double randN = 0.0;

    ///armadi
    ///
    mat A_arma(n,n);
    vec b_arma(n);    


    Matrice A(n,n,0);//(n,n,1)
    Matrice s(n,1,0);//(n,1,1)

    for(int i = 1; i<= n;i++){
        for(int j = 1; j<= n; j++){
             randN = ((double)rand())/(double)1000;
             A.setValue(i,j,randN);
             A_arma.at(i-1, j-1) = randN;
        }
    }

    for(int i = 1; i<= n;i++){
        randN = (double)rand()/(double)1000;
        s.setValue(i,1,randN);                
    }



    cout << "Amat = " << endl;
    A_arma.print(cout);
    cout << "##########################\n";    

    cout << "A = " << endl;
    A.print(cout);
    cout << "##########################\n";
    cout << "s = " << endl;
    s.print(cout);
    cout << "##########################\n";    


    Matrice b = A*s;

    for(int i = 1; i<= n; i++){
        b_arma.at(i-1) = b.getValue(i,1);
    }

    Matrice x(n,1,0);

    Matrice bi(b);

    clock_t t = clock();
    Matrice::solveSystem(A,b,x,eps, n);
    t = clock() -t;
    Matrice test = A*x;

    cout << "A*x = \n";
    test.print(cout);
    cout << "##############################\n";

    cout << "b = \n";
    bi.print(cout);
    cout << "##############################\n";

    cout << "x = \n";
    x.print(cout);
    cout << "##############################\n";


    Matrice err_test = test - bi;
    _printErrorOne(err_test, n);

    Matrice err_test2 = x - s;
    _printErrorTwo(err_test2, s,n);

    cout << "Solve time : " << ((float)t)/CLOCKS_PER_SEC << " seconds" << endl;
    cout << "##############################\n";

    t = clock();
    mat armaXSolution = solve(A_arma,b_arma);
    t = clock() - t;
    cout << "Armadillo solution :\n ";
    armaXSolution.print(cout);
    cout << "\n";

    _printErrorOne_Arma((A_arma*armaXSolution)- b_arma,n);
    _printErrorTwo_Arma(armaXSolution-b_arma,b_arma,n);

    cout << "Solve time armadillo : " << ((float)t)/CLOCKS_PER_SEC << " seconds" << endl;

}

int main(int argc, char *argv[])
{
   (void)argc;
   (void)argv;
    srand(time(NULL));
   //testOp();
    //ex1();
    //ex2();
    //ex3_4();

    QApplication a(argc, argv);
    CN_HW2 w;
    w.show();

    return a.exec();
    /*vec b;
      b << 2.0 << 5.0 << 2.0;

      // endr represents the end of a row
      // in a matrix
      mat A;
      A << 1.0 << 2.0 << endr
        << 2.0 << 3.0 << endr
        << 1.0 << 3.0 << endr;

      cout << "Least squares solution:" << endl;
      cout << solve(A,b) << endl;
      A.print(cout);
      */
    return 0;
}
