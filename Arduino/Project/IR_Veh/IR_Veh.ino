#include <IRremote.h>
#include <AFMotor.h>
#include "Thread.h"
#include "ThreadController.h"
#include "IRVeh.h"

/////AUTOMATIC//////
bool automaticActivated = false;
bool avoidImpediment = false;
bool absoluteImpediment = false;
ImpledimentData implediment;

//////MOTORS/////////
AF_DCMotor motor_1(4);
AF_DCMotor motor_2(3);

MotorInfo motor_1_info;
MotorInfo motor_2_info;

/////IR RECV/////////
IRrecv irrecv(IR_RECV_PIN);
decode_results ir_result;
static unsigned long last_ir_value = NO_IR;
static unsigned long current_ir_value = NO_IR;

/////PSEUDO THREADS///
ThreadController controllThreads = ThreadController();
Thread irThread = Thread();
Thread motorsThread = Thread();
Thread distanceThread = Thread();

//////DISTANCE///////
long distance = 0;

void changeMotorDataByModeInfo(unsigned long what){

  if (true == automaticActivated){
    if(true == avoidImpediment){
      // continue
    }else if(what == NO_IR){
      return;
    }
  } 
  
  switch(what){
    case NO_IR:
      motor_1_info.change = true;
      motor_1_info.motor_dir = RELEASE;
      motor_2_info.motor_dir = RELEASE;
    break;
    case B_2:
      motor_1_info.change = true;
      motor_1_info.motor_dir = FORWARD;
      motor_2_info.motor_dir = FORWARD;
    break;
    case B_4:
      motor_1_info.change = true;
      motor_1_info.motor_dir = FORWARD;
      motor_2_info.motor_dir = BACKWARD;
    break;
    case B_6:
      motor_1_info.change = true;
      motor_1_info.motor_dir = BACKWARD;
      motor_2_info.motor_dir = FORWARD;
    break;
    case B_8:
      motor_1_info.change = true;
      motor_1_info.motor_dir = BACKWARD;
      motor_2_info.motor_dir = BACKWARD;
    break;   
    case B_0:
      if(automaticActivated == false){
#ifdef DEBUG_MSGS
        Serial.println("YES Automatic");
#endif
        automaticActivated = true;
        motor_1_info.change = true;
        motor_1_info.motor_dir = FORWARD;
        motor_2_info.motor_dir = FORWARD;
      }else{
#ifdef DEBUG_MSGS
        Serial.println("NO Automatic");
#endif
        absoluteImpediment = false;
        automaticActivated = false;
        digitalWrite(STOP_PIN_LED, LOW);
        
        motor_1_info.change = true;
        motor_1_info.motor_dir = RELEASE;
        motor_2_info.motor_dir = RELEASE;
      }
      
    break;
  }
}

void callBackIR(){
  static uint8_t no_ir_counter = 0;  
  
  if(irrecv.decode(&ir_result)){
    irrecv.resume();
    no_ir_counter = 0; // reset no ir counter 
    current_ir_value = ir_result.value;
#ifdef DEBUG_MSGS
  Serial.print("IR_MSG : 0x");
  Serial.println(ir_result.value, HEX);
#endif
    //ir received !
  }else{
    // no ir
    no_ir_counter = no_ir_counter + 1; 
    if(no_ir_counter == NR_NO_IR_AFTER){
        current_ir_value = NO_IR;        
        no_ir_counter = 0;        
#ifdef DEBUG_MSGS        
        Serial.println("IR : NO IR");
#endif 
    }    
  }

  if(last_ir_value != current_ir_value){
    last_ir_value = current_ir_value;
    changeMotorDataByModeInfo(last_ir_value);
#ifdef DEBUG_MSGS
    Serial.print("Last IR : 0x");
    Serial.println(last_ir_value, HEX);
#endif 
  }  
}

void callBackMotors(){

  if((false == motor_1_info.change)){
#ifdef DEBUG_MSGS
  Serial.println("MOTOR: NO CHANGE ABORT");
#endif  
    return;
  }
  motor_1_info.change = false; 

  motor_1.setSpeed(motor_1_info.speed);
  motor_2.setSpeed(motor_2_info.speed);
  
  motor_1.run(motor_1_info.motor_dir);
  motor_2.run(motor_2_info.motor_dir);
  
  if(motor_1_info.time != NO_TIME_LIMIT){
      delay(motor_1_info.time);
      motor_1.run(RELEASE);
      motor_2.run(RELEASE);
      
      motor_1_info.time = NO_TIME_LIMIT;
      delay(DELAY_MAV);
  }
  
#ifdef DEBUG_MSGS
  Serial.println("MOTOR UPDATE");
#endif  
}

void readDistance(long &distanceInputRef){
  static long duration;

  digitalWrite(TRIG_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIG_PIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG_PIN, LOW);
  duration = pulseIn(ECHO_PIN, HIGH);
  distanceInputRef = (duration/2)/29.1;
}

void blink_StopABS(){
  static bool ledStatus = false;
  digitalWrite(STOP_PIN_LED, (ledStatus == true ? HIGH: LOW));
  delay(1000);
  ledStatus = !ledStatus;
}

void callBackDistance(){

  if(automaticActivated == false){
      return;
  }   

  if(true == absoluteImpediment){
    blink_StopABS();
    return;
  }

  readDistance(distance);
  if(distance < IMPEDIMENT_LIMIT){
#ifdef DEBUG_MSGS
  Serial.print("Avoid impediment 1 : ");
  Serial.println(distance);
#endif   
    avoidImpediment = true;
    
    // stop motors
    motor_1_info.change = true;
    motor_1_info.motor_dir = RELEASE;
    motor_2_info.motor_dir = RELEASE;
    callBackMotors();

    digitalWrite(STOP_PIN_LED, HIGH); 
    delay(DELAY_MAV);    
    
    //Move left
    motor_1_info.time = 750;
    changeMotorDataByModeInfo(B_4);
    callBackMotors();
    readDistance(distance);

    if(distance < IMPEDIMENT_LIMIT){        

#ifdef DEBUG_MSGS
  Serial.print("Avoid impediment 2 : ");
  Serial.println(distance);
#endif  
        //Move right  
        motor_1_info.time = 1500;
        changeMotorDataByModeInfo(B_6);
        callBackMotors();
        readDistance(distance);
        if(distance < IMPEDIMENT_LIMIT){

#ifdef DEBUG_MSGS
  Serial.print("Avoid impediment 3 : ");
  Serial.println(distance);
#endif            
          //Move back
          motor_1_info.time = 750;
          changeMotorDataByModeInfo(B_6);
          callBackMotors();
          readDistance(distance);
          
          if(distance < IMPEDIMENT_LIMIT){

#ifdef DEBUG_MSGS
  Serial.print("Avoid impediment absolut : ");
  Serial.println(distance);
#endif  
              //absolute impediment
              absoluteImpediment = true; 
              return;             
          }
        }       
    }
  }
  // move forward
  changeMotorDataByModeInfo(B_2);
  callBackMotors();
  avoidImpediment = false;
  digitalWrite(STOP_PIN_LED, LOW);
  
#ifdef DEBUG_MSGS
  Serial.print("Distance : ");
  Serial.println(distance);
#endif
}

void setup(){ 
  Serial.begin(9600);  
  irrecv.enableIRIn();
  
  //PINS
  pinMode(TRIG_PIN, OUTPUT);
  pinMode(ECHO_PIN, INPUT); 
  pinMode(STOP_PIN_LED, OUTPUT);    

  irThread.onRun(callBackIR);
  irThread.setInterval(50);

  motorsThread.onRun(callBackMotors);
  motorsThread.setInterval(50);

  distanceThread.onRun(callBackDistance);
  distanceThread.setInterval(50);

  controllThreads.add(&irThread);
  controllThreads.add(&motorsThread);
  controllThreads.add(&distanceThread);
#ifdef DEBUG_MSGS
  Serial.println("Setup done");
#endif  
}

void loop() {  
  controllThreads.run();
}
