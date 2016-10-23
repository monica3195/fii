#define _USE_MATH_DEFINES
#include<math.h>
#include<iostream>
#include<algorithm>
#include<cstring>
#include<string>
#include<iomanip>
using namespace std;

double u=1.0, up=1.0;
double mic=1e-30;
double inf=1000000000;
int m=0;
//double inf=MAX_DOUBLE;

double mytan(double x, double eps) {
	
   double semn=1;
	
   if (x>=0)
       while (x>=2.0*M_PI) x-=2.0*M_PI;
   else
	   while (x<0) x+=2.0*M_PI;
   
   if (x>M_PI && x<3.0*M_PI/2.0) { x=x-M_PI; semn=1; }
   else if (x>=3.0*M_PI/2.0) { x=M_PI*2.0-x; semn=-1; }
   else if (x>=M_PI/2.0) { x=M_PI-x; semn=-1; }
   
   if (x==M_PI/2.0) return inf;

   double f=mic;
   
   double C=f;
   double D=0.0;
   double dj=1.0;
   double b=1.0;
   double a=x;
   
   do {
   	
   	D=b+a*D;
	D=max(D,mic);
	
	C=b+a/C;
	C=max(C,mic);
	
	D=1.0/D;
	dj=C*D;
	f=dj*f;

	if (b==1.0) a=-x*x;
	b+=2.0;

   }while (fabs(dj-1.0)>=eps);
	
   return f*semn;
	
}

int main(void) {
	
	//ex 1
	
	while (1.0+u!=1.0) {
	   ++m;
	   up=u;
	   u/=10.0;
	}
	
	cout<<"M="<<m<<"\n";
	cout<<setprecision(16)<<fixed<<"U="<<up<<"\n";
	
	//ex 2
	double a=1.0;
	double b=up;
	double c=up;
	
	if ( (a+b)+c != a+(b+c) ) cout<<"Adunare neasocoativa\n";
	else cout<<"Adunare asociativa\n";
	
	a=10;
	b=up;
	c=up;
	
	if ( (a*b)*c != a*(b*c) ) cout<<"Inmultire neasociativa\n";
	else cout<<"Inmultire asociativa\n";

	//ex 3
    cout << "M_PI/3.0 = " << M_PI/3.0 << "\n";
	cout<<"My_Tan="<<mytan(M_PI/3.0,0.001)<<"\n";
	cout<<"Mh TAN="<<tan(M_PI/3.0)<<"\n";
	
	return 0;
}
