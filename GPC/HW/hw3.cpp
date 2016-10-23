#include<iostream>
#include<algorithm>
#include <GL/glut.h>
using namespace std;

#define dim 300
#define PI 3.1415926535897932384626433832795

unsigned char prevKey;
double width = dim;
double height = dim;

class Grila{
    int n;
    int m;
    double lat;
    double eps;
    int cx;
    int cy;

public:

    Grila(int n=20,int m=20,int cx = 0,int cy=0,double eps=0.1){
        this->n=n;
        this->m=m;
        this->eps=eps;
        lat=min( (2.0-2.0*eps)/(double)m, (2.0-2.0*eps)/(double)n );
        this->cx=cx;
        this->cy=cy;
    }

    void draw() {

        glColor3f(0.1, 0.1, 0.1);

        double nx=1.0;
        double ny=1.0;

        if (width<height) ny=height/width;
        else nx=width/height;

        double y=eps-1.0;

        for (int i=0; i<=n; ++i) {
            glBegin(GL_LINES);
            glVertex2f((eps-1.0)/nx,y/ny);
            glVertex2f((eps-1.0+lat*m)/nx,y/ny);
            glEnd();
            y+=lat;
        }

        double x=eps-1.0;
        for (int i=0; i<=m; ++i) {
            glBegin(GL_LINES);
            glVertex2f(x/nx,(eps-1.0)/ny);
            glVertex2f(x/nx,(eps-1.0+lat*n)/ny);
            glEnd();
            x+=lat;
        }

    }

    double getRealX(double x){
        double nx=1.0;
        x+=cx;
        if (width>height) nx=width/height;
        double rx=(eps-1.0+x*lat)/nx;

        return rx;

    }

    double getRealY(double y){
        double ny=1.0;
        y+=cy;
        if (width<height) ny=height/width;
        double ry=(eps-1.0+y*lat)/ny;

        return ry;
    }

    void drawPixel(int x, int y) {
        //draw circle with center in x y and radius r

        double nx=1.0;
        double ny=1.0;
        x+=cx;
        y+=cy;

        if (width<height) ny=height/width;
        else nx=width/height;

        double r=lat/5.0;
        double xr=(eps-1.0+lat*x)/nx;
        double yr=(eps-1.0+lat*y)/ny;

        glColor3f(1,0.1,0.1);
        glPolygonMode(GL_FRONT,GL_FILL);
        glBegin(GL_POLYGON);
        for (int teta=0; teta<=360; teta+=5) {

            double rad=(double)teta*PI/180.0;
            double xc=r*cos(rad);
            double yc=r*sin(rad);

            glVertex2f(xc/nx+xr,yc/ny+yr);
        }
        glEnd();
    }

    void drawSegment(int x1,int y1,int x2, int y2) {

        glColor3f(1,0.1,0.1);
        glLineWidth(3);
        glBegin(GL_LINES);

        glVertex2f(getRealX(x1),getRealY(y1));
        glVertex2f(getRealX(x2),getRealY(y2));

        glEnd();
        glLineWidth(1);

    }

    //TO DO:
    void drawCircle(double x, double y, double r){
        //draw circle with center in x y and radius r

        double nx=1.0;
        double ny=1.0;
        x+=cx;
        y+=cy;

        if (width<height) ny=height/width;
        else nx=width/height;

        double xr=(eps-1.0+lat*x)/nx;
        double yr=(eps-1.0+lat*y)/ny;

        glColor3f(1,0.1,0.1);
        glLineWidth(3);
        glBegin(GL_POLYGON);
        for (int teta=0; teta<=360; teta+=5) {

            double rad=(double)teta*PI/180.0;
            double xc=r*cos(rad);
            double yc=r*sin(rad);

            glVertex2f(xc/nx+xr,yc/ny+yr);
        }
        glLineWidth(1);
        glEnd();
    }

    void drawLinePixels(int x1, int y1, int x2, int y2,bool zoom) {

        if (x1>x2) { swap(x1,x2); swap(y1,y2); }

        if (y1<y2) {

            if ( y2-y1 <= x2-x1)
            {
                int dx = x2 - x1;
                int dy = y2 - y1;
                int d = 2 * dy - dx;
                int dE = 2 * dy;
                int dNE = 2 * (dy - dx);
                int x = x1, y = y1;
                drawPixel(x, y);
                if (zoom) { drawPixel(x,y+1); drawPixel(x,y-1);}

                while (x < x2)
                {
                    if (d <= 0)
                    {
                        d += dE;
                        x++;
                    }
                    else
                    {
                        d += dNE;
                        x++;
                        y++;
                    }
                    drawPixel(x, y);
                    if (zoom) { drawPixel(x,y+1); drawPixel(x,y-1); }
                }

            }
            else
            {
                int dx = x2 - x1;
                int dy = y2 - y1;
                int d = dy - 2*dx;
                int dN = -2 * dx;
                int dNE = 2 * (dy - dx);
                int x = x1, y = y1;
                drawPixel(x, y);
                if (zoom) { drawPixel(x+1,y); drawPixel(x-1,y);}

                while (y < y2)
                {
                    if (d <= 0)
                    {
                        d += dNE;
                        x++;
                        y++;
                    }
                    else
                    {
                        d += dN;
                        y++;
                    }
                    drawPixel(x, y);
                    if (zoom) { drawPixel(x+1,y); drawPixel(x-1,y); }
                }
            }

        }
        else {

            if (y1-y2<=x2-x1)
            {
                int dx = x2 - x1;
                int dy = y2 - y1;
                int d = 2 * dy + dx;
                int dE = 2 * dy;
                int dSE = 2 * (dy + dx);
                int x = x1, y = y1;
                drawPixel(x, y);
                if (zoom) { drawPixel(x,y+1); drawPixel(x,y-1); }

                while (x < x2)
                {
                    if (d > 0)
                    {
                        d += dE;
                        x++;
                    }
                    else
                    {
                        d += dSE;
                        x++;
                        y--;
                    }
                    drawPixel(x, y);
                    if (zoom) { drawPixel(x,y+1); drawPixel(x,y-1); }
                }

            }
            else
            {
                int dx = x2 - x1;
                int dy = y1 - y2;
                int d = 2*dx - dy;
                int dS = 2 * dx;
                int dSE = 2 * (dx-dy);
                int x = x1, y = y1;
                drawPixel(x, y);
                if (zoom) { drawPixel(x-1,y); drawPixel(x+1,y); }

                while (y > y2)
                {
                    if (d > 0)
                    {
                        d += dSE;
                        x++;
                        y--;
                    }
                    else
                    {
                        d += dS;
                        y--;
                    }
                    drawPixel(x, y);
                    if (zoom) { drawPixel(x+1,y); drawPixel(x-1,y); }
                }

            }
        }
    }
};

void Display1(){
    //creaza un obiect de tip grila carteziana si deseneaza-l
    Grila *g=new Grila(20,20,2,2,0.1);
    g->draw();

    g->drawSegment(0,0,15,7);
    g->drawLinePixels(0,0,15,7,0);

    g->drawSegment(0,15,15,10);
    g->drawLinePixels(0,15,15,10,1);

    g->drawSegment(0,0,7,10);
    g->drawLinePixels(0,0,7,10,0);

    g->drawSegment(0,10,2,3);
    g->drawLinePixels(0,10,2,3,0);
}

void Display2(){
    Grila *g=new Grila(20,20,0,0,0.1);
    g->draw();

    g->drawCircle(10,10, 5);
}

void Init(void) {
    glClearColor(1.0, 1.0, 1.0, 1.0);
    glLineWidth(1);
    glPolygonMode(GL_FRONT, GL_LINE);
}

void Display(void) {
    glClear(GL_COLOR_BUFFER_BIT);

    switch (prevKey) {
        case '1':
            Display1();
            break;
        case '2':
            Display2();
            break;
        default:
            break;
    }

    glFlush();
}

void Reshape(int w, int h) {
    width = w;
    height = h;
    glViewport(0, 0, (GLsizei)w, (GLsizei)h);
}

void KeyboardFunc(unsigned char key, int x, int y) {
    prevKey = key;
    if (key == 27) // escape
        exit(0);
    glutPostRedisplay();
}

void MouseFunc(int button, int state, int x, int y) {
}

int main(int argc, char** argv) {

    glutInit(&argc, argv);

    glutInitWindowSize(dim, dim);

    glutInitWindowPosition(100, 100);

    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);

    glutCreateWindow(argv[0]);

    Init();

    glutReshapeFunc(Reshape);

    glutKeyboardFunc(KeyboardFunc);

    glutMouseFunc(MouseFunc);

    glutDisplayFunc(Display);

    glutMainLoop();

    return 0;
}