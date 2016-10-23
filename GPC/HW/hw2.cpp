
#include <GL/glut.h>

//#include <cstdlib>
//#include <algorithm>
#include <cmath>

using namespace std;

#define PI 3.1415926535897932384626433832795

// dimensiunea ferestrei in pixeli
#define dim 300

unsigned char prevKey;

// concoida lui Nicomede (concoida dreptei)
// $x = a + b \cdot cos(t), y = a \cdot tg(t) + b \cdot sin(t)$. sau
// $x = a - b \cdot cos(t), y = a \cdot tg(t) - b \cdot sin(t)$. unde
// $t \in (-\pi / 2, \pi / 2)$
void Display1() {
    double xmax, ymax, xmin, ymin;
    double a = 1, b = 2;
    double pi = 4 * atan(1);
    double ratia = 0.05;

    // calculul valorilor maxime/minime ptr. x si y
    // aceste valori vor fi folosite ulterior la scalare
    xmax = a - b - 1;
    xmin = a + b + 1;
    ymax = ymin = 0;
    for (double t = - pi/2 + ratia; t < pi / 2; t += ratia) {
        double x1, y1, x2, y2;
        x1 = a + b * cos(t);
        xmax = (xmax < x1) ? x1 : xmax;
        xmin = (xmin > x1) ? x1 : xmin;

        x2 = a - b * cos(t);
        xmax = (xmax < x2) ? x2 : xmax;
        xmin = (xmin > x2) ? x2 : xmin;

        y1 = a * tan(t) + b * sin(t);
        ymax = (ymax < y1) ? y1 : ymax;
        ymin = (ymin > y1) ? y1 : ymin;

        y2 = a * tan(t) - b * sin(t);
        ymax = (ymax < y2) ? y2 : ymax;
        ymin = (ymin > y2) ? y2 : ymin;
    }

    xmax = (fabs(xmax) > fabs(xmin)) ? fabs(xmax) : fabs(xmin);
    ymax = (fabs(ymax) > fabs(ymin)) ? fabs(ymax) : fabs(ymin);

    // afisarea punctelor propriu-zise precedata de scalare
    glColor3f(1,0.1,0.1); // rosu
    glBegin(GL_LINE_STRIP);
    for (double t = - pi/2 + ratia; t < pi / 2; t += ratia) {
        double x1, y1, x2, y2;
        x1 = (a + b * cos(t)) / xmax;
        x2 = (a - b * cos(t)) / xmax;
        y1 = (a * tan(t) + b * sin(t)) / ymax;
        y2 = (a * tan(t) - b * sin(t)) / ymax;

        glVertex2f(x1,y1);
    }
    glEnd();

    glBegin(GL_LINE_STRIP);
    for (double t = - pi/2 + ratia; t < pi / 2; t += ratia) {
        double x1, y1, x2, y2;
        x1 = (a + b * cos(t)) / xmax;
        x2 = (a - b * cos(t)) / xmax;
        y1 = (a * tan(t) + b * sin(t)) / ymax;
        y2 = (a * tan(t) - b * sin(t)) / ymax;

        glVertex2f(x2,y2);
    }
    glEnd();
}

// graficul functiei
// $f(x) = \bar sin(x) \bar \cdot e^{-sin(x)}, x \in \langle 0, 8 \cdot \pi \rangle$,
void Display2() {
    double pi = 4 * atan(1);
    double xmax = 8 * pi;
    double ymax = exp(1.1);
    double ratia = 0.05;

    // afisarea punctelor propriu-zise precedata de scalare
    glColor3f(1,0.1,0.1); // rosu
    glBegin(GL_LINE_STRIP);
    for (double x = 0; x < xmax; x += ratia) {
        double x1, y1;
        x1 = x / xmax;
        y1 = (fabs(sin(x)) * exp(-sin(x))) / ymax;

        glVertex2f(x1,y1);
    }
    glEnd();
}

void Display3(){

    glColor3f(1,0.1,0.1); //rosu
    glBegin(GL_LINE_STRIP);

    glVertex2f(0.0,1.0);
    double ratia=0.5;

    for (double x=ratia; x<=25; x+=ratia){
        double y=(int)(x+0.5);
        if (y==0.0) y=1.0;
        glVertex2f(x*0.04,fabs(y-x)/x);
    }

    glEnd();
}

double max(double a, double b) {
    if (a>b) return a;
    return b;
}

void Display4(){

    double xmax=-100000;
    double ymax=-100000;

    double xc, yc;
    double eps=0.1;
    double a=0.3;
    double b=0.2;

    for (double t=-PI; t<=PI; t+=eps){

        xc=2.0*(a*cos(t)+b)*cos(t);
        yc=2.0*(a*cos(t)+b)*sin(t);

        xmax=max(xmax,fabs(xc));
        ymax=max(ymax,fabs(yc));

    }

    glColor3f(1,0.1,0.1); //rosu
    glBegin(GL_LINE_LOOP);

    for (double t=-PI; t<=PI; t+=eps){

        xc=2.0*(a*cos(t)+b)*cos(t);
        yc=2.0*(a*cos(t)+b)*sin(t);

        glVertex2f(xc/xmax,yc/ymax);

    }

    glEnd();

}

void Display5(){
    //to be continued

    double xmax=-100000;
    double ymax=-100000;

    double xc, yc;
    double eps=0.01;
    double a=0.2;

    for (double t=-PI/2.0+eps; t<-PI/6.0; t+=eps){

        if (t==PI/6.0 || t==-PI/6.0) continue;

        xc=a/(4.0*cos(t)*cos(t)-3.0);
        yc=a*tan(t)/(4.0*cos(t)*cos(t)-3.0);

        xmax=max(xmax,fabs(xc));
        ymax=max(ymax,fabs(yc));

    }

    glColor3f(0.1,0.1,1); //albastru
    glBegin(GL_LINE_LOOP);

    glVertex2f(-1.0+eps*eps,1.0-eps*eps);

    for (double t=-PI/2.0+eps; t<-PI/6.0; t+=eps){

        if (t==PI/6.0 || t==-PI/6.0) continue;

        xc=a/(4.0*cos(t)*cos(t)-3.0);
        yc=a*tan(t)/(4.0*cos(t)*cos(t)-3.0);

        glVertex2f(xc,yc);

    }

    glEnd();

    int color=1;

    for (double t=-PI/2.0+eps; t<-PI/6.0-eps; t+=eps){

        if (t>=-PI/2.3 && t<=-PI/4.5) continue;

        if (color%2==1) glColor3f(1,0.1,0.1) ; //rosu
        else glColor3f(1,1,1); //alb

        glBegin(GL_TRIANGLES);

        xc=a/(4.0*cos(t)*cos(t)-3.0);
        yc=a*tan(t)/(4.0*cos(t)*cos(t)-3.0);

        double xn=a/(4.0*cos(t+eps)*cos(t+eps)-3.0);
        double yn=a*tan(t+eps)/(4.0*cos(t+eps)*cos(t+eps)-3.0);

        if (t<=-PI/2.3) { xc-=eps/4.0; xn-=eps/4.0; }

        glVertex2f(xc,yc);
        glVertex2f(xn,yn);
        glVertex2f(-1.0+eps*eps,1.0-eps*eps);

        ++color;

        glEnd();
    }

}

void Display6(){
    double xmax=-100000;
    double ymax=-100000;

    double xc, yc;
    double eps=0.1;
    double a=0.1;
    double b=0.2;

    for (double t=-10.0; t<=10.0; t+=eps){

        xc=a*t-b*sin(t);
        yc=a-b*cos(t);

        xmax=max(xmax,fabs(xc));
        ymax=max(ymax,fabs(yc));

    }

    ymax*=2.0;

    glColor3f(1,0.1,0.1); //rosu
    glBegin(GL_LINE_STRIP);

    for (double t=-10.0; t<=10.0; t+=eps){

        xc=a*t-b*sin(t);
        yc=a-b*cos(t);

        glVertex2f(xc/xmax,yc/ymax);

    }

    glEnd();
}

void Display7(){
    double xmax=-100000;
    double ymax=-100000;

    double xc, yc;
    double eps=0.01;
    double R=0.1;
    double r=0.3;

    for (double t=0.0; t<=2.0*PI; t+=eps){

        xc=(R+r)*cos(r/R*t)-r*cos(t+r/R*t);
        yc=(R+r)*sin(r/R*t)-r*sin(t+r/R*t);

        xmax=max(xmax,fabs(xc));
        ymax=max(ymax,fabs(yc));

    }

    xmax*=1.1;
    ymax*=1.1;

    glColor3f(1,0.1,0.1); //rosu
    glBegin(GL_LINE_STRIP);

    for (double t=0.0; t<=2.0*PI; t+=eps){

        xc=(R+r)*cos(r/R*t)-r*cos(t+r/R*t);
        yc=(R+r)*sin(r/R*t)-r*sin(t+r/R*t);

        glVertex2f(xc/xmax,yc/ymax);

    }

    glEnd();
}

void Display8(){
    double xmax=-100000;
    double ymax=-100000;

    double xc, yc;
    double eps=0.01;
    double R=0.1;
    double r=0.3;

    for (double t=0.0; t<=2.0*PI; t+=eps){

        xc=(R-r)*cos(r/R*t)-r*cos(t-r/R*t);
        yc=(R-r)*sin(r/R*t)-r*sin(t-r/R*t);

        xmax=max(xmax,fabs(xc));
        ymax=max(ymax,fabs(yc));

    }

    xmax*=1.1;
    ymax*=1.1;

    glColor3f(1,0.1,0.1); //rosu
    glBegin(GL_LINE_STRIP);

    for (double t=0.0; t<=2.0*PI; t+=eps){

        xc=(R-r)*cos(r/R*t)-r*cos(t-r/R*t);
        yc=(R-r)*sin(r/R*t)-r*sin(t-r/R*t);

        glVertex2f(xc/xmax,yc/ymax);

    }

    glEnd();
}

void Display9(){
    double xmax=-100000;
    double ymax=-100000;

    double xc, yc;
    double eps=0.01;
    double a=0.4;
    double r;

    for (double t=-PI/4.0+eps; t<PI/4.0; t+=eps){

        r=a*sqrt(2.0*cos(2.0*t));

        xc=r*cos(t);
        yc=r*sin(t);

        xmax=max(xmax,fabs(xc));
        ymax=max(ymax,fabs(yc));

    }

    xmax*=1.1;
    ymax*=2.1;

    glColor3f(1,0.1,0.1); //rosu
    glBegin(GL_LINE_STRIP);

    for (double t=PI/4.0-eps; t>-PI/4.0; t-=eps){

        r=-a*sqrt(2.0*cos(2.0*t));

        xc=r*cos(t);
        yc=r*sin(t);

        glVertex2f(xc/xmax,yc/ymax);

    }

    for (double t=-PI/4.0+eps; t<PI/4.0; t+=eps){

        r=a*sqrt(2.0*cos(2.0*t));

        xc=r*cos(t);
        yc=r*sin(t);

        glVertex2f(xc/xmax,yc/ymax);

    }

    glEnd();
}

void Display10(){

    double xc, yc;
    double eps=0.01;
    double a=0.02;
    double r;

    glColor3f(1,0.1,0.1); //rosu
    glBegin(GL_LINE_STRIP);

    for (double t=eps; t<=100; t+=eps){

        r=a*exp(1.0+t);

        xc=r*cos(t);
        yc=r*sin(t);

        glVertex2f(xc,yc);

    }

    glEnd();
}


void Init(void) {

    glClearColor(1.0,1.0,1.0,1.0);

    glLineWidth(1);

    glPolygonMode(GL_FRONT, GL_LINE);
}

void Display(void) {
    glClear(GL_COLOR_BUFFER_BIT);

    switch(prevKey) {
        case '1':
            Display1();
            break;
        case '2':
            Display2();
            break;
        case '3':
            Display3();
            break;
        case '4':
            Display4();
        case '5':
            Display5();
            break;
        case '6':
            Display6();
            break;
        case '7':
            Display7();
            break;
        case '8':
            Display8();
            break;
        case '9':
            Display9();
            break;
        case '0':
            Display10();
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
//   if (key == 27) // escape
//      exit(0);
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
