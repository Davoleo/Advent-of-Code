const fs = require('fs');

fs.readFile('input.txt', (error, data) => {
    if (error)
        console.error(error);

    const seats = data.toString().split('\n');
    const seatIds = [];
    compute(seats, seatIds);
    seatIds.sort((a, b) => a - b);
    findSeat(seatIds);
});

function compute(seatsArr, seatIds) {

    let maxSeatId = 0;

    seatsArr.forEach(seat => {

        let rowString = '';
        for (let i = 0; i < 7; i++) {
            rowString += seat.charAt(i) === 'F' ? '0' : '1';
        }

        let colString = ''
        for (let i = 7; i < 10; i++) {
            colString += seat.charAt(i) === 'L' ? '0' : '1';
        }

        //console.log(rowString + " | " + colString);

        const rowNum = parseInt(rowString, 2);
        const colNum = parseInt(colString, 2);
        const seatId = (rowNum << 3) + colNum;

        //console.log(rowNum + " | " + colNum + " | " + seatId);
        seatIds.push(seatId);
        
        if (maxSeatId < seatId)
            maxSeatId = seatId;
    });

    console.log("The highest Seat UID is: " + maxSeatId);
}

function findSeat(seats) {
    for (let i = 36; i <= 944; i++) {
        if (seats.indexOf(i) === -1) {
            console.log("Your seat ID is: " + i);
        }
    }
}