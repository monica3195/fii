#include<iostream>
#include<fstream>
#include<unordered_set>
#include<unordered_map>
#include<stdlib.h>
#include<time.h>
#include<string>
#include<conio.h>
#include<iomanip>
#include<cmath>
using namespace std;

typedef struct celula {
        double val;
        int col;
        celula *next;
        }*lista;

class SparseMatrix {

      lista *M;
      unordered_set<int> line;
      unordered_set<int> col;
      int n;

public:

      SparseMatrix() {}

      SparseMatrix(const SparseMatrix &Obj){

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

      SparseMatrix(int n) {
          M=new lista[n+1];
          for (int i=0; i<=n; ++i) M[i]=NULL;
          this->n=n;

          for (int i=1; i<=n; ++i)
          if ( rand()%2==1 ){

              int nrel=rand()%(n/10);
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

      SparseMatrix(istream &in, bool as_vector) {

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

      void allocEmpty(int n) {

           this->n=n;
           M=new lista[n+1];
           for (int i=0; i<=n; ++i) M[i]=NULL;

           }

      void insertEl(int i,lista v) {
           v->next=M[i];
           line.insert(i);
           col.insert(v->col);
           M[i]=v;
           }

      SparseMatrix transpose() const {
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

	  void print(ostream &out) {

		  out<<n<<"\n";
		  for (int i=1; i<=n; ++i)
		   for (lista p=M[i]; p!=NULL; p=p->next)
			out<<p->val<<" , "<<i-1<<", "<<p->col-1<<"\n";

	  }

	  bool verifyDiagonal(double eps) {

		  for (int i=1; i<=n; ++i){

			 bool isOk=0;
			 for (lista p=M[i]; p!=NULL; p=p->next)
			  if (p->col==i && fabs(p->val)>eps ) { isOk=1; break; }
		  	
		  	if (!isOk) return 0;
		  }
		  
		  return 1;
	  	
	  }

	  double norm() {
		  double ne=0;
		  for (int i=1; i<=n; ++i) ne+=(M[i]->val)*(M[i]->val);
		  return sqrt(ne);
	  }

      int getN() const { return this->n; }
      lista getLine(int k) const { return this->M[k]; }
      unordered_set<int> getLines() const { return this->line; }
      unordered_set<int> getCols() const { return this->col; }

};

SparseMatrix operator+(const SparseMatrix &A,const SparseMatrix &B){
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

};

SparseMatrix operator-(const SparseMatrix &A,const SparseMatrix &B){
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

};

SparseMatrix operator*(const SparseMatrix &A, const SparseMatrix &B1){
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
};

SparseMatrix operator*(const SparseMatrix &A, const double &c) {
	SparseMatrix rez=A;
	
	for (int i=1; i<=rez.getN(); ++i)
	 for (lista p=rez.getLine(i); p!=NULL; p=p->next)
	  p->val*=c;
	  
	return rez;
}

bool operator==(const SparseMatrix &A,const SparseMatrix &B) {

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

int main(void) {
	
	ifstream cinA("A.txt");
	ifstream cinb("b.txt");
	
	SparseMatrix A(cinA,0);
	SparseMatrix b(cinb,1);
	
	if (A.verifyDiagonal(0.000000001)) cout<<"Diagonal is ok\n";
	else cout<<"Matrix A has NULL elements on main diagonal\n";
	
	cout<<"SOR Method:\n";
	
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
	
	cout<<setprecision(10)<<fixed;
	cout<<"Delta="<<delta<<"\n";
	cout<<"Nr steps="<<k<<"\n";
	
	SparseMatrix err=A*X_SOR-b;
	double eSor=err.norm();
	cout<<"ERR="<<eSor<<"\n";
	
	//-------------------------
	cout<<"BICGSTAB method:\n";
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
		
		//3 pi = ri-1 + ß(pi-1 - wi-1*vi-1)
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
		
		++k;
		cout<<k<<" "<<eBic<<"\n";
	}

	double eBic=(b-A*x).norm();
	cout<<k<<" "<<eBic<<"\n";
	
	return 0;
}
