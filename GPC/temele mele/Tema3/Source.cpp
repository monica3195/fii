#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include "glut.h"
#define dim 750
double epsilon = 0.1, cx = -1.0 + epsilon, cy = -1.0 + epsilon; // cx,cy - coordonatele in opengl ale originii grilei carteziene;

class GrilaCarteziana{
	int n, m; // n linii, m coloane
	double  dc; // dc - distanta dintre coloane
	double  dl; // dl - distanta dintre linii
public:
	GrilaCarteziana(int a, int b){
		n = a;
		m = b;
		dc = (2.0 - 2.0 * epsilon) / (double)(m - 1);
		dl = (2.0 - 2.0 * epsilon) / (double)(n - 1);

		glColor3f(0, 0, 0);
		glBegin(GL_LINE_STRIP); // linie poligonala (chenarul)
		glVertex2f(0.9, 0.9);
		glVertex2f(0.9, -0.9);
		glVertex2f(-0.9, -0.9);
		glVertex2f(-0.9, 0.9);
		glVertex2f(0.9, 0.9);
		glEnd();

		glBegin(GL_LINES); // desenarea coloanelor
		for (double i = -0.9 + dc; i <= 0.9; i += dc)
		{
			glVertex2f(i, 0.9);
			glVertex2f(i, -0.9);
		}
		glEnd();

		glBegin(GL_LINES); // desenarea liniilor
		for (double i = -0.9 + dl; i <= 0.9; i += dl)
		{
			glVertex2f(-0.9, i);
			glVertex2f(0.9, i);
		}
		glEnd();
	}

	void WritePixel(int i, int j){ // i,j - coordonatele x,y ale pixelului in cadrul grilei carteziene
		glColor3f(0, 0, 0);
		glBegin(GL_POINTS);
		glVertex2d(cx + (double)i*dc, cy + (double)j*dl);
		glEnd();
	}

	void desenareSegment(int x0, int y0, int xn, int yn){
		glColor3f(1, 0, 0);
		glLineWidth(3);
		glBegin(GL_LINE_STRIP);
		glVertex2f(cx + (double)x0*dc, cy + (double)y0*dl);
		glVertex2f(cx + (double)xn*dc, cy + (double)yn*dl);
		glEnd();
		double panta = (double)(yn - y0) / (double)(xn - x0);
		if (x0 > xn){
			int aux = xn; xn = x0; x0 = aux;
			aux = yn; yn = y0; y0 = aux;
		}
		int dx = xn - x0; // -b
		int dy = yn - y0; // a
		int d = 2 * dy - dx; // d - variabila de decizie utilizata in algoritm
		int dE = 2 * dy;
		int dNE = 2 * (dy - dx);
		int dSE = 2 * (dy + dx);
		int dN = 2 * dx;
		int dS = -2 * dx;
		int x = x0, y = y0;

		WritePixel(x0, y0);
		WritePixel(xn, yn);
		while (x < xn && panta >= 0 && panta <= 1) // SV-NE
		{
			if (d <= 0) {/* alegem pixelul E */ d += dE; x++; }
			else { /* alegem pixelul NE */ d += dNE; x++; y++; }
			WritePixel(x, y);
		}
		while (x < xn && panta<0 && panta >= -1) // NV-SE
		{
			if (d > 0) {/* alegem pixelul E */ d += dE; x++; }
			else { /* alegem pixelul SE */ d += dSE; x++; y--; }
			WritePixel(x, y + 1);
		}
		d = 2 * dx - dy;
		while (y<yn && panta >1) // SV-NE
		{
			if (d <= 0) {/* alegem pixelul N */ d += dN; y++; }
			else { /* alegem pixelul NE */ d -= dNE; x++; y++; }
			WritePixel(x, y);
		}
		while (y>(yn - 2) && panta <(-1)) // NV-SE
		{
			if (d > 0) {/* alegem pixelul S */ d += dS; y--; }
			else { /* alegem pixelul SE */ d -= dSE; x++; y--; }
			if ((y+2)<y0 )
				WritePixel(x, y+2);
		}
	}
};

void Init(void)
{
	glEnable(GL_POINT_SMOOTH); // pt. ca punctele sa fie cercuri, si nu patrate
	//gluOrtho2D(-10, 10, -10, 10); // sistem de coordonate cu x si y de la -10 la 10
	glClearColor(1.0, 1.0, 1.0, 1.0);
	glLineWidth(1.5);
	glPointSize(15);
	glPolygonMode(GL_FRONT, GL_LINE);
}

void Display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	GrilaCarteziana grila = GrilaCarteziana(16, 16);
	grila.desenareSegment(15, 10, 0, 15);
	grila.desenareSegment(15, 7, 0, 0);
	grila.desenareSegment(1, 3, 3, 12);
	grila.desenareSegment(4, 11, 7, 5);
	glFlush();
}

void Reshape(int w, int h) {
	glViewport(0, 0, (GLsizei)w, (GLsizei)h);
}

int main(int argc, char** argv)
{
	glutInit(&argc, argv);
	glutInitWindowSize(dim, dim);
	glutInitWindowPosition(100, 100);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutCreateWindow(argv[0]);
	Init();
	glutReshapeFunc(Reshape);
	glutDisplayFunc(Display);
	glutMainLoop();
	return 0;
}