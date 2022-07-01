package sdf.assessment;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public class MailMerge {

  private LinkedList<String> variablesList = new LinkedList<>();
  private LinkedList<String> usersData = new LinkedList<>();
  private LinkedList<String> mailTemplate = new LinkedList<>();

  public void run(String[] args){

    String csvFile = "";
    String template = "";

    if(args.length == 2){
      csvFile = args[0];
      template = args[1]; 

      for(String file: args){
        if(checkFileExist(file) == false){
          System.out.printf("File: %s does not exist in Task01 folder\n",file);
          System.exit(1);
        }
      }

    }else{
      System.out.println("Run program in the format java sdf.assessment.Main <CSV file> <template file>");
      System.exit(1);
    }
    
    readCsvFile(csvFile);
    readTemplateFile(template);
    
    for(String user: usersData){
      printMail(user);
    }
  }

  public void printMail(String user){
    String[] userInfo = user.split(",");
    LinkedList<String> mailMerge = new LinkedList<>();
    for(String mailLine: mailTemplate){
      String mailLineToPrint = mailLine;
      for(int i=0; i<variablesList.size();i++){
        if(mailLineToPrint.contains(variablesList.get(i))){
          mailLineToPrint = mailLineToPrint.replace(variablesList.get(i), userInfo[i]);
        }
      }
      mailMerge.add(mailLineToPrint);
    }

    for(String mailMergeLines: mailMerge){
      System.out.println(mailMergeLines);
    }
    System.out.println();
  }

  public void readCsvFile(String file){
    try{
      File csvFile = new File("./"+file);
      Scanner readFile = new Scanner(csvFile);
      LinkedList<String> process = new LinkedList<>();
      while(readFile.hasNextLine()){
        process.add(readFile.nextLine());
      }
      for(int i=0; i<process.size();i++){
        if(i == 0){
          String[] variables = process.get(0).split(",");
          for(String variable: variables){
            variablesList.add("__"+variable+"__");
          }
        }else{
          usersData.add(process.get(i));
        }
      }
      readFile.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void readTemplateFile(String file){
    try{
      File csvFile = new File("./"+file);
      Scanner readFile = new Scanner(csvFile);
      while(readFile.hasNextLine()){
        mailTemplate.add(readFile.nextLine());
      }
      readFile.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public static boolean checkFileExist(String file){
    Path path = Paths.get("./"+file);
    boolean fileExist = Files.exists(path);
    return fileExist;
  }
  
}
