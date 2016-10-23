#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include "glut.h"
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
	double pi = 4 * atan(1.0);
	double ratia = 0.05;
	double t;

	// calculul valorilor maxime/minime ptr. x si y
	// aceste valori vor fi folosite ulterior la scalare
	xmax = a - b - 1;
	xmin = a + b + 1;
	ymax = ymin = 0;
	for (t = -pi / 2 + ratia; t < pi / 2; t += ratia) {
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
	glColor3f(1, 0.1, 0.1); // rosu
	glBegin(GL_LINE_STRIP);
	for (t = -pi / 2 + ratia; t < pi / 2; t += ratia) {
		double x1, y1, x2, y2;
		x1 = (a + b * cos(t)) / xmax;
		x2 = (a - b * cos(t)) / xmax;
		y1 = (a * tan(t) + b * sin(t)) / ymax;
		y2 = (a * tan(t) - b * sin(t)) / ymax;

		glVertex2f(x1, y1);
	}
	glEnd();

	glBegin(GL_LINE_STRIP);
	for (t = -pi / 2 + ratia; t < pi / 2; t += ratia) {
		double x1, y1, x2, y2;
		x1 = (a + b * cos(t)) / xmax;
		x2 = (a - b * cos(t)) / xmax;
		y1 = (a * tan(t) + b * sin(t)) / ymax;
		y2 = (a * tan(t) - b * sin(t)) / ymax;

		glVertex2f(x2, y2);
	}
	glEnd();
}

// graficul functiei 
// $f(x) = \bar sin(x) \bar \cdot e^{-sin(x)}, x \in \langle 0, 8 \cdot \pi \rangle$, 
void Display2() {
	double pi = 4 * atan(1.0);
	double xmax = 8 * pi;
	double ymax = exp(1.1); //Returns e raised to the specified power.
	double ratia = 0.05;

	// afisarea punctelor propriu-zise precedata de scalare
	glColor3f(1, 0.1, 0.1); // rosu
	glBegin(GL_LINE_STRIP);
	for (double x = 0; x < xmax; x += ratia) {
		double x1, y1;
		x1 = x / xmax;
		y1 = (fabs(sin(x)) * exp(-sin(x))) / ymax;

		glVertex2f(x1, y1);
	}
	glEnd();
}

void Display3(){ //melcul lui Pascal

	double a = 0.3, b = 0.2;
	double pi = 4 * atan(1.0);
	double ratia = 0.005;

	glColor3f(1, 0.1, 0.1); // rosu
	glBegin(GL_LINE_STRIP);
	for (double t = -pi + ratia; t < pi; t += ratia) {
		double x1, y1;
		x1 = 2 * (a*cos(t) + b)*cos(t);
		y1 = 2 * (a*cos(t) + b)*sin(t);
		glVertex2f(x1, y1);
	}
	glEnd();
}

void Display4(){//trisectoarea lui Longchamps

	double pi = 4 * atan(1.0);
	double ratia = 0.05;
	double x, y;

	double a = 0.2;

	glColor3f(0, 0, 0); // negru
	glBegin(GL_LINE_STRIP);
	for (double t = -pi / 2 + ratia; t < pi / 2; t += ratia) {
		if (t != -pi / 6 && t != pi / 6){

			x = a / (4 * cos(t)*cos(t) - 3);
			y = a * tan(t) / (4 * cos(t)*cos(t) - 3);
			if (x<0 && y>0)//stabilesc conturul in cadranul 2
			{

				glVertex2f(x, y);
			}
		}
	}
	glEnd();

	//desenez triunghiurile din ochi
	glColor3f(1, 0.1, 0.1); // rosu
	glBegin(GL_TRIANGLES);// primul triunghi rosu de sus
	double y1 = 0.95;
	double y2 = 0.88;
	double d = 0.06; //distanta dintre triunghiurile rosii
	double D = 0.08; //distanta dintre triunghiurile albe

	glVertex2f(-1.0, 1.0);
	glVertex2f(-0.076, y1);
	glVertex2f(-0.076, y2);

	glEnd();

	for (int i = 1; i <= 5; i++)
	{
		glBegin(GL_TRIANGLES);
		y1 = y2 - D;
		y2 = y1 - d;
		D = D - 0.011;
		d = d - 0.009;

		glVertex2f(-1.0, 1.0);
		glVertex2f(-0.076, y1);
		glVertex2f(-0.076, y2);

		glEnd();
	}

	for (int i = 1; i <= 5; i++)
	{
		glBegin(GL_TRIANGLES);
		y1 = y2 - D;
		y2 = y1 - d;
		D = D - 0.0012;
		d = d - 0.007;

		glVertex2f(-1.0, 1.0);
		glVertex2f(-0.076, y1);
		glVertex2f(-0.076, y2);

		glEnd();

	}

	double dx = 0.06;
	double dy = 0.04;
	double Dx = 0.07;
	double Dy = 0.05;
	y1 = 0.63;
	y2 = 0.59;


	double x1 = -0.95;
	double x2 = -0.88;

	glBegin(GL_TRIANGLES);// primul triunghi rosu din stanga


	glVertex2f(-1.0, 1.0);

	glVertex2f(x2, y2);
	glVertex2f(x1, y1);


	glEnd();

	for (int i = 1; i <= 5; i++){

		glBegin(GL_TRIANGLES);

		x1 = x2 + Dx;
		x2 = x1 + dx;

		y1 = y2 - Dy;
		y2 = y1 - dy;

		glVertex2f(-1.0, 1.0);
		glVertex2f(x2, y2);
		glVertex2f(x1, y1);

		Dx = Dx - 0.005;
		Dy = Dy - 0.009;
		dx = dx - 0.005;
		dy = dy - 0.005;

		glEnd();
	}
}

void Display5(){//cicloida

	double a = 0.1, b = 0.2;
	double pi = 4 * atan(1.0);
	double ratia = 0.05;


	glColor3f(1, 0.1, 0.1); // rosu
	glBegin(GL_LINE_STRIP);
	for (double t = -10 + ratia; t < 10; t += ratia) {
		double x1, y1;
		x1 = (a*t - b*sin(t));

		y1 = (a - b*cos(t));

		glVertex2f(x1, y1);
	}
	glEnd();

}

void Display6(){//epicicloida

	double r = 0.3, R = 0.1;
	double pi = 4 * atan(1.0);
	double ratia = 0.003;

	glColor3f(1, 0.1, 0.1); // rosu
	glBegin(GL_LINE_STRIP);
	for (double t = 0; t <= 2 * pi; t += ratia) {
		double x1, y1;
		x1 = (R + r)*cos((r / R)*t) - (r*cos(t + (r / R)*t)); /// xmax;

		y1 = (R + r)*sin((r / R)*t) - (r*sin(t + (r / R)*t)); /// ymax;
		glVertex2f(x1, y1);
	}
	glEnd();
}

void Display7(){//hipocicloida

	double R = 0.1, r = 0.3;
	double pi = 4 * atan(1.0);
	double ratia = 0.05;

	glColor3f(1, 0.1, 0.1); // rosu
	glBegin(GL_LINE_STRIP);
	for (double t = 0; t < 2 * pi; t += ratia) {
		double x1, y1;
		x1 = (R - r)*cos((r / R)*t) - (r*cos(t - (r / R)*t));// / xmax;
		y1 = (R - r)*sin((r / R)*t) - (r*sin(t - (r / R)*t));// / ymax;

		glVertex2f(x1, y1);
	}
	glEnd();
}

void Display8() {//lemniscata lui Bernoulli

	double a = 0.4;
	double pi = 4 * atan(1.0);
	double ratia = 0.001;

	glColor3f(1, 0.1, 0.1); // rosu
	glBegin(GL_LINE_STRIP);
	for (double t = -pi / 4; t <= pi / 4; t += ratia) {
		double x1, y1, x2, y2;
		x1 = a*sqrt(2 * cos(2 * t))*cos(t);//Transformarea in coordonate carteziene a coordonatelor polare (r,t)
		y1 = a*sqrt(2 * cos(2 * t))*sin(t);//Transformarea in coordonate carteziene a coordonatelor polare (r,t)

		glVertex2f(x1, y1);
	}
	glEnd();

	glBegin(GL_LINE_STRIP);
	for (double t = -pi / 4; t <= pi / 4; t += ratia) {
		double x1, y1, x2, y2;

		x2 = (-a)*sqrt(2 * cos(2 * t))*cos(t);// xmax;
		y2 = (-a)*sqrt(2 * cos(2 * t))*sin(t);// ymax;
		glVertex2f(x2, y2);
	}
	glEnd();
}

void Display9(){//spirala logaritmica

	double pi = 4 * atan(1.0);
	double a = 0.02;
	double ratia = 0.05;

	// afisarea punctelor propriu-zise precedata de scalare
	glColor3f(1, 0.1, 0.1); // rosu
	glBegin(GL_LINE_STRIP);
	for (double t = 0 + ratia; t < 10; t += ratia) {
		double x1, y1;
		x1 = (a*exp(1 + t))*cos(t);
		y1 = (a*exp(1 + t))*sin(t);

		glVertex2f(x1, y1);
	}
	glEnd();
}

void Display0(){//ex1
	double x, xmax;
	double ratia = 0.05;

	xmax = 32;

	glColor3f(1, 0.1, 0.1);
	glBegin(GL_LINE_STRIP);
	for (x = 0; x<xmax; x += ratia){
		double x1, y1;
		if (x == 0)
		{
			x1 = 0;
			y1 = 1;
		}
		else
		{
			x1 = x / xmax;
			y1 = (x - int(x)) / x;
		}
		glVertex2f(x1, y1);
	}
	glEnd();
}

void Init(void) {

	glClearColor(1.0, 1.0, 1.0, 1.0);
	glLineWidth(1);
	//   glPointSize(4);
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
	case '3':
		Display3();
		break;
	case '4':
		Display4();
		break;
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
		Display0();
		break;
	default:
		break;
	}
	glFlush();
}

void Reshape(int w, int h) {
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