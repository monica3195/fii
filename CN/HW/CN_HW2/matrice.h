#ifndef MATRICE_H
#define MATRICE_H

#include <iostream>
#include <cmath>
#include <algorithm>
#include <iomanip>
#include <time.h>
#include <ctime>

using namespace std;

class Matrice
{
    double **a;
    int n, m;
    //static clock_t beginTime, endTime;
public:
    static double solveTimeSec;
    Matrice();
    Matrice(int n, int m, bool random_flag);
    Matrice(istream& in);
    Matrice(const Matrice& m);

    Matrice transpose();

    void print(ostream& out);
    int getN() const{ return n;}
    int getM() const{ return m;}
    double getValue(int i, int j) const;

    void setValue(int i, int j, double value);
    void addValue(int i, int j, double value);

    static void build_QR(Matrice &R, Matrice &Qt, Matrice &b, double eps);
    static void solveSystem(Matrice &A, Matrice &b, Matrice &x, double eps, int n);

    friend Matrice operator+(const Matrice& m1, const Matrice& m2){
        int l1 = m1.getN();
        int l2 = m2.getN();

        int c1 = m1.getM();
        int c2 = m2.getM();

        if((l1 != l2) || (c1 != c2)){
            Matrice rez(0,0,0);
            return rez;
        }

        Matrice rez(l1,c1,0);
        for(int i = 1; i <= l1; i++){
            for(int j = 1; j<= c1; j++){
                rez.setValue(i, j, m1.getValue(i,j) + m2.getValue(i,j));
            }
        }
        return rez;
    }
    friend Matrice operator-(const Matrice& m1, const Matrice& m2){
        int l1 = m1.getN();
        int l2 = m2.getN();

        int c1 = m1.getM();
        int c2 = m2.getM();

        if((l1 != l2) || (c1 != c2)){
            Matrice rez(0,0,0);
            return rez;
        }
        Matrice rez(l1,c1,0);
        for(int i = 1; i <= l1; i++){
            for(int j = 1; j<= c1; j++){
                rez.setValue(i, j, m1.getValue(i,j) - m2.getValue(i,j));
            }
        }
        return rez;}
    friend Matrice operator*(const Matrice& m1, const Matrice& m2){
        int l1 = m1.getN();
        int l2 = m2.getN();

        int c1 = m1.getM();
        int c2 = m2.getM();

        if(c1 != l2){
            Matrice rez(0,0,0);
            return rez;
        }
        Matrice rez(l1,c2,0);

        for(int i = 1; i <= l1; i++){
            for(int j = 1; j<= c2; j++){
                for(int k = 1; k<= c1; k++){
                    rez.addValue(i, j, m1.getValue(i,k) * m2.getValue(k,j));
                }
            }
        }
        return rez;
    }
};

#endif // MATRICE_H
