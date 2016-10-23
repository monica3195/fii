#include<stdio.h>

#define READ     1
#define WRITE    2
#define EXECUTE  3
//#define CHECK_BIT(var,pos) ((var) & (1<<(pos-1)))

int securityClassSubj[3]  = {3,   1,  2};
int securityClassObj[3]   = {1,   4,  3};

int ACESSMatrix[3][3] = { { 0, 1, 2},
                          { 0, 7, 0 },
                          { 0, 2, 3 }
                        };

bool hasRight(int sub, int obj, int right){

  int matrixValue = ACESSMatrix[sub][obj];
  if( ! (matrixValue & (1 << (right-1)) )){

      printf("No right in matrix \n");
      return false;
  }
  int secObj = securityClassObj[obj];
  int secSub = securityClassSubj[sub];

  if(right == READ){
    //no read up
    if(secSub < secObj){
        printf("No read up\n");
        return false;
    }
  }
  else if(right == WRITE)
  {
    //no write down
    if(secSub > secObj){
      printf("No write down\n");
      return false;
    }
  }

  return true;
}

int main(){

  if(hasRight(1,1, WRITE)){
    printf("Sub %d on %d check write \n", 1,1);
    printf("Has right \n");
  }else{
    printf("No right \n");
  }
  printf("-------------------\n");

  if(hasRight(0,1, WRITE)){
    printf("Has right \n");
  }else{
    printf("No right \n");
  }
  printf("-------------------\n");

  if(hasRight(0,2, WRITE)){
    printf("Has right \n");
  }else{
    printf("No right \n");
  }
  printf("-------------------\n");

  if(hasRight(2,2, READ)){
    printf("Has right \n");
  }else{
    printf("No right \n");
  }
  printf("-------------------\n");
}
