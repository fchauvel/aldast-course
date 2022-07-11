#include <stdio.h>

union Data {
   int asInt;
   float asFloat;
};

int main() {
   union Data data;
   data.asFloat = 3.5;
   printf("Value: %d\n", data.asInt);
}
