#include <stdio.h>

int main() {
  int sum = 0;
  for (int any=0; any<=100; any++) {
    sum += any;
  }
  printf("Sum: %d\n", sum);
}
