# include <fstream>
# include <cstring>
# include <vector>

using namespace std;

//ifstream f("C:/Users/Toma/Desktop/concepte/concepte_nelematizate/266974.concept");
//ifstream f1("C:/Users/Toma/Desktop/concepte/concepte_lematizate/266974.concept1");

ofstream g("concepte.out");

int numar = 0;
char a[ 1000 ];

char ap_lem[ 100 ];
char ap_nelem[ 100 ];

char nume_nelem[ 1000 ][ 1000 ];
char nume_lem[ 1000 ][ 1000 ];

int lg_nelem;
int lg_lem;

int nr_con_lem;
int nr_con_nelem;
int nou;

char *p;

void prelucreaza();

void concepte_nelematizate( char sir[1000] )
{
	ifstream f(sir);
	int nr = 0;
	lg_nelem = 0;
	while ( !f.eof() )
	{
		nr++;
		f.getline( a, 1000 );
		p = strtok(a," :");
		if ( nr == 1 )
		{
			int cuv = 0;
			while ( p )
			{
				cuv++;
				if ( cuv == 3 )
				{
					strcpy( ap_nelem, p );
				}
				//g << p << " ";
				p = strtok(NULL," :");
			}
		}
		else
		{
			int cuv = 0;
			while ( p )
			{
				cuv++;
				if ( cuv == 4 )
				{
					lg_nelem++;
					strcpy( nume_nelem[ lg_nelem ], p );
				}
				p = strtok(NULL," :");
			}
		}
	}
	nr_con_nelem = lg_nelem;
	g << " Numarul de concepte nelematizate  " <<  nr_con_nelem << "\n";
	g << " Numarul de aparitii al conceptelor nelematizate " <<  ap_nelem << "\n";
	g << "\n";
	int i;
	//for ( i = 1; i <= lg_nelem; i ++ )
		//g << nume_nelem[ i ] << "\n";
	f.close();
}
void concepte_lematizate( char sir[1000] )
{
	int nr = 0;
	lg_lem = 0;
	ifstream f1(sir);
	while ( !f1.eof() )
	{
		nr++;
		f1.getline( a, 1000 );
		p = strtok(a," :");
		if ( nr == 1 )
		{
			int cuv = 0;
			while ( p )
			{
				cuv++;
				if ( cuv == 3 )
				{
					strcpy( ap_lem, p );
				}
				//g << p << " ";
				p = strtok(NULL," :");
			}
		}
		else
		{
			int cuv = 0;
			while ( p )
			{
				cuv++;
				if ( cuv == 4 )
				{
					lg_lem++;
					strcpy( nume_lem[ lg_lem ], p );
				}
				p = strtok(NULL," :");
			}
		}
	}
	nr_con_lem = lg_lem;
	g << " Numarul de concepte lematizate  " <<  nr_con_lem << "\n";
	g << " Numarul de aparitii al conceptelor lematizate " <<  ap_lem << "\n";
	int i;
	//for ( i = 1; i <= lg_lem; i ++ )
		//g << nume_lem[ i ] << "\n";
	f1.close();
}

void compara()
{
	int i, j, ok = 0;
	nou = 0;
	for ( i = 1 ; i <= lg_lem ; i++ )
	{
		ok = 0;
		for ( j = 1 ; j <= lg_nelem && ok == 0 ; j++ )
			if ( strcmp( nume_lem[ i ], nume_nelem[ j ] ) == 0 )
			{
				ok = 1;
			}
		if ( ok == 0 )
		{
			//g << " " << nume_lem[ i ] << "\n";
			nou++;
		}
	}
	g << " Nr de concepte noi " << nou << "\n";
	if (nou != 0)
		numar++;
}

void prelucreaza()
{
	char adresa1[ 1000 ];
	char adresa2[ 1000 ];
	
	
	long minim = 266959;
	long maxim = 267225;
	long i;
	
	strcpy(adresa1,"C:/Users/Toma/Desktop/concepte/concepte_nelematizate/");
	strcpy(adresa2,"C:/Users/Toma/Desktop/concepte/concepte_lematizate/");
	int nr_fisiere = 0;
	for ( i = minim ; i <= maxim ; i++ )
	{
		char cons1[ 1000 ];
		char cons2[ 1000 ];
		char iesire[ 100 ];
		
		char numar[ 100 ];
		itoa( i, numar, 10 );
		//g << numar << "\n";
		strcpy( cons1, adresa1 );
		strcpy( cons2, adresa2 );
		
		strcat( cons1, "/");
		strcat( cons1, numar);
		strcat( cons1, ".concept");
		
		strcat( cons2, "/");
		strcat( cons2, numar);
		strcat( cons2, ".concept1");
		
		strcpy( iesire, numar );
		strcat( iesire, ".out");
		
		//g << cons1 << "\n" << cons2 << "\n";
		//g << "\n";
		
		ifstream f(cons1);
		ifstream f1(cons2);
			if ( f && f1 )
			{
				nr_fisiere++;
				f.close();
				f1.close();
				//ofstream g(iesire);
				g << "Fisierul numarul " << numar << ":( index " << nr_fisiere << " ) " <<  "\n";
				concepte_nelematizate(cons1);
				concepte_lematizate(cons2);
				compara();
				g << "------------------------------------------------------------------------------" << "\n";
				//f.close();
			}
	}
}

int main()
{
	prelucreaza();
	g << " In " << numar << " % din cazuri apar concepte noi dupa lematizare \n";
	return 0;
}
