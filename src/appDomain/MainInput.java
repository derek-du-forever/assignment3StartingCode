package appDomain;

import utilities.WordItemPrinter;

/**
 * Class representing the main input configuration,
 * including input/output file paths and the printer to use.
 */
public class MainInput {
    private String inputFilePath;
    private String outputFilePath;
    private WordItemPrinter printer;

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public WordItemPrinter getPrinter() {
        return printer;
    }

    public void setPrinter(WordItemPrinter printer) {
        this.printer = printer;
    }

}