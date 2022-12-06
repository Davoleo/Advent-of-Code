#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

typedef struct _node {
    char data;
    struct _node* next;
} node;

/// @brief Checks if the current node list can be considered a valid preamble for ELF PROTOCOL
/// @param cur current node list
bool isElfProtocolPacket(node* cur) {
    node* check1 = cur;
    do {

        node* check2 = check1->next;
        while (check2 != check1) {
            //printf("Checking: %c against %c\n", check1->data, check2->data);
            if (check1->data == check2->data) {
                return false;
            }
            check2 = check2->next;
        }

        check1 = check1->next;

    } while (check1 != cur);

    return true;
}

int main() {

    puts("** Advent of Code 2022 **");
    puts("### Day 06: Tuning Trouble ###");
    puts("--- Part 1 ---");

    //Circular linked list of 4 slots
    node* junction = malloc(sizeof(node));

    node* temp = junction;
    for (int i = 1; i < 4; i++) {
        temp->next = malloc(sizeof(node));
        temp->data = '#'; //Initialize with dummy characters
        temp = temp->next;
        //printf("Allocating node: %d\n", i);
    }
    temp->data = '#'; //Initialize with dummy characters
    temp->next = junction;

    FILE* file;
    file = fopen("input.txt", "r");

    node* cur = junction;
    int in_count = 0;
    char input;
    while (fscanf(file, "%c", &input) != EOF) {
        cur->data = input;
        ++in_count;

        //printf("Scan no.%d, detected char: %c\n", in_count, input);

        if (in_count >= 4) {

            if (isElfProtocolPacket(cur))
                break;
        }

        cur = cur->next;
    }

    printf("Elf Protocol Preamble sequence delay: %d\n", in_count);
    printf("Packet Preamble: %c%c%c%c\n", cur->data, cur->next->data, cur->next->next->data, cur->next->next->next->data);

    temp = junction;
    for (int i = 0; i < 4; ++i) {
        node* t = temp;
        temp = temp->next;
        free(t);
        //puts("Freeing...");
    }

    fclose(file);
    
    file = fopen("input.txt", "r");



    fclose(file);

    return 0;
}