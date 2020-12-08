import 'dart:io';

main() {
  File inputFile = new File("input.txt");

  String input = inputFile.readAsStringSync();
  int sum = anyPositivePerGroup(input.split("\n\n"));
  print("The total number of positive answers from all groups is: $sum\n");
  List<String> inputLines = inputFile.readAsLinesSync();
  int sum2 = allPositivePerGroup(inputLines);
  print("The total number of answers that were positive for each member of the group is: $sum2\n");

}

//Works out of the box (only 1 newline after the last line of the input file)
int anyPositivePerGroup(List<String> groupList) {

  int sum = 0;

  for (String group in groupList) {
    //print("$group\n----------------------------------------");
    List<int> chars = new List<int>();

    for (var i = 0; i < group.length; i++) {
      int char = group.codeUnitAt(i);
      if (char != '\n'.codeUnitAt(0)) {
        if (!chars.contains(char)) {
          chars.add(char);
          sum++;
        }
      }
    }
  }

  return sum;
}

//Needs 2 new lines after the last line to work
int allPositivePerGroup(List<String> lines) {
  int sum = 0;

  List<List<String>> groupedLines = List();
  int baseIndex = 0;

  for (int i = 0; i < lines.length; i++) {
    //print(lines[i]);
    if (lines[i].toUpperCase() == lines[i]) {
      print("$i | $baseIndex");
      groupedLines.add(lines.sublist(baseIndex, i));
      baseIndex = i + 1;
    }
  }

  for (var group in groupedLines) {
    List<int> chars = new List();
    for (var i = 0; i < group[0].length; i++) {
      bool valid = true;
      for (String member in group) {
        //print(member);
        if (!member.runes.contains(group[0].codeUnitAt(i))) {
          valid = false;
        }
      }

      if (valid)
        chars.add(group[0].codeUnitAt(i));
    }

    sum += chars.length;
  }


  return sum;
}

