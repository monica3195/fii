#include <LiquidCrystal.h>   

LiquidCrystal lcd( 8, 9, 4, 5, 6, 7 );
char mesaj [60]= {0x1F,0x0E,0x5E,0x41,0x54,0x4F,0x0E,0x13,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x2E,
                 0x1F,0x45,0x0E,0x4D,0x5B,0x58,0x47,0x40,0x5A,0x4B,0x0E,0x0E,0x0E,0x0E,0x2E,
                 0x1F,0x0E,0x42,0x4F,0x4C,0x41,0x5C,0x4F,0x5A,0x41,0x5C,0x0E,0x13,0x0E,0x2E,
                 0x1F,0x45,0x0E,0x5E,0x41,0x54,0x4B,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x2E};
int position = 0;
int enemies[5];

String lineOne;
String lineTwo;                      

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

  Change();
}

void loop()
{  
   int button;
   if(position == 0){
    
  
   button =  analogRead( A0 );
   Serial.print(button);
   Serial.println("Loop");
   
   if (button<60) //right
   {
    timeSelector++;
    timeSelector %= 3;
   }
   else
   if (button<200 && button > 60) // up
   {      
      if(position == 0)[
         position = 1;
       }
     
   }   
   else
   if (button<400 && button > 200) // down
   {      
      if(position == 1){
        position = 0;
      }
   }   
   else
   if (button< 600 && button > 400) // left
   {      
      //left pauza
      timeSelector = -1;
   }   
   else
   if (button > 600 && button < 800) // select
   {

      if(timeSelector == -1){
        setupTime = millis() - setupTime;
      }
      else{
        setupTime = millis();
      }
     
     timeSelector = 0;
   }   
 delay(100);
}
