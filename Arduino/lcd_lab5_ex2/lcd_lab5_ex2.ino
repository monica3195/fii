#include <LiquidCrystal.h>   

LiquidCrystal lcd( 8, 9, 4, 5, 6, 7 );
char mesaj [60]= {0x1F,0x0E,0x5E,0x41,0x54,0x4F,0x0E,0x13,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x2E,
                 0x1F,0x45,0x0E,0x4D,0x5B,0x58,0x47,0x40,0x5A,0x4B,0x0E,0x0E,0x0E,0x0E,0x2E,
                 0x1F,0x0E,0x42,0x4F,0x4C,0x41,0x5C,0x4F,0x5A,0x41,0x5C,0x0E,0x13,0x0E,0x2E,
                 0x1F,0x45,0x0E,0x5E,0x41,0x54,0x4B,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x0E,0x2E};

int ora = 0, minute = 0, secunde = 0;
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

  Change();
}

void loop()
{  
   int button;
   currentTime = millis();

  lcd.setCursor( 0, 0 );  
  lcd.print("                        ");

  lcd.setCursor( 0, 1 );  
  lcd.print("                        ");
   
   if(!(timeSelector >= 0)){
     secunde =  (currentTime / 1000) % 60;
     minute  =  (currentTime/(60000)%60);  
     ora     =  (currentTime/(1000*60*60)%60);  
   }
   
  lcd.setCursor( 0, 0 );  
  lcd.print(mesajLine1);

  String oraLine2 = "";
  if(ora < 10){
    oraLine2 += "0";  
  }
  
  oraLine2 += ora;
  oraLine2 += ":";
  if(minute < 10){
    oraLine2 += "0";  
  }
  oraLine2 += minute;
  oraLine2 += ":";
  if(secunde < 10){
    oraLine2 += "0";  
  }
  oraLine2 += secunde;  

   if(timeSelector >= 0){
      if(timeSelector == 0){
        oraLine2 += " sec";
      }
      else 
      if(timeSelector == 1){
        oraLine2 += " min";
      }
      else
      if(timeSelector == 2){
        oraLine2 += " ora";
      }
   }

   

  lcd.setCursor( 0, 1 );
  lcd.print(oraLine2);
   
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
      switch(timeSelector)
      {
      case 0 :
        secunde++;
        secunde %= 60;
      break;
      case 1 :
        minute++;
        minute %= 60;
      break;
      case 2 :
        ora++;
        ora %= 60;
      break;      
      
      }
   }   
   else
   if (button<400 && button > 200) // down
   {
      switch(timeSelector)
      {
      case 0 :
        secunde--;
        secunde %= 60;
      break;
      case 1 :
        minute--;
        minute %= 60;
      break;
      case 2 :
        ora--;
        ora %= 60;
      break;      
      
      }
   }   
   else
   if (button< 600 && button > 400) // left
   {      
      timeSelector--;
      timeSelector %= 3;
   }   
   else
   if (button > 600 && button < 800) // select
   {
      lcd.setCursor( 0, 0 );
      lcd.print("Selector");
      
      if(timeSelector == -1){
        timeSelector = 0;
      }
      else{
        timeSelector = -1;
      }            
      
   }   
 delay(500);
}

// < 60 right
// < 200 up
// < 400 down
// < 600 left
// < 800
//0 select
