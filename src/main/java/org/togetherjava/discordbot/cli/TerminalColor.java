package org.togetherjava.discordbot.cli;

/**
 * ANSII escape sequences for colours.
 */
public enum TerminalColor {
  //  RESET_COLOR("\u001b[39;49m"),
  RESET("\u001b[0m"),
  BOLD("\u001b[1m"),
  DIM("\u001b[2m"),
  ITALIC("\u001b[3m"),
  UNDERLINE("\u001b[4m"),
  FRAMED("\u001b[51m"),
  BLACK("\u001b[30m"),
  RED("\u001b[31m"),
  GREEN("\u001b[32m"),
  YELLOW("\u001b[33m"),
  BLUE("\u001b[34m"),
  MAGENTA("\u001b[35m"),
  CYAN("\u001b[36m"),
  GRAY("\u001b[37m"),
  BRIGHT_BLACK("\u001b[90m"),
  BRIGHT_RED("\u001b[91m"),
  BRIGHT_GREEN("\u001b[92m"),
  BRIGHT_YELLOW("\u001b[93m"),
  BRIGHT_BLUE("\u001b[94m"),
  BRIGHT_MAGENTA("\u001b[95m"),
  BRIGHT_CYAN("\u001b[96m"),
  BRIGHT_WHITE("\u001b[97m");

  private String escapeSequence;

  TerminalColor(String escapeSequence) {
    this.escapeSequence = escapeSequence;
  }

  @Override
  public String toString() {
    return escapeSequence;
  }
}

