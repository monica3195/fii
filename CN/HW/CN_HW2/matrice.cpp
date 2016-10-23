#include "matrice.h"
double Matrice::solveTimeSec = 0;
//########################################
Matrice::Matrice() {}
//########################################
Matrice::Matrice(int n, int m, bool random_flag){
    this->m=m;
    this->n=n;

    a=new double*[n+1];
    for (int i=1; i<=n; ++i) a[i]=new double[m+1];

    for (int i=1; i<=n; ++i)
        for (int j=1; j<=m; ++j)
            if (random_flag)
                a[i][j]=(double)rand()/(double)1000;
            else
                a[i][j]=0.0;
}
//########################################
Matrice::Matrice(istream &in){
    cout << "Give n, m";
    in>>n>>m;

    a=new double*[n+1];
    for (int i=1; i<=n; ++i)
        a[i]=new double[m+1];

    for (int i=1; i<=n; ++i)
        for (int j=1; j<=m; ++j){
            cout << "Give elem " << i << ", " << j << ":\n";
            in>>a[i][j];
        }
}
//########################################
Matrice::Matrice(const Matrice& matrix){
    n = matrix.getN();
    m = matrix.getM();

    a=new double*[n+1];
    for (int i=1; i<=n; ++i)
        a[i]=new double[m+1];

    for (int i=1; i<=n; ++i)
        for (int j=1; j<=m; ++j)
            a[i][j] = matrix.getValue(i, j);
}
//########################################
Matrice Matrice::transpose(){
    Matrice t(m,n,0);

    for(int i = 1; i<= n; i++){
        for(int j =  1; j<= m; j++){
            t.setValue(j,i,a[i][j]);
        }
    }
    return t;
}

//########################################
void Matrice::print(ostream &out){
    for (int i=1; i<=n; ++i){
        for (int j=1; j<=m; ++j)
            out<<setprecision(10)<<fixed<<a[i][j]<<" ";
        out<<"\n";
    }
}
//########################################
double Matrice::getValue(int i, int j) const{
    if((i <= n) && (j <= m)){
        return a[i][j];
    }
    return 0;
}
//########################################
void Matrice::setValue(int i, int j, double value){
    if((i <= n) && (j <= m)){
        a[i][j] = value;
    }
}
//########################################
void Matrice::addValue(int i, int j, double value){
    if((i <= n) && (j <= m)){
        a[i][j] += value;
    }
}
//########################################
void Matrice::build_QR(Matrice &R, Matrice &Qt, Matrice &b, double eps){
    int n = R.getN();

    for(int r = 1; r < n; r++){
        double sigma = 0;
        for(int i = r; i<= n; i++){
            sigma += ( R.getValue(i,r) * R.getValue(i,r));
        }

        if(sigma < eps){
            break;
        }

        double k = sqrt(sigma);
        if(R.getValue(r,r) > 0){
            k = -k;
        }

        double beta = sigma - k*R.getValue(r,r);

        Matrice u(1,n,0);
        u.setValue(1,r,R.getValue(r,r) - k);

        for(int i = r+1; i<=n; i++){
            u.setValue(1,i,R.getValue(i,r));
        }

        //R=R*Pr
        //transformarea coloanelor r+1..n
        for(int j = r+1; j<=n; j++){
            double eta = 0;

            for(int i = r; i <= n; ++i){
                eta += u.getValue(1,i) * R.getValue(i,j);
            }
            eta /= beta;

            for(int i = r; i<=n; i++){
                R.setValue(i,j, R.getValue(i,j) - eta*u.getValue(1,i));
            }
        }

        //transformarea coloanei R
        R.setValue(r,r,k);
        for(int i = r+1; i <= n; i++){
            R.setValue(i,r, 0);
        }

        //b = Pr*b
        double eta = 0.0;
        for(int i = r; i <= n; i++){
            eta += u.getValue(1, i) * b.getValue(i,1);
        }
        eta /= beta;

        for(int i = r; i<=n; i++){
            b.setValue(i, 1, b.getValue(i,1) - eta*u.getValue(1,i));
        }

        //QT = Pr*QT
        for(int j = 1; j<= n; j++){
            double eta = 0.0;
            for(int i = r; i<= n; i++){
                eta += u.getValue(1,i) * Qt.getValue(i,j);
            }
            eta /= beta;
            for(int i = r; i<= n; i++){
                Qt.setValue(i, j, Qt.getValue(i, j) - eta*u.getValue(1,i));
            }
        }
    }
}
//########################################
void Matrice::solveSystem(Matrice &A, Matrice &b, Matrice &x, double eps, int n){

    Matrice I(n,n,0);

    for(int i = 1; i <= n; i++){
        I.setValue(i,i,1.0);
    }

    Matrice R(A);
    Matrice Qt(I);

    Matrice::build_QR(R, Qt, b, eps);

    //construiesc raspunsul cu metoda substitutiei inverse
    x.setValue(n, 1, b.getValue(n,1) / R.getValue(n,n));

    for(int i = n-1; i >= 1; i--){
        double sum = 0;

        for(int j = n; j>i; j--){
            sum += R.getValue(i,j) * x.getValue(j,1);
        }
        x.setValue(i,1,(b.getValue(i,1)-sum)/R.getValue(i,i));
    }
}
//########################################
