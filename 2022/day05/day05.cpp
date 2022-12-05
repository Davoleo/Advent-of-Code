#include <iostream>
#include <fstream>
#include <stack>
#include <array>
#include <cstring>
#include <algorithm>
#include <vector>
#include <iterator>
#include <cassert>


std::array<std::stack<char>, 9> stack_arr;
std::array<std::stack<char>, 9> stack_arr_copy;

void move_crates(int n, std::stack<char>& from_stack, std::stack<char>& to_stack) {
	
	assert(from_stack.size() >= n);

	for (int i = 0; i < n; i++) {
		char popped = from_stack.top();
		from_stack.pop();
		to_stack.push(popped);
	}
}

void move_crates_9001(int n, std::stack<char>& from_stack, std::stack<char>& to_stack) {
	assert(from_stack.size() >= n);

	static char temp[32] = {};

	for (int i = 0; i < n; i++) {
		temp[i] = from_stack.top();
		from_stack.pop();
	}

	for (int i = n-1; i >= 0; i--)
		to_stack.push(temp[i]);
	
}

int main()
{

	std::cout << "** Advent of Code 2022 **" << std::endl;
	std::cout << "### Day 05: Supply Stacks ###" << std::endl;

	std::ifstream file;
	file.open("input.txt");

	std::vector<std::string> lines;

	do
	{	
		std::string line;
		std::getline(file, line);

		if(line.empty()) 
			break;

		std::cout << line << "\n";

		lines.emplace_back(line);
		
	}
	while(!file.eof());


	std::vector<std::string>::reverse_iterator rev = lines.rbegin();
	++rev; //Skip first line

	while (rev != lines.rend()) { 

		for (int i=1, j=0; i < rev->size() && j < 9; i+=4, j++) {
			char crate = rev->at(i);
			if (crate != ' ') 
				stack_arr.at(j).push(crate);
		}

		++rev;
	}

	for(int i=0; i<stack_arr.size(); ++i)
		stack_arr_copy[i] = std::stack<char>(stack_arr[i]);

	/////////////////////////////////////

//	for(int i=0; i<9; ++i)
//		std::cout << "Stack at " << i << " is size " << stack_arr.at(i).size() << "\n";

	do
	{
		int n_crate, src, dst;
		char trash[100];	//LA MONNEZZA FINISCE QUI

		file.clear();

		file >> trash >> n_crate >> trash >> src >> trash >> dst;
		//std::cout << n_crate << " " << src << " " << dst << "\n";

		move_crates(n_crate, stack_arr.at(src-1), stack_arr.at(dst-1));
		move_crates_9001(n_crate, stack_arr_copy.at(src-1), stack_arr_copy.at(dst-1));

	}
	while(!file.eof());

	std::cout << "Resulting top crates [Part 1]: [";
	for(std::stack<char> stack : stack_arr)
	{
		std::cout << stack.top();
	}
	std::cout << ']' << std::endl;

	std::cout << "Resulting top crates [Part 2 with 9001 crane]: [";
	for(std::stack<char> stack : stack_arr_copy)
	{
		std::cout << stack.top();
	}
	std::cout << ']' << std::endl;
}
