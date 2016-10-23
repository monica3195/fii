#include <GL/glut.h>

#include <stdio.h>
#include <math.h>
#include <assert.h>
#include <float.h>
//#include <iostream>

// dimensiunea ferestrei in pixeli
#define dim 300
// numarul maxim de iteratii pentru testarea apartenentei la mult.Julia-Fatou
#define NRITER_JF 5000
#define NRITER_MB 500
// modulul maxim pentru testarea apartenentei la mult.Julia-Fatou
#define MODMAX_JF 10000000
#define MODMAX_MB 2.0
// ratii ptr. CJuliaFatou
#define RX_JF 0.01
#define RY_JF 0.01

#define RX_MB 0.01
#define RY_MB 0.01

unsigned char prevKey;

int cnt[500];

//
class C2coord
{
public:
    C2coord()
    {
        m.x = m.y = 0;
    }

    C2coord(double x, double y)
    {
        m.x = x;
        m.y = y;
    }

    C2coord(C2coord &p)
    {
        m.x = p.m.x;
        m.y = p.m.y;
    }

    C2coord &operator=(C2coord &p)
    {
        m.x = p.m.x;
        m.y = p.m.y;
        return *this;
    }

    int operator==(C2coord &p)
    {
        return ((m.x == p.m.x) && (m.y == p.m.y));
    }

protected:
    struct SDate
    {
        double x,y;
    } m;
};

class CPunct : public C2coord
{
public:
    CPunct() : C2coord(0.0, 0.0)
    {}

    CPunct(double x, double y) : C2coord(x, y)
    {}

    CPunct& operator=(CPunct& p)
    {
        m.x = p.m.x;
        m.y = p.m.y;
        return *this;
    }

    void getxy(double &x, double &y)
    {
        x = m.x;
        y = m.y;
    }

    int operator==(CPunct &p)
    {
        return ((m.x == p.m.x) && (m.y == p.m.y));
    }

    void marcheaza()
    {
        glBegin(GL_POINTS);
        glVertex2d(m.x, m.y);
        glEnd();
    }

    void print(FILE *fis)
    {
        fprintf(fis, "(%+f,%+f)", m.x, m.y);
    }

};

class CVector : public C2coord
{
public:
    CVector() : C2coord(0.0, 0.0)
    {
        normalizare();
    }

    CVector(double x, double y) : C2coord(x, y)
    {
        normalizare();
    }

    CVector &operator=(CVector &p)
    {
        m.x = p.m.x;
        m.y = p.m.y;
        return *this;
    }

    int operator==(CVector &p)
    {
        return ((m.x == p.m.x) && (m.y == p.m.y));
    }

    CPunct getDest(CPunct &orig, double lungime)
    {
        double x, y;
        orig.getxy(x, y);
        CPunct p(x + m.x * lungime, y + m.y * lungime);
        return p;
    }

    void rotatie(double grade)
    {
        double x = m.x;
        double y = m.y;
        double t = 2 * (4.0 * atan(1)) * grade / 360.0;
        m.x = x * cos(t) - y * sin(t);
        m.y = x * sin(t) + y * cos(t);
        normalizare();
    }

    void deseneaza(CPunct p, double lungime)
    {
        double x, y;
        p.getxy(x, y);
        glColor3f(1.0, 0.1, 0.1);
        glBegin(GL_LINE_STRIP);
        glVertex2d(x, y);
        glVertex2d(x + m.x * lungime, y + m.y * lungime);
        glEnd();
    }

    void print(FILE *fis)
    {
        fprintf(fis, "%+fi %+fj", C2coord::m.x, C2coord::m.y);
    }

private:
    void normalizare()
    {
        double d = sqrt(C2coord::m.x * C2coord::m.x + C2coord::m.y * C2coord::m.y);
        if (d != 0.0)
        {
            C2coord::m.x = C2coord::m.x * 1.0 / d;
            C2coord::m.y = C2coord::m.y * 1.0 / d;
        }
    }
};

class CCurbaKoch
{
public:
    void segmentKoch(double lungime, int nivel, CPunct &p, CVector v)
    {
        CPunct p1;
        if (nivel == 0)
        {
            v.deseneaza(p, lungime);
        }
        else
        {
            //    v.print(stderr);
            //    fprintf(stderr, "\n");
            segmentKoch(lungime / 3.0, nivel - 1, p, v);
            p1 = v.getDest(p, lungime / 3.0);
            v.rotatie(60);
            //    v.print(stderr);
            //    fprintf(stderr, "\n");
            segmentKoch(lungime / 3.0, nivel - 1, p1, v);
            p1 = v.getDest(p1, lungime / 3.0);
            v.rotatie(-120);
            //    v.print(stderr);
            //    fprintf(stderr, "\n");
            segmentKoch(lungime / 3.0, nivel - 1, p1, v);
            p1 = v.getDest(p1, lungime / 3.0);
            v.rotatie(60);
            //    v.print(stderr);
            //    fprintf(stderr, "\n");
            segmentKoch(lungime / 3.0, nivel - 1, p1, v);
        }
    }

    void afisare(double lungime, int nivel)
    {
        CVector v1(sqrt(3.0)/2.0, 0.5);
        CPunct p1(-1.0, 0.0);

        CVector v2(0.0, -1.0);
        CPunct p2(0.5, sqrt(3.0)/2.0);

        CVector v3(-sqrt(3.0)/2.0, 0.5);
        CPunct p3(0.5, -sqrt(3.0)/2.0);

        segmentKoch(lungime, nivel, p1, v1);
        segmentKoch(lungime, nivel, p2, v2);
        segmentKoch(lungime, nivel, p3, v3);
    }
};

class CArboreBinar
{
public:
    void arboreBinar(double lungime, int nivel, CPunct &p, CVector v)
    {
        CPunct p1;
        if (nivel == 0)
        {
            v.deseneaza(p, lungime);
        }
        else
        {
            arboreBinar(lungime, nivel - 1, p, v);
            p1 = v.getDest(p, lungime);

            v.rotatie(-45);
            arboreBinar(lungime / 2.0, nivel - 1, p1, v);

            v.rotatie(90);
            arboreBinar(lungime / 2.0, nivel - 1, p1, v);
        }
    }

    void afisare(double lungime, int nivel)
    {
        CVector v(0.0, -1.0);
        CPunct p(0.0, 1.0);

        arboreBinar(lungime, nivel, p, v);
    }
};

class CArborePerron
{
public:
    void arborePerron(double lungime,
                      int nivel,
                      double factordiviziune,
                      CPunct p,
                      CVector v)
    {
        assert(factordiviziune != 0);
        CPunct p1, p2;
        if (nivel == 0)
        {
        }
        else
        {
            v.rotatie(30);
            v.deseneaza(p, lungime);
            p1 = v.getDest(p, lungime);
            arborePerron(lungime * factordiviziune, nivel - 1, factordiviziune, p1, v);

            v.rotatie(-90);
            v.deseneaza(p, lungime);
            p1 = v.getDest(p, lungime);
            p2 = p1;

            v.rotatie(-30);
            v.deseneaza(p1, lungime);
            p1 = v.getDest(p1, lungime);
            arborePerron(lungime * factordiviziune, nivel - 1, factordiviziune, p1, v);

            p1 = p2;
            v.rotatie(90);
            v.deseneaza(p1, lungime);
            p1 = v.getDest(p1, lungime);
            p2 = p1;

            v.rotatie(30);
            v.deseneaza(p1, lungime);
            p1 = v.getDest(p1, lungime);
            arborePerron(lungime * factordiviziune, nivel - 1, factordiviziune, p1, v);

            p1 = p2;
            v.rotatie(-90);
            v.deseneaza(p1, lungime);
            p1 = v.getDest(p1, lungime);
            arborePerron(lungime * factordiviziune, nivel - 1, factordiviziune, p1, v);
        }
    }

    void afisare(double lungime, int nivel)
    {
        CVector v(0.0, 1.0);
        CPunct p(0.0, -1.0);

        v.deseneaza(p, 0.25);
        p = v.getDest(p, 0.25);
        arborePerron(lungime, nivel, 0.4, p, v);
    }
};

class CCurbaHilbert
{
public:
    void curbaHilbert(double lungime, int nivel, CPunct &p, CVector &v, int d)
    {
        if (nivel == 0)
        {
        }
        else
        {
            v.rotatie(d * 90);
            curbaHilbert(lungime, nivel - 1, p, v, -d);

            v.deseneaza(p, lungime);
            p = v.getDest(p, lungime);

            v.rotatie(-d * 90);
            curbaHilbert(lungime, nivel - 1, p, v, d);

            v.deseneaza(p, lungime);
            p = v.getDest(p, lungime);

            curbaHilbert(lungime, nivel - 1, p, v, d);

            v.rotatie(-d * 90);
            v.deseneaza(p, lungime);
            p = v.getDest(p, lungime);

            curbaHilbert(lungime, nivel - 1, p, v, -d);

            v.rotatie(d * 90);
        }
    }

    void afisare(double lungime, int nivel)
    {
        CVector v(0.0, 1.0);
        CPunct p(0.0, 0.0);

        curbaHilbert(lungime, nivel, p, v, 1);
    }
};

class Img1{
public:
    void deseneaza(CPunct colt, CVector vec, double lungime, int nivel) {

        if (nivel==0) {//desenez un patrat din colt de latura l

            double x,y;
            colt.getxy(x,y);
            CPunct paux(x+lungime/3.0,y+lungime/3.0);

            for (int i=1; i<=4; ++i)
            {
                vec.deseneaza(paux,lungime/3.0);
                paux=vec.getDest(paux,lungime/3.0);
                vec.rotatie(90);
            }

        }
        else {//ma duc recursiv

            double x,y;
            colt.getxy(x,y);

            CPunct p1(x,y), p2(x,y+lungime/3.0), p3(x,y+2*lungime/3.0), p4(x+lungime/3.0,y+2*lungime/3.0);
            CPunct p5(x+2*lungime/3.0,y+2*lungime/3.0), p6(x+2*lungime/3.0,y+lungime/3.0), p7(x+2*lungime/3.0,y), p8(x+lungime/3.0,y);

            deseneaza(p1,vec,lungime/3.0,nivel-1);
            deseneaza(p2,vec,lungime/3.0,nivel-1);
            deseneaza(p3,vec,lungime/3.0,nivel-1);
            deseneaza(p4,vec,lungime/3.0,nivel-1);
            deseneaza(p5,vec,lungime/3.0,nivel-1);
            deseneaza(p6,vec,lungime/3.0,nivel-1);
            deseneaza(p7,vec,lungime/3.0,nivel-1);
            deseneaza(p8,vec,lungime/3.0,nivel-1);

            CPunct p9(x+lungime/3.0,y+lungime/3.0);

            for (int i=1; i<=4; ++i)
            {
                vec.deseneaza(p9,lungime/3.0);
                p9=vec.getDest(p9,lungime/3.0);
                vec.rotatie(90);
            }
        }

    }

    void afisare(double lungime, int nivel) {

        //desenez nivelul 0
        CPunct colt(-lungime/2.0,-lungime/2.0);
        CVector vec(1.0,0.0);

        for (int i=1; i<=4; ++i)
        {
            vec.deseneaza(colt,lungime);
            colt=vec.getDest(colt,lungime);
            vec.rotatie(90);
        }

        //desenez celelalte nivele recursiv
        deseneaza(colt,vec,lungime,nivel);

    }
};

class Img2{
public:

    void deseneaza(CPunct p,CVector vec,double lungime, int nivel){

        if (nivel==0) {

            vec.deseneaza(p,lungime);
            p=vec.getDest(p,lungime);
            vec.rotatie(-45);
            vec.deseneaza(p,lungime*3.0);

            vec.rotatie(90);
            vec.deseneaza(p,lungime*3.0);
            p=vec.getDest(p,lungime*3.0);
            vec.rotatie(15);
            vec.deseneaza(p,lungime*3.0);

            vec.rotatie(-60);
            vec.deseneaza(p,lungime*3.0);
            p=vec.getDest(p,lungime*3.0);
            vec.rotatie(30);
            vec.deseneaza(p,lungime*1.5);

            vec.rotatie(-120);
            vec.deseneaza(p,lungime*1.5);

        }
        else {

            vec.deseneaza(p,lungime);
            p=vec.getDest(p,lungime);

            vec.rotatie(-45);
            vec.deseneaza(p,lungime*3.0);

            CPunct p1=vec.getDest(p,lungime*3.0);
            deseneaza(p1,vec,lungime/3.0,nivel-1);

            vec.rotatie(90);
            vec.deseneaza(p,lungime*3.0);
            p=vec.getDest(p,lungime*3.0);
            vec.rotatie(15);
            vec.deseneaza(p,lungime*3.0);

            p1=vec.getDest(p,lungime*3.0);
            deseneaza(p1,vec,lungime/3.0,nivel-1);

            vec.rotatie(-60);
            vec.deseneaza(p,lungime*3.0);
            p=vec.getDest(p,lungime*3.0);
            vec.rotatie(30);
            vec.deseneaza(p,lungime*1.5);

            p1=vec.getDest(p,lungime*1.5);
            deseneaza(p1,vec,lungime/3.0,nivel-1);

            vec.rotatie(-120);
            vec.deseneaza(p,lungime*1.5);

            p1=vec.getDest(p,lungime*1.5);
            deseneaza(p1,vec,lungime/3.0,nivel-1);
        }

    }

    void afisare(double lungime, double nivel) {

        CPunct st(-1.0/3.0,0.95);
        CVector vec(0,-1.0);

        deseneaza(st,vec,lungime,nivel);
    }

};

double PI=3.1415926535897932384626433832795;

class Img3 {
public:
    void deseneaza(CPunct p,CVector vec,double lungime,int nivel) {

        if (nivel==0) {
            vec.deseneaza(p,lungime);
        }
        else {
            double alpha=PI/3.0;
            double x=(double)lungime/(2.0*cos(alpha)+1.0);

            vec.rotatie(180.0*alpha/PI);
            CPunct p1=vec.getDest(p,x);
            vec.rotatie(180);
            deseneaza(p1,vec,x,nivel-1);
            vec.rotatie(180);

            p=vec.getDest(p,x);
            vec.rotatie(-180.0*alpha/PI);
            deseneaza(p,vec,x,nivel-1);

            p=vec.getDest(p,x);
            vec.rotatie(-180.0*alpha/PI);
            p=vec.getDest(p,x);
            vec.rotatie(-180);
            deseneaza(p,vec,x,nivel-1);

        }

    }

    void afisare(double lungime,int nivel) {

        double alpha=PI/3.0;
        if (nivel==0) {

            double l1=(2.0*lungime*cos(alpha)+lungime);
            CPunct p(0.1,0.5);
            CVector v(0.0,1.0);
            v.rotatie(180.0*alpha/PI);

            p=v.getDest(p,lungime);
            v.rotatie(-180.0*alpha/PI+180);
            v.deseneaza(p,l1);

            return;

        }

        --nivel;

        CPunct p(0.1,0.5);
        CVector v(0.0,1.0);
        v.rotatie(180.0*alpha/PI);

        deseneaza(p,v,lungime,nivel);

        v.rotatie(-180.0*alpha/PI-180);
        deseneaza(p,v,lungime,nivel);
        p=v.getDest(p,lungime);

        v.rotatie(-180.0*alpha/PI);
        p=v.getDest(p,lungime);
        v.rotatie(180);
        deseneaza(p,v,lungime,nivel);

    }

};
//


class CComplex {
public:
  CComplex() : re(0.0), im(0.0) {}
  CComplex(double re1, double im1) : re(re1 * 1.0), im(im1 * 1.0) {}
  CComplex(const CComplex &c) : re(c.re), im(c.im) {}
	~CComplex() {}

/*  CComplex &operator=(CComplex &c)
  {
    re = c.re;
    im = c.im;
    return *this;
  }
  */

  double getRe() {return re;}
  void setRe(double re1) {re = re1;}
  
  double getIm() {return im;}
  void setIm(double im1) {im = im1;}

  double getModul() {return sqrt(re * re + im * im);}

  int operator==(CComplex &c1)
  {
    return ((re == c1.re) && (im == c1.im));
  }

  CComplex pow2() 
  {
    CComplex rez;
    rez.re = powl(re * 1.0, 2) - powl(im * 1.0, 2);
    rez.im = 2.0 * re * im;
    return rez;
  }

	friend CComplex operator+(CComplex &c1, CComplex &c2);
	friend CComplex operator*(CComplex &c1, CComplex &c2);

	void print(FILE *f) 
	{
		fprintf(f, "%.20f%+.20f i", re, im);
	}

private:
  double re, im;
};



CComplex operator+(CComplex &c1, CComplex &c2) 
{
	CComplex rez(c1.re + c2.re, c1.im + c2.im);
	return rez;
}

CComplex operator*(CComplex &c1, CComplex &c2) 
{
	CComplex rez(c1.re * c2.re - c1.im * c2.im,
                c1.re * c2.im + c1.im * c2.re);
	return rez;
}

class CJuliaFatou {
public:
  CJuliaFatou()
  {
    // m.c se initializeaza implicit cu 0+0i

    m.nriter = NRITER_JF;
    m.modmax = MODMAX_JF;
  }
  
  CJuliaFatou(CComplex &c)
  {
    m.c = c;
    m.nriter = NRITER_JF;
    m.modmax = MODMAX_JF;
  }
  
  ~CJuliaFatou() {}

  void setmodmax(double v) {assert(v <= MODMAX_JF); m.modmax = v;}
  double getmodmax() {return m.modmax;}

  void setnriter(int v) {assert(v <= NRITER_JF); m.nriter = v;}
  int getnriter() {return m.nriter;}

  // testeaza daca x apartine multimii Julia-Fatou Jc
  // returneaza 0 daca apartine, -1 daca converge finit, +1 daca converge infinit
  int isIn(CComplex &x)
  {
    int rez = 0;
    // tablou in care vor fi memorate valorile procesului iterativ z_n+1 = z_n * z_n + c
    CComplex z0,z1;

    z0 = x;
    for (int i = 1; i < m.nriter; i++)
    {
      z1 = z0 * z0;// + m.c;//TODO :
      z1 = z1 + m.c;
      if (z1 == z0) 
      {
        // x nu apartine m.J-F deoarece procesul iterativ converge finit
        rez = -1;
        break;
      }
      else if (z1.getModul() > m.modmax)
      {
        // x nu apartine m.J-F deoarece procesul iterativ converge la infinit
        rez = 1;
        break;
      }
      z0 = z1;
    }

    return rez;
  }

  // afisarea multimii J-F care intersecteaza multimea argument
  void display(double xmin, double ymin, double xmax, double ymax)
  {
    glPushMatrix();
    glLoadIdentity();

//    glTranslated((xmin + xmax) * 1.0 / (xmin - xmax), (ymin + ymax)  * 1.0 / (ymin - ymax), 0);
//    glScaled(1.0 / (xmax - xmin), 1.0 / (ymax - ymin), 1);
    // afisarea propriu-zisa
    glBegin(GL_POINTS); 
    for(double x = xmin; x <= xmax; x+=RX_JF)
      for(double y = ymin; y <= ymax; y+=RY_JF) 
      {
        CComplex z(x, y);
        int r = isIn(z);
//        z.print(stdout);
        if (r == 0) 
        {
//          fprintf(stdout, "   \n");
          glVertex3d(x,y,0);
        }
        else if (r == -1)
        {
//          fprintf(stdout, "   converge finit\n");
        }
        else if (r == 1)
        {
//          fprintf(stdout, "   converge infinit\n");
        }
      }
    fprintf(stdout, "STOP\n");
    glEnd();

    glPopMatrix();
  }

private:
  struct SDate {
    CComplex c;
    // nr. de iteratii
    int nriter;
    // modulul maxim
    double modmax;
  } m;
};

class Mandelbrot {
public:
  Mandelbrot()
  {
    m.nriter = NRITER_MB;
    m.modmax = MODMAX_MB;
  }
  
  ~Mandelbrot() {}

  void setmodmax(double v) {assert(v <= MODMAX_JF); m.modmax = v;}
  double getmodmax() {return m.modmax;}

  void setnriter(int v) {assert(v <= NRITER_JF); m.nriter = v;}
  int getnriter() {return m.nriter;}

  // testeaza daca x apartine multimii MandelBrot
  // returneaza 0 daca apartine, -1 daca converge finit, +1 daca converge infinit
  int isIn(CComplex &x)
  {
    int rez = 0;
    // tablou in care vor fi memorate valorile procesului iterativ z_n+1 = z_n * z_n + c
    CComplex z0(0,0),z1;

    for (int i = 1; i < m.nriter; i++)
    {
      z1 = z0 * z0; //+ x; //TODO : multiple operation don't work ?
      z1 = z1 + x;
      if (z1 == z0) 
      {
        // x apartine multimii mandelbrot intrucit are limita finita
        rez = 0;
        break;
      }
      else if (z1.getModul() > m.modmax)
      {
        // x nu apartine multimii mandelbrot intrucit depaseste modulul maxim
        rez = i;
        break;
      }
      z0 = z1;
    }

    return rez; //sirul nu converge
  }

  // afisarea MandelBrot care intersecteaza multimea argument
  void display(double xmin, double ymin, double xmax, double ymax)
  {
    glPushMatrix();
    glLoadIdentity();

//    glTranslated((xmin + xmax) * 1.0 / (xmin - xmax), (ymin + ymax)  * 1.0 / (ymin - ymax), 0);
//    glScaled(1.0 / (xmax - xmin), 1.0 / (ymax - ymin), 1);
    // afisarea propriu-zisa
    glBegin(GL_POINTS); 
    for(double x = xmin; x <= xmax; x+=RX_MB)
      for(double y = ymin; y <= ymax; y+=RY_MB) 
      {
        CComplex z(x, y);
        int r = isIn(z);
//        z.print(stdout);
        if (r == 0) 
        {
//          fprintf(stdout, "   \n");
		  printf("%f %f\n",x,y);
          glVertex3d(x*0.7+0.5,y*0.7,0);
        }
        else
        {
//          fprintf(stdout, "   converge infinit\n");
			
			if (r<3) glColor3f(0.9,0.9,0.9);
			else if (r<6) glColor3f(0.7,0.7,0.9);
			else if (r<10) glColor3f(0.5,0.5,0.9);
			else if (r<15) glColor3f(0.1,0.1,0.9);
			else if (r<20) glColor3f(0.7,0.9,0.7);
			else if (r<30) glColor3f(0.5,0.9,0.5);
			else if (r<50) glColor3f(0.1,0.9,0.1);
			else if (r<100) glColor3f(0.9,0.7,0.7);
			else if (r<200) glColor3f(0.9,0.5,0.5);
			else if (r<400) glColor3f(0.9,0.1,0.1);
			else glColor3f(0.1,0.1,0.1);

			++cnt[r];

			glVertex3d(x*0.7+0.5,y*0.7,0);
			glColor3f(1.0,0.1,0.1);
        }
      }
    fprintf(stdout, "STOP\n");
    glEnd();

    glPopMatrix();
  }

private:
  struct SDate {
    // nr. de iteratii
    int nriter;
    // modulul maxim
    double modmax;
  } m;
};

// multimea Julia-Fatou pentru z0 = 0 si c = -0.12375+0.056805i
void Display1() {
  CComplex c(-0.12375, 0.056805);
  CJuliaFatou cjf(c);

  glColor3f(1.0, 0.1, 0.1);
  cjf.setnriter(30);
  cjf.display(-0.8, -0.4, 0.8, 0.4);
}

// multimea Julia-Fatou pentru z0 = 0 si c = -0.012+0.74i
void Display2() {
  CComplex c(-0.012, 0.74);
  CJuliaFatou cjf(c);

  glColor3f(1.0, 0.1, 0.1);
  cjf.setnriter(30);
  cjf.display(-1, -1, 1, 1);
}

void Display3(){
	Mandelbrot cm;

	glColor3f(1.0,0.1,0.1);
	cm.display(-2, -2, 2, 2);

	/*for (int i=1; i<=500; ++i)
		if (cnt[i]>0) printf("i=%d ; nr=%d\n",i,cnt[i]);*/
}

void Init(void) {

   glClearColor(1.0,1.0,1.0,1.0);

   glLineWidth(1);

   glPointSize(3);

   glPolygonMode(GL_FRONT, GL_LINE);
}

void Display(void) {
  switch(prevKey) {
  case '1':
    glClear(GL_COLOR_BUFFER_BIT);
    Display1();
    break;
  case '2':
    glClear(GL_COLOR_BUFFER_BIT);
    Display2();
    break;
  case '3':
    glClear(GL_COLOR_BUFFER_BIT);
    Display3();
    break;
  default:
    break;
  }

  glFlush();
}

void Reshape(int w, int h) {
   glViewport(0, 0, (GLsizei) w, (GLsizei) h);
}

void KeyboardFunc(unsigned char key, int x, int y) {
   prevKey = key;
  
   glutPostRedisplay();
}

void MouseFunc(int button, int state, int x, int y) {
}

int main(int argc, char** argv) {

   glutInit(&argc, argv);
   
   glutInitWindowSize(dim, dim);

   glutInitWindowPosition(100, 100);

   glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB);

   glutCreateWindow (argv[0]);

   Init();

   glutReshapeFunc(Reshape);
   
   glutKeyboardFunc(KeyboardFunc);
   
   glutMouseFunc(MouseFunc);

   glutDisplayFunc(Display);
   
   glutMainLoop();

   return 0;
}