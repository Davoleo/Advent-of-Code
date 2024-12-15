#include <iostream>
#include <fstream>
#include <map>
#include <set>
#include <list>
#include <unordered_set>
#include <vector>
#include <string>
#include <cmath>
#include <algorithm>


struct PosYX
{
    int y;
    int x;

    PosYX& operator=(const PosYX& pos)
    {
        this->x = pos.x;
        this->y = pos.y;

        return *this;
    }

    bool operator==(const PosYX& pos) const
    {
        return (pos.x == this->x && pos.y == this->y);
    }

    bool operator<(const PosYX& pos) const
    {
        return x * x + y * y < pos.x * pos.x + pos.y * pos.y;
    }
};

struct MyHashFunction {
    size_t operator()(const PosYX& t) const{
        return std::hash<std::string>{}(t.x + "," + t.y);
    }
};


std::map<char, std::vector<PosYX>> coordsMap;
static int width = 0, height = 0;

static bool IsInBound(const PosYX& pos) 
{
    return pos.x >= 0 && pos.x < width && 
        pos.y >= 0 && pos.y < height;
}

//static means private [file-level] & inline
static void PrintMap(void)
{
    for(auto entry : coordsMap)
    {
        std::cout << "[" << entry.first << "] : ";
        for(auto pos : entry.second)
            std::cout << pos.x << " " << pos.y << " ; ";
        std::cout << std::endl;
    }
}

static void CalculateAntinodes(std::unordered_set<PosYX, MyHashFunction>& antinodes)
{
    for(auto entry : coordsMap)
    {
        // Here have the map set like [a]
        auto nodes = entry.second;

        for(int i = 0; i < nodes.size(); ++i)
        {
            // Here we have the list entries "{0, 1}, ..."
            for(int j = i + 1; j < nodes.size(); ++j)
            {
                //Distance with direction
                int vecX = nodes[i].x - nodes[j].x;
                int vecY = nodes[i].y - nodes[j].y;

                PosYX ant1 = {nodes[i].y + vecY, nodes[i].x + vecX};
                PosYX ant2 = {nodes[j].y - vecY, nodes[j].x - vecX};
                
                // Not-Inverted
                if(IsInBound(ant1) && antinodes.find(ant1) == antinodes.end())
                    antinodes.insert(ant1);
                // inverted
                if (IsInBound(ant2)  && antinodes.find(ant2) == antinodes.end())
                    antinodes.insert(ant2);
            }
        }
    }
}

int main(void)
{
    std::cout << "*** Advent of Code ***" << std::endl;
    std::cout << "### Day 08: Resonant Collinearity ###" << std::endl;
    std::cout << "--- Part 1 ---" << std::endl;
    
    std::ifstream in("input.txt", std::ios_base::in);

    std::string str;
    int y = 0;

    while(std::getline(in, str))
    {
        if(width == 0)
            width = str.length();
        
        for(int x=0; x < str.length(); x++)
            if(str[x] != '.')
                coordsMap[str[x]].push_back({y, x});

        y++;
    }

    height = y;

    std::cout << "Working with a " << height << "x" << width << " map \n";

    PrintMap();

    std::cout << "--------------------------------" << std::endl;

    std::unordered_set<PosYX, MyHashFunction> antinodes;
    CalculateAntinodes(antinodes);

    // for(auto node : antinodes)
    //     std::cout << "{" << node.y << "," << node.x << "}\n";

    std::cout << "The number of unique locations with the map that contain an antinodes is:" << antinodes.size() << std::endl;

    return 0;
}