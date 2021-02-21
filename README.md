### Run instructions

- build project with Gradle
- run main class: simulator.CpuSimulator with 1 argument: instruction filename
- example: simulator.CpuSimulator instructions.txt
- add instruction files you want to run to 'input' directory
- you can reference just the filename when running project, and it will look for file in 'input' directory
- output of cache values (numbers greater than 0), register A and B values output in log form

### Assumptions

- input format will always be "INSTRUCTION VALUE,VALUE"
- for any invalid instructions (doesn't match the list of instructions to support), they will be ignored

### Improvements/questions for future iteration

- input file validation and handling (is it the correct format, is the instruction supported?)
- should we reject an instruction file if invalid instructions or skip over them?
- support arbitrarily more for-use registers (ex. C, D, E, etc)