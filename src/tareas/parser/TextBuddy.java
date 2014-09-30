package tareas.parser;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class manipulates text in a text file, allowing a user to:
 * <ol>
 * <li>Add a line (syntax: add [line]),</li>
 * <li>Show all existing lines with line numbers (syntax: display),</li>
 * <li>Delete a line (syntax: delete [line_number]),</li>
 * <li>Clear the text file (syntax: clear),</li>
 * <li>Save the text file (syntax: save),</li>
 * <li>List all available commands (syntax: help),</li>
 * <li>Sort all lines. (syntax: sort -a to sort ascendingly, sort -d to sort descendingly),</li>
 * <li>Search for a word in the file (syntax: search [word]),</li>
 * <li>Exit the program (syntax: exit).</li>
 * </ol>
 * <p/>
 * The main program should accept the name of the text file to be manipulated as its first and the only argument.
 * Any other arguments will be discarded.
 * <p/>
 * The text file will be saved when the user exits the program, or he/she enters the Save command.
 *
 * @author Kent
 */

public class TextBuddy {

//region === String resources

    private static final String MESSAGE_WELCOME = "Welcome to TextBuddy.";
    private static final String MESSAGE_READY = "%s is ready to use.";
    private static final String MESSAGE_ADD = "Added to %1$s: \"%2$s\".";
    private static final String MESSAGE_DELETE = "Deleted from %1$s: \"%2$s\".";
    private static final String MESSAGE_CLEAR = "All content deleted from %s.";
    private static final String MESSAGE_EMPTY = "%s is empty.";
    private static final String MESSAGE_SAVED = "%s has been saved.";
    private static final String MESSAGE_SORTED_ASC = "All lines have been sorted ascendingly.";
    private static final String MESSAGE_SORTED_DES = "All lines have been sorted descendingly.";
    private static final String MESSAGE_WORD_NOT_FOUND = "No lines match your search.";

    private static final String OUTPUT_DISPLAY = "%d. %s";
    private static final String OUTPUT_AVAILABLE_COMMANDS = "Available commands: %s.";
    private static final String OUTPUT_SEARCH = "Search result for \"%1$s\": \n%2$s";

    private static final String PROMPT_FOR_COMMAND = "command: ";
    private static final String PROMPT_SAVE = "Do you want to save changes to %s? (Y/N) ";
    private static final String ERROR_FILENAME_EXPECTED = "Error! File name expected.";
    private static final String ERROR_FILE_READ = "Error! Unable to read %1$s.\n%2$s";
    private static final String ERROR_INDEX_OUT_OF_BOUND = "Error! Index out of bound.";
    private static final String ERROR_UNABLE_TO_WRITE = "Error! Unable to write to %s.";
    private static final String ERROR_INVALID_COMMAND = "Error! Unknown command. " +
            "Enter \"help\" to list available commands.";
    private static final String ERROR_INVALID_ARGUMENT = "Error! Invalid arguments for command \"%s\".";

//endregion

//region === Input, Output & Commands

    private static Scanner mScanner = new Scanner(System.in);

    private void showMessage(String message) {
        System.out.println(message);
    }

    private String promptRead(String prompt) {
        System.out.print(prompt);
        return mScanner.nextLine();
    }

    private boolean promptYesNo(String prompt) {
        return promptRead(prompt).trim().toLowerCase().equals("y");
    }

    /**
     * Execute a command.
     *
     * @param command The command, as a TextBuddyCommand
     * @return Output of command execution
     */
    public String executeCommand(TextBuddyCommand command) {
        CommandType type = command.getType();
        String[] arguments = command.getArguments();

        // if number of arguments found is different from expectation, it's an error
        if (arguments.length != type.getArgumentCount()) {
            return String.format(ERROR_INVALID_ARGUMENT, type.getCommand());
        }

        switch (type) {
            case ADD_COMMAND:
                return addLine(arguments[0]);

            case DISPLAY_COMMAND:
                return displayAllLines();

            case DELETE_COMMAND:
                return deleteLine(arguments[0]);

            case CLEAR_COMMAND:
                return clearAllLines();

            case SAVE_COMMAND:
                return saveFile();

            case HELP_COMMAND:
                return displayAllCommands();

            case SORT_COMMAND:
                return sort(arguments[0]);

            case SEARCH_COMMAND:
                return search(arguments[0]);

            case EXIT_COMMAND:
                return exitProgram();

            default:
                return ERROR_INVALID_COMMAND;
        }
    }

    private String displayAllCommands() {
        return String.format(OUTPUT_AVAILABLE_COMMANDS, StringUtils.join(CommandType.getAllCommands(), ", "));
    }

    public void startLoop() {
        String inputLine;
        TextBuddyCommand command;
        String output;

        do {
            inputLine = promptRead(PROMPT_FOR_COMMAND);
            command = TextBuddyCommand.analyzeCommand(inputLine);
            output = executeCommand(command);
            showMessage(output);

        } while (command.getType() != CommandType.EXIT_COMMAND);
    }

//endregion

//region === File Handling

    private String mFileName;

    public String getFileName() {
        return mFileName;
    }

    /**
     * Try to read from the text file.
     * If file name is not present or the file can't be read,
     * the user must either enter a valid file name or quit the program.
     *
     * @param fileName the name of the text file
     * @return true if the file is successfully read, false otherwise.
     */
    private boolean openFile(String fileName) {
        clearAllLines();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            mFileName = fileName;
            showMessage(retrieveFileContent(reader));
            return true;
        } catch (IOException e) { // Other exceptions
            showMessage(String.format(ERROR_FILE_READ, fileName, e.toString()));
            return false;
        }

    }

    private String retrieveFileContent(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            addLine(line);
        }

        hasChanged = false;
        return String.format(MESSAGE_READY, getFileName());
    }

    private String saveFile() {
        if (hasChanged) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFileName()))) {
                for (String line : mLines) {
                    writer.write(line);
                    writer.newLine();
                }

                hasChanged = false;
                return String.format(MESSAGE_SAVED, mFileName);
            } catch (IOException e) {
                return String.format(ERROR_UNABLE_TO_WRITE, mFileName);
            }
        } else {
            return "";
        }
    }

    private String exitProgram() {
        if (hasChanged && promptYesNo(String.format(PROMPT_SAVE, mFileName))) {
            return saveFile();
        } else {
            return "";
        }
    }

//endregion

//region === Line Array

    private ArrayList<String> mLines = new ArrayList<>();
    private boolean hasChanged = false;

    private String clearAllLines() {
        if (mLines.size() > 0) {
            hasChanged = true;
        }

        mLines.clear();

        return (mFileName != null) ? String.format(MESSAGE_CLEAR, mFileName) : "";
    }

    private String formatForDisplay(ArrayList<String> lines) {
        String ret = "";

        for (int i = 0; i < lines.size(); i++) {
            ret += String.format(OUTPUT_DISPLAY, i + 1, lines.get(i)) + "\n";
        }

        return ret;
    }

    private String displayAllLines() {
        if (mLines.size() == 0) {
            return String.format(MESSAGE_EMPTY, mFileName);
        } else {
            return formatForDisplay(mLines);
        }
    }

    private String addLine(String line) {
        mLines.add(line);
        hasChanged = true;

        return (mFileName != null) ? String.format(MESSAGE_ADD, mFileName, line) : "";
    }

    private String deleteLine(String arg) {
        try {
            int index = Integer.parseInt(arg) - 1;

            if (index < 0 || index >= mLines.size()) {
                return ERROR_INDEX_OUT_OF_BOUND;
            }

            String tmp = mLines.get(index);

            mLines.remove(index);
            hasChanged = true;

            return String.format(MESSAGE_DELETE, mFileName, tmp);
        } catch (Exception e) {
            return String.format(ERROR_INVALID_ARGUMENT, CommandType.DELETE_COMMAND.getCommand());
        }
    }

    private String sort(String argument) {
        if (mLines.isEmpty()) {
            return String.format(getFileName(), MESSAGE_EMPTY);
        } else {
            if (argument.toLowerCase().equals("-a")) {
                Collections.sort(mLines);
                return MESSAGE_SORTED_ASC;

            } else if (argument.toLowerCase().equals("-d")) {
                Collections.sort(mLines, Collections.reverseOrder());
                return MESSAGE_SORTED_DES;

            } else {
                return ERROR_INVALID_ARGUMENT;
            }
        }
    }

    private String search(String word) {
        ArrayList<String> result = new ArrayList<>();
        word = word.toLowerCase();

        for (String line : mLines) {
            if (line.contains(word)) {
                result.add(line);
            }
        }

        if (result.isEmpty()) {
            return MESSAGE_WORD_NOT_FOUND;
        } else {
            return String.format(OUTPUT_SEARCH, word, formatForDisplay(result));
        }

    }

//endregion

    /**
     * The program's main subroutine.
     * First, the file name is read from the arguments.
     * Then the program loops consecutively, waiting for commands.
     *
     * @param args Arguments to the main subroutine.
     */
    public static void main(String[] args) {
        TextBuddy textBuddy = new TextBuddy();

        textBuddy.showMessage(MESSAGE_WELCOME);

        if (args.length < 1) {
            textBuddy.showMessage(ERROR_FILENAME_EXPECTED);
        } else if (textBuddy.openFile(args[0])) {
            textBuddy.startLoop();
        }

    }
}
