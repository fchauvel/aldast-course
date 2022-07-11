#include "stdio.h"
#include "string.h"

union test
{
 int as_int;
 char as_char[20];
} test;

int main()
{
  char text[20] = "Franck Chauvel";

  strcpy((char*) &test, text);
  test.as_int = 12;

  printf("As int: %d\n", test.as_int);
p  puts(test.as_char);
}
