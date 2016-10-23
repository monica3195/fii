#include<iostream>
#include<fstream>
#include<complex.h>
#include<cmath>
#include<stdlib.h>
#include<time.h>
#include<iomanip>
using namespace std;

int n,i;
double p[20];
double sol[20];

double f(double v) {
	double b0=p[0];
	for (int i=1; i<=n; ++i) b0=p[i]+b0*v;
	return b0;
}

double random_float(double l, double r) {
	double d=rand()%10000;
	return l+d*(r-l)/10000.0;
}


double F(double x) {

	//return x*x-4.0*x+3.0;
	 return x*x+exp(x);
	
}

double g1(double x, double h) {
   return (3.0*F(x)-4.0*F(x-h)+F(x-2.0*h))/2.0*h;
}

double g2(double x, double h) {
   return ( -F(x+2.0*h)+8.0*F(x+h)-8.0*F(x-h)+F(x-2*h) )/(12.0*h) ;
}

int main(void) {
	ifstream cin("input.in");
	srand(time(NULL));
	
	cin>>n;
	for (i=0; i<=n; ++i) cin>>p[i];
	
	//calculez intervalul unde se afla radacinile
	double R=-1000000000;
	
	for (i=1; i<=n; ++i) R=max(R,fabs(p[i]));
	R=(fabs(p[0])+R)/fabs(p[0]);
	
	cout<<"Intervalul in care se afla toate solutiile reale este: ["<<-R<<" , "<<R<<"]\n";
	
	int nrsol=0;
	
	//Muller max
	cout<<"First try:\n";
	for (double l=-R; l<=R; l+=2.0*R/1000.0){
		
		double x0, x1, x2, x3; //generez aleator aceste elemente pe intervalul [l,l+2*R/1000)
		double a,b,c,sigma0,sigma1,delta_x;
		double eps=0.00000000001;
		int k=0;
		
		x0=random_float(l,l+=2.0*R/1000.0);
		x1=random_float(l,l+=2.0*R/1000.0);
		x2=random_float(l,l+=2.0*R/1000.0);
		
		//incerc sa aproximez o solutie incepind de la elementele generate

		do {
			
			double pk=f(x2), pk_1=f(x1), pk_2=f(x0);

  	       /* double w=(pk_1-pk)/(x1-x2) + (pk_2-pk)/(x0-x2) - (pk_2-pk_1)/(x0-x1);
  	        double faux=(pk_2-pk_1)/((x0-x1)*(x0-x2)) - (pk_1-pk)/((x1-x2)*(x0-x2));
  	        
			if (w*w-4*pk*faux<0) break;
			double aux=max(w-sqrt(w*w-4*pk*faux),w+sqrt(w*w-4*pk*faux));*/
			
			sigma0=( pk_1 - pk_2 ) / (x1-x0);
			sigma1=( pk - pk_1 ) / (x2-x1);
			
			a=(sigma1-sigma0)/(x2-x0);
			b=a*(x2-x0)+sigma1;
			c=pk;
			
			if (b*b-4.0*a*c<0.0) break;
			double aux=max(b+sqrt(b*b-4.0*a*c),b-sqrt(b*b-4.0*a*c));

			if (fabs(aux)<eps) break;
			
			delta_x=2.0*pk/aux;
			x3=x2-delta_x;
			++k;
			x0=x1; x1=x2; x2=x3;
			
		} while ( fabs(delta_x)>=eps && k<1000 && fabs(delta_x)<100000000.0 );
		
		if (fabs(f(x2))<eps) {
			
			bool ok=1;
			for (int j=1; j<=nrsol; ++j)
			 if (fabs(x2-sol[j])<eps*1000) { ok=0; break; }

			if (ok) {
				
		        cout<<setprecision(20)<<fixed<<"Solution found: "<<x2<<" P(X)="<<f(x2)<<"\n";
				sol[++nrsol]=x2;
				
			}

	    }
	}
	
	//Muller min
	cout<<"Second try:\n";
	for (double l=-R; l<=R; l+=2.0*R/1000.0){

		double x0, x1, x2, x3; //generez aleator aceste elemente pe intervalul [l,l+2*R/1000)
		double a,b,c,sigma0,sigma1,delta_x;
		double eps=0.00000000001;
		int k=0;

		x0=random_float(l,l+=2.0*R/1000.0);
		x1=random_float(l,l+=2.0*R/1000.0);
		x2=random_float(l,l+=2.0*R/1000.0);

		//incerc sa aproximez o solutie incepind de la elementele generate

		do {

			double pk=f(x2), pk_1=f(x1), pk_2=f(x0);

  	       /* double w=(pk_1-pk)/(x1-x2) + (pk_2-pk)/(x0-x2) - (pk_2-pk_1)/(x0-x1);
  	        double faux=(pk_2-pk_1)/((x0-x1)*(x0-x2)) - (pk_1-pk)/((x1-x2)*(x0-x2));

			if (w*w-4*pk*faux<0) break;
			double aux=max(w-sqrt(w*w-4*pk*faux),w+sqrt(w*w-4*pk*faux));*/

			sigma0=( pk_1 - pk_2 ) / (x1-x0);
			sigma1=( pk - pk_1 ) / (x2-x1);

			a=(sigma1-sigma0)/(x2-x0);
			b=a*(x2-x0)+sigma1;
			c=pk;

			if (b*b-4.0*a*c<0.0) break;
			double aux=min(b+sqrt(b*b-4.0*a*c),b-sqrt(b*b-4.0*a*c));

			if (fabs(aux)<eps) break;

			delta_x=2.0*pk/aux;
			x3=x2-delta_x;
			++k;
			x0=x1; x1=x2; x2=x3;

		} while ( fabs(delta_x)>=eps && k<1000 && fabs(delta_x)<100000000.0 );

		if (fabs(f(x2))<eps) {

			bool ok=1;
			for (int j=1; j<=nrsol; ++j)
			 if (fabs(x2-sol[j])<eps*1000) { ok=0; break; }

			if (ok) {

		        cout<<setprecision(20)<<fixed<<"Solution found: "<<x2<<" P(X)="<<f(x2)<<"\n";
				sol[++nrsol]=x2;

			}

	    }
	}
	
	cout<<"Third try:\n";
	for (double l=-R; l<=R; l+=2.0*R/1000.0){

	   double pk=f(l), pk1=f(l+2.0*R/1000.0);
	   
	   if (pk*pk1<=0.0001) {
	   	
	   	double x=l, y=l+2.0*R/1000.0, eps=0.0001;
	   	
	   	while (y-x>eps) {
	   		double mid=(x+y)/2.0;
	   		double pkmid=f(mid);
	   		double pkx=f(x);
	   		double pky=f(y);
	   		
	   		if ( pkx*pkmid<0 ) y=mid-eps;
	   		else if (pkmid*pky<0) x=mid+eps;
	   		else break;
	   		
	   	}
	   	
	   	if (fabs(f(x))<eps*10) {

			bool ok=1;
			for (int j=1; j<=nrsol; ++j)
			 if (fabs(x-sol[j])<eps*1000) { ok=0; break; }

			if (ok) {

		        cout<<setprecision(20)<<fixed<<"Solution found: "<<x<<" P(X)="<<f(x)<<"\n";
				sol[++nrsol]=x;

			}

	    }
	   	
	   }

    }
	
	
	//aproximarea punctelor de minim prin metoda secantei
	{
	double x0, x1, x2, delta_x, h, eps;
	int k=0;

	 x0=-0.3;
	 x1=-0.4;
	 h=0.001;
	 eps=0.0001;
	 
	
	 do {
	 	
	 	if ( fabs(g1(x1,h)-g1(x0,h))<=0.000000001 ) delta_x=0.00001;
	 	else delta_x=(x1-x0)*g1(x1,h)/( g1(x1,h)-g1(x0,h) );
	 	x2=x1-delta_x;
	 	++k;
	 	x0=x1; x1=x2;
	 	
	 }while (k<=1000 && fabs(delta_x)>=eps && fabs(delta_x)<=1000000000.0);
	
	if (fabs(delta_x)<0.1) {
		cout<<"Convergenta\n";
		cout<<"X="<<x1<<"\n";
		cout<<"G2(x1)="<<g2(x1,h)<<"\n";
		if (g2(x1,h)>-eps) cout<<"Punct de minim local\n";
		else cout<<"Punct de maxim local\n";
	}
	
    }
	
	return 0;
}
