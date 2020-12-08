package day8

class Day8 {

    //yeah it's so fucking bad, (I don't have enough time to spend making it better)
    static void main(String[] args) {
        File file = new File("2020/day8/input.txt")
        //File file = new File("input.txt")

        String[] boot = file.text.split('\n')
        int programCounter = 0
        int accumulator = 0

        for (int i = 0; i < boot.length; i++) {
            String[] newBoot = Arrays.copyOf(boot, boot.length)
            ///
            // println(i)
            if (newBoot[i].startsWith('acc'))
                continue

            if (newBoot[i].startsWith('jmp'))
                newBoot[i] = newBoot[i].replaceAll('jmp', 'nop')
            else if (newBoot[i].startsWith('nop'))
                newBoot[i] = newBoot[i].replaceAll('nop', 'jmp')

            List<Integer> executedInstructions = new ArrayList<>()
            accumulator = 0
            int prev = 0
            programCounter = 0

            println("------------------------------------")

            while (!executedInstructions.contains(programCounter.intValue()) && programCounter < newBoot.length) {
                String[] instr = newBoot[programCounter].split(' ')

                println(programCounter)
                prev = programCounter

                switch (instr[0]) {
                    case 'jmp':
                        //println("jump")
                        programCounter += instr[1].toInteger()
                        break
                    case 'acc':
                        //println("accumulating")
                        accumulator += instr[1].toInteger()
                        programCounter++
                        break
                    default:
                        //println("nothing")
                        programCounter++
                        break
                }

                executedInstructions.add(prev)
            }

            if (programCounter >= newBoot.length)
                break
        }

        if (programCounter >= boot.length) {
            println(accumulator)
        }
        else
            println("broken")
    }
}
