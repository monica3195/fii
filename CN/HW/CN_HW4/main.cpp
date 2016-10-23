#include "cn_hw4.h"
#include <QApplication>
#include <sparsematrix.h>

int test(){

    srand(time(NULL));
    cout << "Start" << endl;

    ifstream cin("MatriceA.txt");
    ifstream cin2("MatriceB.txt");
    ifstream cin3("MatriceAplusB.txt");
    ifstream cin4("MatriceAoriB.txt");
    ifstream cin5("VectorA.txt");

    SparseMatrix a(cin,0);
    SparseMatrix b(cin2,0);
    SparseMatrix sum(cin3,0);
    SparseMatrix prod(cin4,0);

    SparseMatrix c=a+b;

    if (c==sum) cout<<"He He Sum correct!!!!\n";
    else cout<<"Pfffff wrong sum!!!!!\n";

    time_t start, end;
    time(&start);
    SparseMatrix d=a*b;
    time(&end);

    cout<<"Prod time="<<difftime(end,start)<<"\n";

    if (d==prod) cout<<"He He Prod correct!!!!\n";
    else cout<<"Pfffff wrong prod!!!!!\n";

    SparseMatrix vecA(cin5,1);
    SparseMatrix X;
    X.allocEmpty(a.getN());
    for (int i=1; i<=X.getN(); ++i) {
        lista v=new celula;
        v->val=i;
        v->col=1;
        X.insertEl(i,v);
    }

    SparseMatrix e=a*X;

    if (e==vecA) cout<<"A*X==VA correct\n";
    else cout<<"A*X!=VA\n";

    //    getch();


    return 0;
}

int main(int argc, char *argv[])
{
    //(void)test();
     srand(time(NULL));
    QApplication a(argc, argv);
    CN_HW4 w;
    w.show();

    return a.exec();
}
