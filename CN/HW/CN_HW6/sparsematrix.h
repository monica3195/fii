#ifndef SPARSEMATRIX_H
#define SPARSEMATRIX_H

#include<iostream>
#include<fstream>
#include<unordered_set>
#include<unordered_map>
#include<stdlib.h>
#include<time.h>
#include<string>
#include<iomanip>
#include<cmath>
using namespace std;

typedef struct celula {
        double val;
        int col;
        celula *next;
        }*lista;

class SparseMatrix {

public:
      lista *M;
      unordered_set<int> line;
      unordered_set<int> col;
      int n;

public:

      SparseMatrix() {} //constructorul implicit

      SparseMatrix(const SparseMatrix &Obj){ //constructor de copiere

         n=Obj.getN();
         M=new lista[n+1];
         for (int i=0; i<=n; ++i) M[i]=NULL;

         for (int i=1; i<=n; ++i) {
             lista cl=Obj.getLine(i);

             if (cl!=0) line.insert(i);

             for (lista p=cl; p!=NULL; p=p->next){

                 lista v=new celula;
                 v->val=p->val;
                 v->col=p->col;
                 v->next=M[i];
                 M[i]=v;

                 col.insert(v->col);

                 }

             }

      }

      SparseMatrix(int n) { //genereaza aleator o matrice rara
          M=new lista[n+1];
          for (int i=0; i<=n; ++i) M[i]=NULL;
          this->n=n;

          for (int i=1; i<=n; ++i)
          if ( rand()%(max(n/5,2))==1 ){

              int nrel=rand()%(max(n/5,2));
              if (nrel>0) line.insert(i);
              unordered_set<int> c_aux;
              c_aux.clear();

              for (int j=1; j<=nrel; ++j) {

                  int c=rand()%n+1;
                  while (c_aux.find(c)!=c_aux.end()) c=rand()%n+1;
                  c_aux.insert(c);
                  col.insert(c);

                  lista v=new celula;
                  v->val=(double)rand()/10000.0;
                  v->col=c;
                  v->next=M[i];
                  M[i]=v;

                  }

              }
         }

      SparseMatrix(istream &in, bool as_vector) { // citeste matricea dintr-un stream ca matrice sau vector

          in>>n;
          M=new lista[n+1];
          for (int i=0; i<=n; ++i) M[i]=NULL;

          if (as_vector) {
                         col.insert(1);
                         for (int i=1; i<=n; ++i) {
                             line.insert(i);
                             double val;
                             in>>val;
                             lista v=new celula;
                             v->val=val;
                             v->col=1;
                             v->next=M[i];
                             M[i]=v;

                             }
                         }
           else {
              int m;
              in>>m;
              string s;

              for (int i=1; i<=m; ++i){

              //get value
              double val=0;
              in>>val;

              //get coord
              int x=0;
              int y=0;

              getline(in,s);
              int l=0;
              while (s[l]<'0' || s[l]>'9') ++l;
              while (s[l]>='0' && s[l]<='9') { x=x*10+(int)s[l]-48; ++l; }
              while (s[l]<'0' || s[l]>'9') ++l;
              while (s[l]>='0' && s[l]<='9') { y=y*10+(int)s[l]-48; ++l; }

              //cout<<val<<" "<<x<<" "<<y<<"\n";

              ++x;
              ++y;

              //insert new cell
              col.insert(y);
              line.insert(x);

              lista v=new celula;
              v->val=val;
              v->col=y;
              v->next=M[x];
              M[x]=v;

              }
          }
      }

      void allocEmpty(int n) { //aloca memorie pentru omatrice cu n linii

           this->n=n;
           M=new lista[n+1];
           for (int i=0; i<=n; ++i) M[i]=NULL;

           }

      void generateSymetric(int n) {

          M=new lista[n+1];
          for (int i=0; i<=n; ++i) M[i]=NULL;
          this->n=n;

          int cc=0;

          for (int i=1; i<=n; ++i)
          if ( rand()%(max(n/5,2))==0 ){

              int nrel=rand()%(max(i/5,2));

              if (nrel>0) { line.insert(i); col.insert(i); }
              unordered_set<int> c_aux;
              c_aux.clear();

              for (int j=1; j<=nrel; ++j) {

                  int c=rand()%i+1;
                  while (c_aux.find(c)!=c_aux.end()) c=rand()%n+1;
                  c_aux.insert(c);
                  col.insert(c);
                  line.insert(c);

                  lista v=new celula;
                  v->val=(double)rand()/10000.0;
                  double auxv=v->val;
                  v->col=c;
                  v->next=M[i];
                  M[i]=v;

                  v=new celula;
                  v->val=auxv;
                  v->col=i;
                  v->next=M[c];
                  M[c]=v;

                  cc+=2;

                  }

              }

             // cout<<cc<<"\n";

      }

      void insertEl(int i,lista v) { //insereaza un element pe o linie
           v->next=M[i];
           line.insert(i);
           col.insert(v->col);
           M[i]=v;
           }

      SparseMatrix transpose() const { //returneaza matricea transpusa
          SparseMatrix t;
          t.allocEmpty(n);

          for (int i=1; i<=n; ++i)
           for (lista p=M[i]; p; p=p->next){
               lista v=new celula;
               v->val=p->val;
               v->col=i;
               t.insertEl(p->col,v);
               }

          return t;
       }

      void print(ostream &out) { //afiseaza matricea intr-un outstream

          out<<n<<"\n";
          for (int i=1; i<=n; ++i)
           for (lista p=M[i]; p!=NULL; p=p->next)
            out<<p->val<<" , "<<i-1<<", "<<p->col-1<<"\n";

      }

      bool verifyDiagonal(double eps) { //verifica daca elementele de pe diagonala sunt diferite de 0

          for (int i=1; i<=n; ++i){

             bool isOk=0;
             for (lista p=M[i]; p!=NULL; p=p->next)
              if (p->col==i && fabs(p->val)>eps ) { isOk=1; break; }

            if (!isOk) return 0;
          }

          return 1;

      }

      bool isSymetric() {

           for (int i=1; i<=n; ++i)
            for (lista p=M[i]; p!=NULL; p=p->next) {
                  bool ok=0;

                  for (lista p1=M[p->col]; p1!=NULL; p1=p1->next)
                   if (p1->col==i && fabs(p1->val-p->val)<0.00001) { ok=1; break; }

                  if (!ok) {
                            cout<<i<<" "<<p->col<<" "<<p->val<<"\n";
                            return 0;
                            }
            }

            return 1;
      }

      double norm() { //norma unui vector coloana
          double ne=0;
          for (int i=1; i<=n; ++i)
           if (M[i]!=NULL) ne+=(M[i]->val)*(M[i]->val);
          return sqrt(ne);
      }

      int getN() const { return this->n; }
      lista getLine(int k) const { return this->M[k]; }
      unordered_set<int> getLines() const { return this->line; }
      unordered_set<int> getCols() const { return this->col; }

      friend SparseMatrix operator+(const SparseMatrix &A,const SparseMatrix &B){
              SparseMatrix rez=A;

              unordered_map<int,double> aux;
              aux.clear();

              for (int i=1; i<=rez.getN(); ++i){

                  //pun in unordered_map linia din B
                  for (lista p=B.getLine(i); p!=NULL; p=p->next) aux[p->col]=p->val;

                  //actualizez linia din rez
                  for (lista p=rez.getLine(i); p!=NULL; p=p->next){

                      p->val+=aux[p->col];
                      aux.erase(p->col);

                      }

                  //adaug elemente pe line
                  unordered_map<int,double>::iterator it;

                  for (it=aux.begin(); it!=aux.end(); ++it){
                      lista v=new celula;
                      v->val=it->second;
                      v->col=it->first;
                      rez.insertEl(i,v);
                      }

                  //clear unordered_map
                  aux.clear();

              }

            return rez;

      }

      friend SparseMatrix operator-(const SparseMatrix &A,const SparseMatrix &B){
              SparseMatrix rez=A;

              unordered_map<int,double> aux;
              aux.clear();

              for (int i=1; i<=rez.getN(); ++i){

                  //pun in unordered_map linia din B
                  for (lista p=B.getLine(i); p!=NULL; p=p->next) aux[p->col]=p->val;

                  //actualizez linia din rez
                  for (lista p=rez.getLine(i); p!=NULL; p=p->next){

                      p->val-=aux[p->col];
                      aux.erase(p->col);

                      }

                  //adaug elemente pe line
                  unordered_map<int,double>::iterator it;

                  for (it=aux.begin(); it!=aux.end(); ++it){
                      lista v=new celula;
                      v->val=-it->second;
                      v->col=it->first;
                      rez.insertEl(i,v);
                      }

                  //clear unordered_map
                  aux.clear();

              }

            return rez;

      }

      friend SparseMatrix operator*(const SparseMatrix &A, const SparseMatrix &B1){
           SparseMatrix rez;
           SparseMatrix B=B1.transpose();
           rez.allocEmpty(A.getN());

           unordered_set<int> linesA=A.getLines();
           unordered_set<int> linesB=B.getLines();
           unordered_set<int>::iterator itA, itB;
           unordered_map<int,double> bLine;
           int x,y;

           for (itA=linesA.begin(); itA!=linesA.end(); ++itA)
            for (itB=linesB.begin(); itB!=linesB.end(); ++itB)
            {
               x=(*itA); y=(*itB);

               //construiesc elementul x y din rezulatat ===> inmultesc linia x din A cu linia y din B
               for (lista p=B.getLine(y); p!=NULL; p=p->next)
                 bLine[p->col]=p->val;

               double val=0;

               for (lista p=A.getLine(x); p!=NULL; p=p->next) val+=p->val*bLine[p->col];

               if ( fabs(val)>0.000000001 )
               {
               lista v=new celula;
               v->val=val;
               v->col=y;
               rez.insertEl(x,v);
               }

               bLine.clear();

            }

           return rez;
      }

      friend SparseMatrix operator*(const SparseMatrix &A, const double &c) {
          SparseMatrix rez=A;

          for (int i=1; i<=rez.getN(); ++i)
           for (lista p=rez.getLine(i); p!=NULL; p=p->next)
            p->val*=c;

          return rez;
      }

      friend bool operator==(const SparseMatrix &A,const SparseMatrix &B) {

         if (A.getN() != B.getN() ) return 0;

         unordered_set<int> linesA=A.getLines();
         unordered_set<int>::iterator it;

         //check if B includes A
         for (it=linesA.begin(); it!=linesA.end(); ++it){

             //get line i from b in a unordered_map
             unordered_map<int, double> lineB;
             for (lista p=B.getLine(*it); p; p=p->next) lineB[p->col]=p->val;

             for (lista p=A.getLine(*it); p; p=p->next)
              if (fabs(lineB[p->col]-p->val)>0.1) { cout<<"L C VA VExp:"<<(*it)<<" "<<p->col<<" "<<p->val<<" "<<lineB[p->col]<<"\n"; return 0;}

             }

         //check if A includes B
         unordered_set<int> linesB=B.getLines();
         for (it=linesB.begin(); it!=linesB.end(); ++it){

             //get line i from b in a unordered_map
             unordered_map<int, double> lineA;
             for (lista p=A.getLine(*it); p; p=p->next) lineA[p->col]=p->val;

             for (lista p=B.getLine(*it); p; p=p->next)
              if (fabs(lineA[p->col]-p->val)>0.1) return 0;

             }

         //A includes B and B includes A ===> A==B
         return 1;
      }


      friend double scalarProd(const SparseMatrix &A, const SparseMatrix &B) {
          double rez=0;
          for (int i=1; i<=A.getN(); ++i)
           if (A.getLine(i)!=NULL && B.getLine(i)!=NULL)
                   rez+=A.getLine(i)->val*B.getLine(i)->val;

          return rez;
      }
};

#endif // SPARSEMATRIX_H
