const fs = require('fs');

fs.readFile("input.txt", (err, data) => {
    if (err)
        console.error(err);

    console.log("Advent of Code 2022 - Day 01")

    //Split on empty line
    const elves = data.toString().split("\n\n");
    getMaxElfCalories(elves);
    getTop3ElvesCalories(elves);
});

function getMaxElfCalories(elves) {
    let maxCalories = 0;

    elves.forEach(elf => {
        //For each line [food item calories]
        const foodItems = elf.split('\n');

        //total elf calories accumulator
        let elfCalories = 0;
        for (const item of foodItems) {
            elfCalories += parseInt(item);
        }

        //if current elf calories are greater 
        //than max calories previously computed update the maximum with the new value
        if (elfCalories > maxCalories) {
            maxCalories = elfCalories;
        }
    });

    console.log("--- Part 1 ---")
    console.log(maxCalories);
}

function getTop3ElvesCalories(elves) {

    const elvesTotCalories = elves.map(elf => {
        //For each line [food item calories]
        const foodItems = elf.split('\n');

        //total elf calories accumulator
        let elfCalories = 0;
        for (const item of foodItems) {
            elfCalories += parseInt(item);
        }

        return elfCalories;
    }).sort((a, b) => b - a); //Sort in reverse order

    console.log("--- Part 2 ---")
    console.log("Top 3 Hoarder Elves: " + elvesTotCalories[0], elvesTotCalories[1], elvesTotCalories[2]);
    console.log("Total Hoarding Value: " + (elvesTotCalories[0] + elvesTotCalories[1] + elvesTotCalories[2]))
    
}