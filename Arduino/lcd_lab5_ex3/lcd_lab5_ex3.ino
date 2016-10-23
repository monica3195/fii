#include <LiquidCrystal.h>   

LiquidCrystal lcd( 8, 9, 4, 5, 6, 7 );
char mesaj [60]= {0x1F,0x0E,0x5E,0x41,0x54,0x4F,0x0E,0x13,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x2E,
                 0x1F,0x45,0x0E,0x4D,0x5B,0x58,0x47,0x40,0x5A,0x4B,0x0E,0x0E,0x0E,0x0E,0x2E,
                 0x1F,0x0E,0x42,0x4F,0x4C,0x41,0x5C,0x4F,0x5A,0x41,0x5C,0x0E,0x13,0x0E,0x2E,
                 0x1F,0x45,0x0E,0x5E,0x41,0x54,0x4B,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x2E};

int ora = 0, minute = 0, secunde = 0, milisecunde = 0;
int lapOra = 0, lapMinute = 0, lapSecunde = 0, lapMilisecunde = 0;
String mesajLine1 = "Ora este:";
unsigned long setupTime = 0;
unsigned long currentTime = 0;
int timeSelector = -1; // 0 secunde
                      // 1 minute
                      // 2 ore
                      

void Change()
{
  int i;

  for (i=0;i<sizeof(mesaj);i++)
    mesaj[i]^=0x2E;
}


void setup()
{ 
  
  setupTime = millis();
  pinMode( A0, INPUT );        
  digitalWrite( A0, LOW );      
  
  //dimensiunea ecranului
  lcd.begin( 16, 2 );
  lcd.setCursor( 0, 0 );  
  lcd.println("                    ");
  lcd.setCursor( 0, 1 );  
  lcd.println("                    ");

  Change();
}

void loop()
{  
    lcd.setCursor( 0, 0 ); 
   lcd.println("Varlan e");
    lcd.setCursor( 0, 1 ); 
    lcd.println("BO$$ !");
    
}

// < 60 right
// < 200 up
// < 400 down
// < 600 left
// < 800
//0 select
