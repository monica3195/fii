#include<iostream>
#include<fstream>
#include<algorithm>
#include<string>
#include<cmath>
#include<cstring>
#include<time.h>
#include<stdlib.h>
#include<iomanip>
using namespace std;

class Matrice {
	double **a;
	int n, m;
  public:

  	Matrice(int n, int m, bool random_flag) {

	   this->m=m;
	   this->n=n;

	   a=new double*[n+1];
	   for (int i=1; i<=n; ++i) a[i]=new double[m+1];

	   for (int i=1; i<=n; ++i)
		for (int j=1; j<=m; ++j)
		 if (random_flag) a[i][j]=(double)rand()/(double)1000;
		 else a[i][j]=0.0;

  	}

  	Matrice(istream& in) {

	  in>>n>>m;

	  a=new double*[n+1];
      for (int i=1; i<=n; ++i) a[i]=new double[m+1];

	  for (int i=1; i<=n; ++i)
		for (int j=1; j<=m; ++j) in>>a[i][j];

  	}

	void print(ostream& out) {

	  for (int i=1; i<=n; ++i){

	    for (int j=1; j<=m; ++j)
	     	out<<setprecision(10)<<fixed<<a[i][j]<<" ";

      out<<"\n";
      }

	}

	Matrice transpose() {

		Matrice t(m,n,0);

		for (int i=1; i<=n; ++i)
		 for (int j=1; j<=m; ++j)
		 t.setV(j,i,a[i][j]);

		return t;

	}

	double getV(int i, int j) { if (i<=n && j<=m) return a[i][j]; else return 0; }
	void setV(int i, int j,double v) { if (i<=n && j<=m) a[i][j]=v; }
	void addV(int i, int j,double v) { if (i<=n && j<=m) a[i][j]+=v; }

	int getN() { return this->n; }
	int getM() { return this->m; }

	Matrice cpy(){

		Matrice cp(n,m,0);

		for (int i=1; i<=n; ++i)
		 for (int j=1; j<=m; ++j)
		  cp.setV(i,j,a[i][j]);

	   return cp;

	}

	static void build_QR(Matrice &R,Matrice &Qt,Matrice &b,double eps){

		int n=R.getN();

		for (int r=1; r<n; ++r) {

			//construiesc sigma, beta si u
			double sigma=0;
			for (int i=r; i<=n; ++i) sigma+=R.getV(i,r)*R.getV(i,r);
			if (sigma<eps) break; //Pr=In => A-singulara

			double k=sqrt(sigma);
			if (R.getV(r,r)>0) k=-k;

			double beta=sigma-k*R.getV(r,r);

			Matrice u(1,n,0);
			u.setV(1,r,R.getV(r,r)-k);
			for (int i=r+1; i<=n; ++i) u.setV(1,i,R.getV(i,r));

			//R=R*Pr
			//transformarea coloanelor r+1..n
			for (int j=r+1; j<=n; ++j){

				double eta=0;
				for (int i=r; i<=n; ++i) eta+=u.getV(1,i)*R.getV(i,j);
				eta/=beta;

				for (int i=r; i<=n; ++i) R.setV(i,j,R.getV(i,j)-eta*u.getV(1,i));
			}

			//transformarea coloanei r
			R.setV(r,r,k);
			for (int i=r+1; i<=n; ++i) R.setV(i,r,0);

			//b=Pr*b
			double eta=0;
			for (int i=r; i<=n; ++i) eta+=u.getV(1,i)*b.getV(i,1);
			eta/=beta;

			for (int i=r; i<=n; ++i) b.setV(i,1,b.getV(i,1)-eta*u.getV(1,i));

			//QT=Pr*QT
			for (int j=1; j<=n; ++j) {

				double eta=0;
				for (int i=r; i<=n; ++i) eta+=u.getV(1,i)*Qt.getV(i,j);
				eta/=beta;

				for (int i=r; i<=n; ++i)
				 Qt.setV(i,j,Qt.getV(i,j)-eta*u.getV(1,i));
			}


        }//end main for

	}

    static void solveSystem(Matrice &A, Matrice &b, Matrice &x,double eps,int n) {

		Matrice I(n,n,0);

		for (int i=1; i<=n; ++i)
	 		I.setV(i,i,1.0);

        Matrice R=A.cpy();
        Matrice Qt=I.cpy();

        Matrice::build_QR(R,Qt,b,eps);

		//construiesc raspunsul cu metoda substitutiei inverse
		x.setV(n,1,b.getV(n,1)/R.getV(n,n));

		for (int i=n-1; i>=1; --i) {

			double sum=0;
			for (int j=n; j>i; --j) sum+=R.getV(i,j)*x.getV(j,1);

			x.setV(i,1,(b.getV(i,1)-sum)/R.getV(i,i));

        }

	}
	
	static void find_inverse(Matrice &a,Matrice &inv,double &det,double eps) {
		
		//construiesc matrice unitate ca fiind matricea inversa
		int n=a.getN();
		for (int i=1; i<=n; ++i) inv.setV(i,i,1.0);
		
		//transform matricea a in matrice superior triunghiulara apoi aplic aceleasi operatii pe matricea inversa
		double d_semn=1.0;
		
		for (int j=1; j<n; ++j) {
			
			//aleg pivotul pentru coloana j
			double maxim=fabs(a.getV(j,j));
			int pivot=j;
			
			for (int i=j+1; i<=n; ++i)
			 if ( fabs(a.getV(i,j))>maxim) {
			 	maxim=fabs(a.getV(i,j));
			 	pivot=i;
			 }
			 
			if ( maxim<=eps ) { det=0; break; }
			
			if (pivot!=j) {//interschimb liniile si schimb semnul determinantului
				d_semn*=-1.0;
				for (int i=1; i<=n; ++i) {
					
				   double v1=a.getV(j,i);
				   double v2=a.getV(pivot,i);
				   a.setV(j,i,v2);
				   a.setV(pivot,i,v1);
				   
				   //interschimb liniile si in matricea inversa
				   v1=inv.getV(j,i);
			       v2=inv.getV(pivot,i);
				   inv.setV(j,i,v2);
				   inv.setV(pivot,i,v1);
				   
				}
			}
			
			//modific coloanele j..n
			for (int i=j+1; i<=n; ++i) {
				
				double coef=a.getV(i,j)/a.getV(j,j);
				
				for (int k=1; k<=n; ++k){
				   double newv=a.getV(i,k)-a.getV(j,k)*coef;
				   a.setV(i,k,newv);
				   
				   //la fel si in matricea simetrica
				   newv=inv.getV(i,k)-inv.getV(j,k)*coef;
				   inv.setV(i,k,newv);
				   
				}
				
			}
			
		}

		//acum am matricea a in forma superior triunghiulara si pot calcula determinantul
		det=a.getV(1,1);
		for (int i=2; i<=n; ++i) det*=a.getV(i,i);
		det*=d_semn;
		
		if (fabs(det)<eps) det=0;
		else {//exista matricea inversa => calculez matricea inversa pina la urma rezolvind n sisteme de ecuatii
			
			for (int j=1; j<=n; ++j) {//rezolv sistemul A*x=Inv[j] si inlocuiesc coloana j cu solutia x
				
				for (int i=n; i>=1; --i) {
					
					double coef_xc=a.getV(i,i);
					double sum=0;
					for (int k=i+1; k<=n; ++k) sum+=a.getV(i,k)*inv.getV(k,j);
					
					double xc=(inv.getV(i,j)-sum)/coef_xc;
					
					inv.setV(i,j,xc);
				}
				
			}
			
		 }
	}//end inversa


};

Matrice operator+(Matrice& m1, Matrice& m2) {

	int l1=m1.getN();
	int l2=m2.getN();
	int c1=m1.getM();
	int c2=m2.getM();

	if (l1!=l1 || c1!=c2) {
    	Matrice rez(0,0,0);
	    return rez;
	}
	else {
		Matrice rez(l1,c1,0);

		for (int i=1; i<=l1; ++i)
		 for (int j=1; j<=c1; ++j)
		 rez.setV(i,j,m1.getV(i,j)+m2.getV(i,j));

		return rez;

	}

}

Matrice operator-(Matrice& m1, Matrice& m2) {

	int l1=m1.getN();
	int l2=m2.getN();
	int c1=m1.getM();
	int c2=m2.getM();

	if (l1!=l1 || c1!=c2) {
    	Matrice rez(0,0,0);
	    return rez;
	}
	else {
		Matrice rez(l1,c1,0);

		for (int i=1; i<=l1; ++i)
		 for (int j=1; j<=c1; ++j)
		 rez.setV(i,j,m1.getV(i,j)-m2.getV(i,j));

		return rez;

	}

}

Matrice operator*(Matrice& m1, Matrice& m2) {

	int l1=m1.getN();
	int l2=m2.getN();
	int c1=m1.getM();
	int c2=m2.getM();

	if (c1!=l2) {

		Matrice rez(0,0,0);
	    return rez;

    }
    else {
    	Matrice rez(l1,c2,0);

    	for (int i=1; i<=l1; ++i)
		 for (int j=1; j<=c2; ++j)
		  for (int k=1; k<=c1; ++k)
		   rez.addV(i,j,m1.getV(i,k)*m2.getV(k,j));

		return rez;
    }
}

int main(void) {

	srand(time(NULL));
	
	//random test
	int n=3;
//	Matrice a(n,n,1);
	Matrice a(cin);
	Matrice b=a.cpy();
	Matrice inv(n,n,0);
	double eps=0.0000000001;
	double det;
	
	cout<<"A initial:\n";
	a.print(cout);
	cout<<"\n\n";
	
	Matrice::find_inverse(a,inv,det,eps);
	
	cout<<setprecision(10)<<fixed<<"Det= "<<det<<"\n\n";
	
	cout<<"A=\n";
	a.print(cout);
	cout<<"\n";
	
	cout<<"Inv=\n";
	inv.print(cout);
	cout<<"\n";
	
	//testez eraorea
	Matrice In(n,n,0);
	for (int i=1; i<=n; ++i) In.setV(i,i,1.0);
	
	Matrice test=b*inv;
	test=test-In;
	
	double err=0;
	for (int j=1; j<=n; ++j) {
		double errc=0;
		for (int i=1; i<=n; ++i) errc+=fabs(test.getV(i,j));
		err=max(err,errc);
	}
	
	cout<<"Eroare="<<err<<"\n";

	return 0;
}
