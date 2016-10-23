#include <hw1.h>

static double inf=1000000000;
static double mic=1e-30;

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
