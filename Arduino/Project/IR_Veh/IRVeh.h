#define DEBUG_MSGS 1
#define IR_RECV_PIN   A5

// IR Remote buttons
#define CH_M          0xFFA25D
#define CH_S          0xFF629D
#define CH_P          0xFFE21D
#define PREV          0xFF22DD
#define NEXT          0xFF02FD
#define PLAY_PAUSE    0xFFC23D
#define VOL_M         0xFFE01F
#define VOL_P         0xFFA857
#define EQ            0xFF906F
#define B_100P        0xFF9867
#define B_200P        0xFFB04F
#define B_0           0xFF6897
#define B_1           0xFF30CF
#define B_2           0xFF18E7
#define B_3           0xFF7A85
#define B_4           0xFF10EF
#define B_5           0xFF38C7
#define B_6           0xFF5AA5
#define B_7           0xFF42BD
#define B_8           0xFF4AB5
#define B_9           0xFF52AD
#define NO_IR         0xFFAAAA
#define NR_NO_IR_AFTER 5

#define DELAY_MAV 1000
#define DEFAULT_SPEED 220
#define NO_TIME_LIMIT -1
#define IMPEDIMENT_LIMIT 28

//DISTANCE Sensor
#define TRIG_PIN A1//10
#define ECHO_PIN A0//9

#define STOP_PIN_LED 10

//DATA Structures
//Motors
struct MotorInfo {
  bool change = false;
  unsigned int motor_dir = RELEASE;
  unsigned int speed = DEFAULT_SPEED;  
  unsigned long time = NO_TIME_LIMIT;
};

struct ImpledimentData{
  unsigned long frontDistance = 0;
  unsigned long leftDistance = 0;
  unsigned long righttDistance = 0;
  unsigned long backDistance = 0;
};

