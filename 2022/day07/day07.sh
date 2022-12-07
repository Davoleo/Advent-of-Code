#!/bin/bash

# absolute path to file tree folder
FILE_TREE=$(pwd)/filetree

MAX_SIZE=100000

cleanup_tree() {
  rm -rf "${FILE_TREE:?}/*"
}

build_filetree() {
  # Read All lines one by one
  while read -r line; do

    # Skip first 2 lines, and CD to the file tree root
    if [ "$line" == "$ cd /" ]; then
      cd "$FILE_TREE" || (echo "ERROR: while CDing to root"; exit)
      continue
    fi

    if [ "$line" == "$ ls" ]; then
      continue
    fi

    # if the line starts with $ it means it's a command -> Execute it
    if grep "^$ " <<< "$line"; then
      $(echo "$line" | tr --delete '$')
      continue
    fi
    # otherwise

    # parse the line in its 2 parts divided by ' '
    FILE_SIZE=$(cut -d' ' -f1 <<< "$line")
    FILE_NAME=$(cut -d' ' -f2 <<< "$line")

    # if the first part !equals to "dir" -> it means it's storing a number stating the file size
    if [ "$FILE_SIZE" != "dir" ]; then
      # Create the file and write the fictional size inside of it
      touch "$FILE_NAME"
      echo "$FILE_SIZE" > "$FILE_NAME"
    else
      # otherwise create a directory with that name
      mkdir "$FILE_NAME"
    fi

  done < input.txt
}

# Super slow KEKW
day07_part1() {
  echo "--- Part 1 ---"
  cd "$FILE_TREE" || exit 1
  total=0
  for dir in $(find . -type d); do
    sum=0
    for file in $(find "$dir" -type f); do
      sum=$((sum + $(cat "$file")))
    done
    if [ $sum -le $MAX_SIZE ]; then
      total=$((total + sum))
    fi
  done
  echo "Total space amount: $total"
}

echo "** Advent of Code 2022 **"
echo "### Day 07: No Space Left On Device"

#build_filetree

day07_part1
