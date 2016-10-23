#include "cn_hw2.h"
#include "ui_cn_hw2.h"
#include <armadillo>

using namespace std;
using namespace arma;
#define DOUBLE_PRECISION 25

QString printErrorOne_Arma(mat err_test, int n){
    QString output = "First type error Armadillo = ";

    double norm = 0.0;
    for(int i = 0; i< n; i++){

        norm +=  err_test.at(i)*err_test.at(i);
    }
    norm = sqrt(norm);
    output.append(QString::number(norm,'g',DOUBLE_PRECISION));
    output.append("\n");
    cout << "First type error Armadillo = " << setprecision(10) << fixed << norm << "\n";
    return output;
}

QString printErrorTwo_Arma(mat err_test, vec s,int n){
    QString output = "Second type error Armadillo = ";

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
    output.append(QString::number(up_norm/down_norm, 'g', DOUBLE_PRECISION));
    output.append("\n");
    cout << "Second type error Armadillo = " << setprecision(10) << fixed << up_norm/down_norm << "\n";
    return output;

}

QString printErrorOne(Matrice& err_test, int n){
    QString output = "First type error = ";

    double norm = 0;
    for(int i = 1; i<=n; i++){
        norm +=  err_test.getValue(i,1)*err_test.getValue(i,1);
    }
    norm = sqrt(norm);

    output.append(QString::number(norm, 'g', DOUBLE_PRECISION));
    output.append("\n");
    cout << "First type error = " << setprecision(10) << fixed << norm << "\n";
    return output;

}
QString printErrorTwo(Matrice& err_test, Matrice& s, int n){
    QString output = "Second type error = ";

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
\

    output.append(QString::number(up_norm/down_norm, 'g', DOUBLE_PRECISION));
    output.append("\n");
    cout << "Second type error = " << setprecision(10) << fixed << up_norm/down_norm << "\n";
    return output;

}

void CN_HW2::ex3_4(int n){
    srand(time(NULL));    
    double eps = 0.1;
    double randN = 0.0;

    ///armadi
    ///
    mat A_arma(n,n);
    vec b_arma(n);
    vec s_arma(n);

    Matrice A(n,n,0);//(n,n,1)
    Matrice s(n,1,0);//(n,1,1)

    for(int i = 1; i<= n;i++){
        for(int j = 1; j<= n; j++){
             randN = (double)rand()/(double)1000;
             A.setValue(i,j,randN);
             A_arma.at(i-1, j-1) = randN;
        }
    }

    for(int i = 1; i<= n;i++){
        randN = (double)rand()/(double)1000;
        s.setValue(i,1,randN);        
        s_arma[i-1] = randN;
    }

    cout << "A_arma = " << endl;
    A_arma.print(cout);
    cout << "##########################\n";
    cout << "b_arma = " << endl;
    b_arma.print(cout);
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
    QString outputInfo = "Solution : ";

    for(int i = 1; i<= n; i++){
        outputInfo.append(QString::number(x.getValue(i,1)));
        outputInfo.append(" ");
    }
    outputInfo.append("\n");
    ui->textBrowser->append(outputInfo);

    /*cout << "A*x = \n";
    test.print(cout);
    cout << "##############################\n";

    cout << "b = \n";
    bi.print(cout);
    cout << "##############################\n";
    */

    Matrice err_test = test - bi;
    ui->textBrowser->append(printErrorOne(err_test, n));

    Matrice err_test2 = x - s;
    ui->textBrowser->append(printErrorTwo(err_test2, s,n));

    outputInfo = "Solve time : ";
    outputInfo.append(QString::number(((float)t)/CLOCKS_PER_SEC, 'f', 6));
    outputInfo.append("\n");
    ui->textBrowser->append(outputInfo);
    outputInfo.clear();

    cout << "Solve time : " << ((float)t)/CLOCKS_PER_SEC << " seconds" << endl;
    cout << "##############################\n";

    t = clock();
    mat armaXSolution = solve(A_arma,b_arma);        
    t = clock() - t;

    outputInfo = "Solution armadillo : ";

    for(int i = 0; i< n; i++){
        outputInfo.append(QString::number(armaXSolution.at(i)));
        outputInfo.append(" ");
    }
    outputInfo.append("\n");

    ui->textBrowser->append(outputInfo);
    ui->textBrowser->append(printErrorOne_Arma((A_arma*armaXSolution)-b_arma,n));
    ui->textBrowser->append(printErrorTwo_Arma(armaXSolution-s_arma,s_arma,n));


    outputInfo = "Solve time armadillo : ";
    outputInfo.append(QString::number(((float)t)/CLOCKS_PER_SEC, 'f', 6));
    outputInfo.append("\n");
    ui->textBrowser->append(outputInfo);
    outputInfo.clear();

    cout << "Solve time armadillo : " << ((float)t)/CLOCKS_PER_SEC << " seconds" << endl;
}


CN_HW2::CN_HW2(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::CN_HW2)
{
    ui->setupUi(this);
}

CN_HW2::~CN_HW2()
{
    delete ui;
}

void CN_HW2::on_calculateSol_clicked()
{
    ui->textBrowser->clear();
    QString matrixN = ui->matrixN->text();    

    int n = matrixN.toInt();    

    ex3_4(n);
}
