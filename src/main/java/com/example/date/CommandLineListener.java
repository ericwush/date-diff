package com.example.date;

import java.util.Observable;
import java.util.Scanner;

/**
 * I listen to the command line and notify on input.
 */
public class CommandLineListener extends Observable implements Runnable {

  @Override
  public void run() {
    while (true) {
      String commandLineString = new Scanner(System.in).nextLine();
      setChanged();
      notifyObservers(commandLineString);
    }
  }

}
