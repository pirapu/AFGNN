package pdg_gui;

import com.github.javaparser.ParseException;
import graphStructures.GraphNode;
import graphStructures.RelationshipEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import pdg.PDGCore;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.jgrapht.ext.StringNameProvider;

/*  STEPS TO RUN

cd COMP_project/
mvn clean install
mvn exec:java -Dexec.mainClass=pdg_gui.mainframe 
*/

public class mainframe {
    private File selectedFile;
    private String selectedFileName;
    @SuppressWarnings("rawtypes")
    private DirectedGraph<GraphNode, RelationshipEdge> hrefGraph;
    private PDGCore astPrinter = new PDGCore();
    static String outputFolder = "./API-misuse/MuBench-Data/before_pruning/";
    static String inputFolder = "./API-misuse/MuBench-Data/after_processing/";

    // Get all .java files
    static private ArrayList<String> getListOfFiles(String dirPath) {
        ArrayList<String> listOfFiles = new ArrayList<String>();
        File directory = new File(dirPath);
        File foldersList[] = directory.listFiles();
        for (File folder : foldersList) {
            if (folder.isDirectory()) {
                File filesList[] = folder.listFiles();
                for (File file : filesList) {
                    if (file.getAbsolutePath().endsWith(".java")) {
                        listOfFiles.add(file.getAbsolutePath());
                    }
                }
            }
        }
        System.out.println("\n\nNumber Of Files: " + listOfFiles.size());
        return listOfFiles;
    }

    // Get all folders inside a folder
    static private ArrayList<String> getListOfFolders(String dirPath) {
        ArrayList<String> listOfFolders = new ArrayList<String>();
        File directory = new File(dirPath);
        for (File folder : directory.listFiles()) {
            if (!folder.getAbsolutePath().endsWith("DS_Store")) {
                listOfFolders.add(folder.getAbsolutePath());
            }
        }
        System.out.println("\n\nNumber Of folders: " + listOfFolders.size());
        return listOfFolders;
    }

    // constructor
    private mainframe() {
        createGraph();
    }

    private void methods(String filename, String apiName, String sampleName) {

        selectedFile = new File(filename);
        selectedFileName = selectedFile.getName();

        // System.out.println("***************");
        // System.out.println(selectedFileName);
        // System.out.println("***************");

        runAnalysisAndMakeGraph(apiName, sampleName);

        // ExportToDot
        FileOutputStream out;
        try {
            checkIfFolderExists(outputFolder + apiName + "/");
            GraphNode.exporting = true;
            String fn = selectedFileName.substring(0, selectedFileName.length() - 5) + "_" + sampleName + "_" + apiName
                    + ".dot";

            out = new FileOutputStream(outputFolder + apiName + "/" + fn);
            @SuppressWarnings("rawtypes")
            DOTExporter<GraphNode, RelationshipEdge> exporter = new DOTExporter<>(
                    new StringNameProvider<>(), null,
                    new StringEdgeNameProvider<>());
            exporter.export(new OutputStreamWriter(out), hrefGraph);
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        GraphNode.exporting = false;
    }

    private boolean checkIfFolderExists(String folderLocation) {
        File theDir = new File(folderLocation);
        return !theDir.exists() && theDir.mkdir();
    }

    private void createGraph() {
        hrefGraph = new DefaultDirectedGraph<>(RelationshipEdge.class);
    }

    private void runAnalysisAndMakeGraph(String apiName, String sampleName) {
        try {
            createGraph();
            GraphNode gn = new GraphNode(0, "Entry", "null");
            hrefGraph.addVertex(gn);

            if (astPrinter.addFile(new FileInputStream(selectedFile), hrefGraph, gn, "null",
                    selectedFileName, outputFolder + apiName + "/", sampleName + "_" + apiName)) {
                //System.out.println("astPrinter called");
            }

        } catch (ParseException | com.github.javaparser.TokenMgrError | IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new PDGCore();
        try {
            mainframe obj = new mainframe();
            boolean isOnlyFile = false;
            String generateFor = "mubench"; // "code2seq" or "ck" or "crypto-api" or "codenet" or "perturbed-code" or
                                             // "big-clone-bench" or "bug-detection" or "llm-data" or "mubench" or "au500"
            if (isOnlyFile) {
                outputFolder = ".\\API misuse prediction\\Repository\\PdgGeneratorModified\\COMP_project\\Testcases\\src\\aftermodified\\last-update\\";
                String file = ".\\API misuse prediction\\Repository\\PdgGeneratorModified\\COMP_project\\Testcases\\src\\testcase-last-update\\example_10.java"; 
                String apiName = "NA";
                String sampleName = "sample-0";
                obj.methods(file, apiName, sampleName);
            } else if (generateFor.equals("ck")) {
                ArrayList<String> folders = getListOfFolders(inputFolder);
                // System.out.println("\n\nAPI Folders: " + folders);
                for (String folder : folders) {
                    int lastIndex = folder.lastIndexOf("/");
                    String apiName = folder.substring(lastIndex + 1);
                    // System.out.println("\n\nFolder Name: " + folder);
                    System.out.println("\n\nAPI Name: " + apiName);
                    ArrayList<String> files = getListOfFiles(folder);
                    for (String file : files) {
                        // System.out.println("\n\nFile Name: " + file);
                        int lastIndexInFile1 = file.lastIndexOf("/");
                        int lastIndexInFile2 = file.lastIndexOf("/", lastIndexInFile1 - 1);
                        String sampleName = file.substring(lastIndexInFile2 + 1, lastIndexInFile1);
                        // System.out.println("\n\nsampleName: " + sampleName);
                        obj.methods(file, apiName, sampleName);
                    }
                }
            } else if (generateFor.equals("code2seq") || generateFor.equals("codenet") || generateFor.equals("mubench") || generateFor.equals("au500")
                    || generateFor.equals("perturbed-code") || generateFor.equals("bug-detection") || generateFor.equals("llm-data")) {

                // Find the APIs that are already processed
                // HashSet<String> processed_apis = new HashSet<>();
                // try {
                //     Scanner scanner = new Scanner(new File(
                //             "./API-misuse/PDG-gen/PdgGeneratorModified/logs/processed-api.log"));
                //     while (scanner.hasNextLine()) {
                //         String line = scanner.nextLine();
                //         if (line.startsWith("INFO: /home")) {
                //             line = line.substring(6);
                //             processed_apis.add(line);
                //         }
                //     }
                //     scanner.close();
                // } catch (FileNotFoundException e) {
                //     e.printStackTrace();
                // }

                // // setup the logger for projects processing data
                // Logger processed_apis_file = Logger.getLogger("ProcessedApiLog");
                // FileHandler fh;
                // try {
                //     // This block configure the logger with handler and formatter
                //     fh = new FileHandler(
                //             "./API-misuse/PDG-gen/PdgGeneratorModified/logs/processed-api.log");
                //     processed_apis_file.addHandler(fh);
                //     SimpleFormatter formatter = new SimpleFormatter();
                //     fh.setFormatter(formatter);
                //     processed_apis_file.setUseParentHandlers(false);
                // } catch (SecurityException e) {
                //     e.printStackTrace();
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }

                ArrayList<String> folders = getListOfFolders(inputFolder);
                // processed_apis_file.info("Total Number of APIs: " + folders.size() + "\n");

                // // Add already processed APIs to the logs
                // for (String ele : processed_apis) {
                //     processed_apis_file.info(ele + "\n");
                // }

                for (String folder : folders) {
                    
                    // If the API is already proceseed, skip it
                    // if(processed_apis.contains(folder)){
                    //     System.out.println("\nAlready Project Processed: " + folder);
                    //     continue;
                    // }

                    int lastIndex = folder.lastIndexOf("/");
                    String apiName = folder.substring(lastIndex + 1);
                    // System.out.println("\n\nFolder Name: " + folder);
                    System.out.println("\nAPI Name (processing for): " + apiName);

                    // Get the files for the API
                    ArrayList<String> listOfFiles = new ArrayList<String>();
                    File directory = new File(folder);
                    File filesList[] = directory.listFiles();
                    for (File file : filesList) {
                        if (file.getAbsolutePath().endsWith(".java")) {
                            listOfFiles.add(file.getAbsolutePath());
                        }
                    }

                    for (String file : listOfFiles) {
                        System.out.println("\nProcessing File: " + file);
                        obj.methods(file, apiName, "NA");
                    }

                    // processed_apis.add(folder);
                    // processed_apis_file.info(folder + "\n");
                    // processed_apis_file.info("Number of processed APIs: " + processed_apis.size() + "\n");
                }
            } else if (generateFor.equals("crypto-api") || generateFor.equals("big-clone-bench")) {
                ArrayList<String> listOfFiles = new ArrayList<String>();
                File directory = new File(inputFolder);
                File filesList[] = directory.listFiles();
                for (File file : filesList) {
                    if (file.getAbsolutePath().endsWith(".java") && !file.getAbsolutePath().contains("Http")) {
                        listOfFiles.add(file.getAbsolutePath());
                    }
                }

                for (String file : listOfFiles) {
                    System.out.println("\n\nFile Name: " + file);
                    obj.methods(file, "NA", "NA");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
