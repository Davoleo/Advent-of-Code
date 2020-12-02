#include <stdio.h>

/*-------------------*
 *  Author: Davoleo  *
 *-------------------*/

void findNumbers(int[], int, int*, int*);
void findNumbersPart2(int[], int, int*, int*, int*);

int main() {
    FILE* inputPointer;
    int numbers[205];
    int counter = 0;

    inputPointer = fopen("input.txt", "r");

    int num;
    while (fscanf(inputPointer, "%d", &num) != EOF) {
        numbers[counter] = num;
        counter++;
    }

    int result1, result2;

    findNumbers(numbers, counter, &result1, &result2);
    printf("The product of the 2 entries that sum to 2020 is %d.\n", result1 * result2);

    int result3;

    findNumbersPart2(numbers, counter, &result1, &result2, &result3);
    printf("The product of the 3 entries that sum to 2020 is %d.\n", result1 * result2 * result3);

    system("pause");
}

void findNumbers(int array[], int bound, int* firstResult, int* secondResult) {

    for (int i = 0; i < bound; i++) {
        for (int j = 0; j < bound; j++) {
            if (array[i] + array[j] == 2020)
            {
                *firstResult = array[i];
                *secondResult = array[j];
                return;
            }
        }
    }
}

void findNumbersPart2(int array[], int bound, int* firstResult, int* secondResult, int* thirdResult) {
    for(int i = 0; i < bound; i++) {
        for (int j = 0; j < bound; j++) {
            for (int k = 0; k < bound; k++) {
                if (array[i] + array[j] + array[k] == 2020) {
                   *firstResult = array[i]; 
                   *secondResult = array[j];
                   *thirdResult = array[k];
                   return;
                }
            }
        }
    }
}