#!/bin/bash

# absolute path to file tree folder
FILE_TREE=$(pwd)/filetree
mkdir -p "$FILE_TREE"

MAX_SIZE=100000

FILESYSTEM_SPACE=70000000
UPDATE_SIZE=30000000
USED_SPACE=0
FREE_SPACE=0

cleanup_tree() {
  rm -rf "${FILE_TREE:?}/*"
}

recursive_dir_size() {
  local dir=$1
  local sum=0
  for file in $(find "$dir" -type f); do
    sum=$((sum + $(cat "$file")))
  done
  echo $sum
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
    sum=$(recursive_dir_size "$dir")
    if [ "$sum" -le $MAX_SIZE ]; then
      total=$((total + sum))
    fi
  done
  echo "Total space amount: $total"
}

day07_part2() {
  echo "--- Part 2 ---"
  cd "$FILE_TREE" || exit 1

  for file in $(find . -type f); do
    if [ -f "$file" ]; then
      USED_SPACE=$((USED_SPACE + $(cat "$file")))
    fi
  done

  FREE_SPACE=$((FILESYSTEM_SPACE - USED_SPACE))
  echo "Needed Space: $((UPDATE_SIZE - FREE_SPACE))"

  SIZES=$(for dir in $(find . -type d); do
    recursive_dir_size "$dir"
  done | sort --numeric-sort)

  echo "recursive dir size calculation complete!"

  set +x
  for size in $SIZES; do
    if [ "$size" -gt $((UPDATE_SIZE - FREE_SPACE)) ]; then
      echo "Smallest directory size to delete: $size"
      break
    fi
  done
  set -x
}

echo "** Advent of Code 2022 **"
echo "### Day 07: No Space Left On Device"

cleanup_tree

build_filetree

day07_part1

day07_part2
