#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include "glut.h"
#include <fstream>
#define dim 600
using namespace std;
unsigned char prevKey;
double epsilon = 0.1, cx = -1.0 + epsilon, cy = -1.0 + epsilon; // cx,cy - coordonatele in opengl ale originii grilei carteziene;
double PI = 3.14159265;
struct VARF { int x, y; };
struct MUCHIE { VARF vi, vf; };
//typedef MUCHIE POLIGON[100];
struct INTERSECTIE { int ymax; double xmin; double ratia; };
// ymax al muchiei
// xmin al muchiei -  may be changed during later processing
// ratia = 1/m , m = panta
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
		glBegin(GL_TRIANGLE_FAN); // ca sa se coloreze si inauntru
		for (double t = 0; t <= 2 * PI; t += 0.003) // cerc de raza=1/3*dc (pt ingrosare)
			glVertex2d(cx + (double)i*dc + (double)1 / 3 * cos(t)*dc, cy + (double)j*dl + (double)1 / 3 * sin(t)*dl);
		glEnd();
	}

	void desenareCerc(int R){
		//cercul rosu:
		glColor3f(1, 0, 0);
		glBegin(GL_LINE_STRIP);
		for (double t = 0; t <= 2 * PI; t += 0.003)
			glVertex2d(cx + (double)R*cos(t)*dc, cy + (double)R*sin(t)*dl); // R*cos(t),R*sin(t) - coordonate polare
		glEnd();

		int x = R, y = 0;
		int d = 1 - R;
		int dN = 2;
		int dNV = -2 * R + 2;
		WritePixel(x - 1, y);
		WritePixel(x, y);	// ingrosare prin simetrie
		WritePixel(x + 1, y);
		while (y < x)
		{ // conditia y < x caracterizeaza octantul 1
			if (d < 0)
			{	// alegem pixelul N
				d += dN;
				dN += 2;
				dNV += 4;
			}
			else
			{ 	// alegem pixelul NV
				d += dNV;
				dN += 2;
				dNV += 2;
				x--;
			}
			y++;
			WritePixel(x - 1, y);
			WritePixel(x, y);	// ingrosare prin simetrie
			WritePixel(x + 1, y);
		}
	}

	void UmplereElipsa(int a, int b)
	{	//cercul rosu:
		glColor3f(1, 0, 0);
		glBegin(GL_LINE_STRIP);
		for (double t = 0; t <= 2 * PI; t += 0.003)
			glVertex2d(0 + (double)a * cos(t)*(dc), -0.49 + (double)b*sin(t)*(dl)); // a*cos(t),b*sin(t) - coordonate polare
		glEnd();
	}

	void UmplerePoligon(int v[], int nrvarfuri)
	{ // ET - Edge Table , AET - Active Edge Table
		glColor3f(1, 0, 0); //poligonul rosu
		glBegin(GL_POLYGON); // convex
		for (int i = 0; i < nrvarfuri * 2; i += 2)
		{
			glVertex2d(cx + (double)v[i] * (dc), cy + (double)v[i + 1] * (dl));
		}
		glEnd();

		int xm, xM, ym, yM;
		INTERSECTIE ET[100][100];
		MUCHIE p[100]; // POLIGON p	

		int kk = 0; // nr de muchii din poligon
		for (int i = 0; i < nrvarfuri * 2; i += 2) // initializare p (POLIGON)
		{
			p[kk].vi.x = v[i];
			p[kk].vi.y = v[i + 1];
			p[kk].vf.x = v[(i + 2) < nrvarfuri * 2 ? i + 2 : 0];
			p[kk].vf.y = v[(i + 3) < nrvarfuri * 2 ? i + 3 : 1];
			kk++;
		}
		int ymaxp = 0;// y max al polig (DOM_SCAN)
		for (int i = 1; i < nrvarfuri * 2; i += 2)
			if (ymaxp < v[i])
				ymaxp = v[i];

		// calculul intersectiei unei drepte de scanare cu toate muchiile poligonului (ET)
		for (int i = 0; i < nrvarfuri; i++) // pentru fiecare muchie din poligon ...
		{
			if (p[i].vi.y != p[i].vf.y) // ... care nu este orizontala
			{
				ym = (p[i].vi.y < p[i].vf.y) ? p[i].vi.y : p[i].vf.y;
				yM = (p[i].vi.y > p[i].vf.y) ? p[i].vi.y : p[i].vf.y;
				xm = (ym == p[i].vi.y) ? p[i].vi.x : p[i].vf.x;
				xM = (yM == p[i].vi.y) ? p[i].vi.x : p[i].vf.x;
				//xm = (p[i].vi.x < p[i].vf.x) ? p[i].vi.x : p[i].vf.x;
				//xM = (p[i].vi.x > p[i].vf.x) ? p[i].vi.x : p[i].vf.x;
				for (int iii = 0; iii < 100; iii++)
					if (abs(ET[ym][iii].xmin) > 10000)
					{
						ET[ym][iii] = { yM, xm, (double)(xm - xM) / (double)(ym - yM) }; // ratia = 0 pt segmente verticale
						break;
					}
			}
		}

		/* sortarea in ordine crescatoare conform cu xm a intersectiilor dreptei de scanare cu muchiile poligonului */
		bool change;
		for (int i = 0; i <= ymaxp; i++)
		{
			do
			{
				change = false;
				if (abs(ET[i][0].xmin) > 10000) break;
				int eti = 0; // |et(i)|
				while (abs(ET[i][eti].xmin) < 10000) // daca exista intersectie
					eti++;

				for (int j = 0; j < eti - 1; j++)
				{
					if (ET[i][j].xmin > ET[i][j + 1].xmin)
					{
						INTERSECTIE aux = ET[i][j];
						ET[i][j] = ET[i][j + 1];
						ET[i][j + 1] = aux;
						change = true;
					}
				}
			} while (change);
		}

		/*------------------------------------------calculssm------------------------------------------------*/
		INTERSECTIE aet[100];
		INTERSECTIE ssm[100][100];
		int y = -1, k;
		for (int i = 0; i <= ymaxp; i++)
			if (abs(ET[i][0].ymax) < 10000)
			{
				y = i; // Set y to smallest y coordinate that has an entry in ET (ymin) (1)
				break;
			}
		if (y<0 || y>10000) return;

		do
		{
			int aetcount = 0;
			/*if (abs(ET[y][0].xmin)>10000)
			{
				y++;
				continue;
			}*/
			for (int i = 0; i < 100; i++)
			{
				aet[i] = ET[y][i]; // aet = aet . et(y);
				aetcount++;
				if (abs(aet[i].xmin)>10000)
				{
					aetcount--;
					break;
				}
			}

			// eliminarea varfurilor cu ymax == y
			for (int i = 0; i < aetcount; i++)
			{
				if (aet[i].ymax == y)
				{
					for (int ii = i; ii < aetcount; ii++)
						aet[ii] = aet[ii + 1];
					aetcount--;
				}
			}

			// sortarea aet cf. cheii xmin
			for (int i = 0; i < aetcount; i++)
				for (int j = 0; j < aetcount - 1; j++)
					if (aet[j].xmin > aet[j + 1].xmin)
					{
						INTERSECTIE aux = aet[j];
						aet[j] = aet[j + 1];
						aet[j + 1] = aux;
					}

			y++;
			// reactualizarea punctelor de intersectie pentru noua dreapta de scanare
			for (int i = 0; i < aetcount; i++)
			{
				if (aet[i].ratia != 0)
					aet[i].xmin += aet[i].ratia;
			}


			for (int i = 0; i < aetcount - 1; i += 2) // pt fiecare intersectie 
			{
				int fl = ceil(aet[i].xmin);
				while (fl <= floor(aet[aetcount - 1].xmin))
				{	// ssm(y) = aet;
					WritePixel(fl, y);
					fl++;
					WritePixel(fl, y);
					fl++;
				}
			}
		} while (y < ymaxp);
	}
};

void Init(void)
{
	glEnable(GL_POINT_SMOOTH); // pt. ca punctele sa fie cercuri, si nu patrate
	//gluOrtho2D(-10, 10, -10, 10); // sistem de coordonate cu x si y de la -10 la 10
	glClearColor(1.0, 1.0, 1.0, 1.0);
	glLineWidth(1.5);
	glPolygonMode(GL_FRONT, GL_LINE);
}

void Display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	GrilaCarteziana grila = GrilaCarteziana(16, 16);
	int nrvf, vv[30]; ifstream fisier("fisier.txt");
	fisier >> nrvf;
	for (int i = 0; i < nrvf * 2; i++)
		fisier >> vv[i]; // coordonatele varfurilor V1, V2, ..., Vn
	fisier.close();
	switch (prevKey) {
	case '1':
		grila.desenareCerc(7);
		break;
	case '2':
		grila.UmplereElipsa(7, 3);
		break;
	case '3':
		grila.UmplerePoligon(vv, nrvf);
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

int main(int argc, char** argv)
{
	glutInit(&argc, argv);
	glutInitWindowSize(dim, dim);
	glutInitWindowPosition(100, 100);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutCreateWindow(argv[0]);
	Init();
	glutReshapeFunc(Reshape);
	glutKeyboardFunc(KeyboardFunc);
	glutDisplayFunc(Display);
	glutMainLoop();
	return 0;
}