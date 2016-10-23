#include <stdlib.h>
#include <stdio.h>
#include <math.h>

#include "glut.h"

// dimensiunea ferestrei in pixeli
#define dim 500
#define RADIANS 0.01745329251994329576


unsigned char prevKey;

void drawGrid(int dimension, int ratio)
{
	int r = dimension / ratio;
	if (r % 2 != 0)
		r++;
	ratio = dimension / r;

	int i = 0, j = 0;
	glColor3ub(0, 0, 0);
	glBegin(GL_LINES);
	for (i = -dimension; i <= dimension; i += ratio)
	{
		glVertex2f(i, -dimension);
		glVertex2f(i, dimension);
	}
	for (i = -dimension; i <= dimension; i += ratio)
	{
		glVertex2f(-dimension, i);
		glVertex2f(dimension, i);
	}
	glEnd();
}


void WritePixel(int x, int y, int dimension, int ratio)
{
	int absRep = abs(dimension / ratio);
	if ((abs(x) <= absRep) && (abs(y) <= absRep))
	{
		int Rep = dimension / ratio;
		int x1 = x * ratio;
		int y1 = y * ratio;

		glColor3ub(87, 87, 87);
		glPushMatrix();
		glTranslatef(x1, y1, 0.0f);
		float start[2] = { 1.0f, 0.0f };
		int degrees, factor = 24;

		glBegin(GL_POLYGON);
		for (degrees = 0; degrees < 360 / factor; degrees++)
			glVertex3f(cos(degrees*factor*RADIANS)*11.0f, sin(degrees*factor*RADIANS)*11.0f, 0.0f);
		glEnd();
		glPopMatrix();
	}
}

void AfisareSegmentDreapta3(int x1, int y1, int x2, int y2, int dimension, int ratio)
{
	int dx, dy, x, y, E, NE, d, dE, dSE;
	glColor3f(1.0f, 0.0f, 0.0f);
	glLineWidth(2.0f);
	glBegin(GL_LINES);
	glVertex3f(x1*ratio, y1*ratio, 0.0f);
	glVertex3f(x2*ratio, y2*ratio, 0.0f);
	glEnd();
	glLineWidth(1.0f);
	glColor3f(0.0f, 0.0f, 0.0f);
	dx = x2 - x1;
	dy = y2 - y1;
	if (dy < 0)
	{
		x = x1;
		y = y1;
		d = 2 * dy + dx;
		dE = 2 * dy;
		dSE = 2 * (dy + dx);
		WritePixel(x, y, dimension, ratio);
		WritePixel(x, y + 1, dimension, ratio);
		WritePixel(x, y - 1, dimension, ratio);
		while (x<x2)
		{
			if (d <= 0)
			{
				d += dSE;
				x++;
				y--;
			}
			else
			{
				d += dE;
				x++;
			}
			WritePixel(x, y, dimension, ratio);
			WritePixel(x, y + 1, dimension, ratio);
			WritePixel(x, y - 1, dimension, ratio);
		}
	}
	else
	{
		x = x1;
		y = y1;
		d = 2 * dy - dx;
		E = 2 * dy;
		NE = 2 * (dy - dx);
		WritePixel(x, y, dimension, ratio);
		while (x < x2)
		{
			if (d <= 0)
			{
				d += E;
				x++;
			}
			else
			{
				d += NE;
				x++;
				y++;
			}
			WritePixel(x, y, dimension, ratio);
		}
	}
}


void Init(void)
{
	glClearColor(1.0, 1.0, 1.0, 1.0);
	glLineWidth(1);
	glPointSize(1);
	glMatrixMode(GL_PROJECTION);
	gluOrtho2D(-dim, dim, -dim, dim);
}

void Display(void) {
	glClear(GL_COLOR_BUFFER_BIT);

	switch (prevKey) {
	case '1':
		drawGrid(480, 30);
		break;
	case '2':
		drawGrid(480, 30);
		AfisareSegmentDreapta3(-16, -8, 16, 5, 480, 30);
		AfisareSegmentDreapta3(-16, 16, 16, 8, 480, 30);
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