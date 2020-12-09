#include <stdio.h>

int findWeakness(const int arrayLength, const int preambleLength, int array[2002]);
int findTrueWeakness(int array[2002], const int arrayLength, const int invalid);

int main() {
    FILE* inputPointer;
    int array[2002];
    int counter = 0;

    inputPointer = fopen("input.txt", "r");

    const int preambleLength = 25;

    int num;
    while (fscanf(inputPointer, "%d", &num) != EOF) {
        array[counter] = num;        

        //Computes the length of the preamble
        /*if (counter > 1 && preambleLength == 0) {
            for (int i = 0; i < counter; i++) {
                for (int j = 0; j < counter; j++) {
                    if (j == i)
                        continue;

                    if (array[i] + array[j] == array[counter]) {
                        preambleLength = counter;
                    }
                }
            }
        }*/

        counter++;
    }

    int invalid = findWeakness(counter, preambleLength, array);

    printf("Preamble Length is: %d\n", preambleLength);
    printf("The first invalid XMAS sequence number is: %d\n", invalid);

    int trueWeakness = findTrueWeakness(array, counter, invalid);
    printf("The weakness of the XMAS sequence is: %d\n", trueWeakness);
}

int findWeakness(const int arrayLength, const int preambleLength, int array[2002]) {

    for (int offset = 0; offset <= arrayLength - preambleLength; offset++) {
        //printf("%d\n", offset);
        int i = preambleLength + offset;
        
        short found = 0;
        for (int j = offset; j < offset + preambleLength; j++) {
            for (int k = offset; k < offset + preambleLength; k++) {
                if (array[j] == array[k])
                    continue;
                
                if (array[k] + array[j] == array[i]) {
                    found = 1;
                }
            }
        }

        if (found == 0) {
            return array[i];
        }
    }
}

int findTrueWeakness(int array[2002], const int length, const int invalid) {

    int count = 0;
    int sum = 0;
    int min;
    int max;

    while (sum != invalid)
    {
        min = array[count];
        max = array[count];

        sum = 0;
        for (int i = count; i < length; i++) {
            if (sum >= invalid)
                break;

            if (min > array[i])
                min = array[i];

            if(max < array[i])
                max = array[i];
            
            sum += array[i];
        }

        count++;
    }

    return min + max;
    
}