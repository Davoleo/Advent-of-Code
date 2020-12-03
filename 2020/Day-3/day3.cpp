#include <iostream>
#include <fstream>
#include <cstring>

/*-------------------*
 *  Author: Davoleo  *
 *-------------------*/

int main() {

    char forestMatrix[325][32];

    std::ifstream inStream;
    inStream.open("input.txt");

    int actualLength = 0;
    do {
        inStream.getline(forestMatrix[actualLength++], 40, '\n');
    } 
    while (!inStream.eof());

    int treeCount1 = 0;
    int treeCount2 = 0;
    int treeCount3 = 0;
    int treeCount4 = 0;
    int treeCount5 = 0;

    for (int row = 0; row < actualLength; row++) {
        
        int col1 = row;
        //Skip 3 cols after 1 row cycle
        int col2 = row * 3;
        //Skip 5 cols after 1 row cycle
        int col3 = row * 5;
        //Skip 7 cols after 1 row cycle
        int col4 = row * 7;
        //Skip 2 rows after 1 col cycle
        int row5 = row * 2;

        //Debug Print (periodic column index)
        std::cout << "Col: "  << col2 % 31 << " | Item: " << forestMatrix[row][col2 % 32] << std::endl;

        if (forestMatrix[row][col1 % 31] == '#')
            treeCount1++;

        if (forestMatrix[row][col2 % 31] == '#')
            treeCount2++;

        if (forestMatrix[row][col3 % 31] == '#')
            treeCount3++;

        if (forestMatrix[row][col4 % 31] == '#')
            treeCount4++;

        if (row5 < actualLength && forestMatrix[row5][col1 % 31] == '#')
            treeCount5++;
    }

    std::cout << "The total number of trees you'll meet on the first slope: " << treeCount1 << std::endl;
    std::cout << "The total number of trees you'll meet on the second slope: " << treeCount2 << std::endl;
    std::cout << "The total number of trees you'll meet on the third slope: " << treeCount3 << std::endl;
    std::cout << "The total number of trees you'll meet on the fourth slope: " << treeCount4 << std::endl;
    std::cout << "The total number of trees you'll meet on the fifth slope: " << treeCount5 << std::endl;
    
    std::cout << "All the number of trees you'd encounter on each slope multiplied by each other are: " << treeCount1 * treeCount2 * treeCount3 * treeCount4 * treeCount5 << std::endl;

    system("pause");
    return 1;
}