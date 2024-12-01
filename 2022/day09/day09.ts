import { assert } from "console";

const fs = require('fs');

interface Direction {
    vertical: Boolean,
    negative: Boolean
}

interface Vec2 {
    x: Number,
    y: Number
}
const head: Vec2 = {
    x: 0,
    y: 0
}
const tail: Vec2 = {
    x: 0,
    y: 0
}

function directionOf(char: String): Direction {
    switch (char) {
        case 'U':
            return { vertical: true, negative: false };
        case 'R':
            return { vertical: false, negative: false };
        case 'L':
            return { vertical: false, negative: true };
        case 'D':
            return { vertical: true, negative: true };
    }

    assert(false, "VALID CASES NOT MATCHED!");
    return { vertical: false, negative: false };
}

fs.readFile('input.txt', (err, data => {
    if (err)
        console.error(err);

    console.log("** Advent of Code 2022 **");
    console.log("### Day 9: Rope Bridge ###");
    console.log("--- Part 1 ---");

    data.toString().split('\n').forEach(line => {
        const direction = line.charAt(0);
        const times = Number(line.charAt(2))
        for (let i = 0; i < times; i++) {
            
        }
    })
}));

