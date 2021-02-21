package simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class CpuSimulator {

  static final int CACHE_SIZE = 256;
  static final int MAX_VALUE = 256;

  static Logger log = Logger.getLogger(CpuSimulator.class.getName());
  static Cache cache = new Cache(CACHE_SIZE);
  static List<String> instructions = new ArrayList<>();
  static Map<String, Integer> registers = new HashMap<>();
  static int instructionCounter = 0;

  public static void main(String[] args) {

    // create map of for-use registers, in this case statically creating 2
    initializeRegisters();

    // expecting user input for instruction file
    if (args.length != 1) {
      log.warning("Incorrect number of parameters. Exiting...");
      System.exit(0);
    }

    // creating instruction list from file
    try (BufferedReader reader = new BufferedReader(new FileReader("input/" + args[0]))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] thisLine = line.split(" ");
        instructions.add(thisLine[0]);
        instructions.add(thisLine[1].split(",")[0]);
        instructions.add(thisLine[1].split(",")[1]);
      }
    } catch (IOException e) {
      log.warning("File not found...");
      System.exit(0);
    }

    // expecting a format of 3 arguments per instruction
    // running each instruction
    while (instructionCounter + 2 < instructions.size()) {
      processInstruction(instructions.get(instructionCounter));
    }

    // output
    log.info(cache.printCacheValues());
    log.info("Register A Value: " + registers.get("A"));
    log.info("Register B Value: " + registers.get("B"));

  }

  public static void initializeRegisters(){
    registers.put("A", 0);
    registers.put("B", 0);
  }

  public static void processInstruction(String operation) {
    String operand1;
    String operand2;
    Integer value1;
    Integer value2;
    Integer newValue;

    switch (operation) {
      case "SET": // saving value from operand2 to operand1
        instructionCounter++;
        operand1 = instructions.get(instructionCounter);
        instructionCounter++;
        operand2 = instructions.get(instructionCounter);
        value2 = registers.containsKey(operand2) ? registers.get(operand2) : Integer.parseInt(operand2);
        registers.put(operand1, value2);
        instructionCounter++;
        break;
      case "ADD": // adding operand2 to operand1 and saving this in operand1
        instructionCounter++;
        operand1 = instructions.get(instructionCounter);
        instructionCounter++;
        operand2 = instructions.get(instructionCounter);
        value1 = registers.get(operand1);
        value2 = registers.containsKey(operand2) ? registers.get(operand2) : Integer.parseInt(operand2);
        newValue = (value1 + value2) % MAX_VALUE;
        registers.put(operand1, newValue);
        instructionCounter++;
        break;
      case "SUBT": // subtracting operand2 from operand1 and saving this in operand2
        instructionCounter++;
        operand1 = instructions.get(instructionCounter);
        instructionCounter++;
        operand2 = instructions.get(instructionCounter);
        value1 = registers.get(operand1);
        value2 = registers.containsKey(operand2) ? registers.get(operand2) : Integer.parseInt(operand2);
        newValue = (value1 - value2) % MAX_VALUE;
        registers.put(operand1, newValue);
        instructionCounter++;
        break;
      case "LSHIFT": // left shift by operand2 bits and save to operand1
        instructionCounter++;
        operand1 = instructions.get(instructionCounter);
        instructionCounter++;
        operand2 = instructions.get(instructionCounter);
        value1 = registers.get(operand1);
        value2 = registers.containsKey(operand2) ? registers.get(operand2) : Integer.parseInt(operand2);
        newValue = value1 << value2;
        registers.put(operand1, newValue);
        instructionCounter++;
        break;
      case "READ": // read value from cache and store in operand1
        instructionCounter++;
        operand1 = instructions.get(instructionCounter);
        instructionCounter++;
        operand2 = instructions.get(instructionCounter);
        newValue = registers.containsKey(operand2) ? registers.get(operand2) : cache.getFromCache(Integer.parseInt(operand2));
        registers.put(operand1, newValue);
        instructionCounter++;
        break;
      case "WRITE": // save operand1 value to cache
        instructionCounter++;
        operand1 = instructions.get(instructionCounter);
        instructionCounter++;
        operand2 = instructions.get(instructionCounter);
        value1 = registers.get(operand1);
        value2 = registers.containsKey(operand2) ? registers.get(operand2) : Integer.parseInt(operand2);
        cache.putInCache(value2, value1);
        instructionCounter++;
        break;
      default:
        log.warning("invalid instruction");
        instructionCounter+=2;
        break;
    }
  }

}
