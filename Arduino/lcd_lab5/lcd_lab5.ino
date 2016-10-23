#include <LiquidCrystal.h>   

LiquidCrystal lcd( 8, 9, 4, 5, 6, 7 );
char mesaj [60]= {0x1F,0x0E,0x5E,0x41,0x54,0x4F,0x0E,0x13,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x2E,
                 0x1F,0x45,0x0E,0x4D,0x5B,0x58,0x47,0x40,0x5A,0x4B,0x0E,0x0E,0x0E,0x0E,0x2E,
                 0x1F,0x0E,0x42,0x4F,0x4C,0x41,0x5C,0x4F,0x5A,0x41,0x5C,0x0E,0x13,0x0E,0x2E,
                 0x1F,0x45,0x0E,0x5E,0x41,0x54,0x4B,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x2E};

int number;                 

void Change()
{
  int i;

  for (i=0;i<sizeof(mesaj);i++)
    mesaj[i]^=0x2E;
}


void setup()
{ 
  pinMode( A0, INPUT );        
  digitalWrite( A0, LOW );      
  
  //dimensiunea ecranului
  lcd.begin( 16, 2 );
  lcd.setCursor( 0, 0 );  
  number=0;

  Change();
}

void loop()
{
   int button;
   String msgString = "";
   
   
  // button =  analogRead( A0 );
   lcd.setCursor( 0, 0 );
   
   for(int i=0; i < 16; i++){    
    msgString+=number;        
   }
   
   lcd.print(msgString);    
   
   lcd.setCursor( 0, 1 );
   
   number++;
   number%=10;
   msgString = "";
   for(int i=0; i < 16; i++){    
    msgString+=number;        
   }
   lcd.print(msgString);    
   /*if (button<512)
   {
      lcd.setCursor( 0, 0 );
      lcd.print(mesaj);
      lcd.setCursor( 0, 1 );
      lcd.print(mesaj+15);       
   }
   else
   if (button<800)
   {
      lcd.setCursor( 0, 0 );
      lcd.print(mesaj+30);
      lcd.setCursor( 0, 1 );
      lcd.print(mesaj+45);  
   }
*/
   delay(500);
}
