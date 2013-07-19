package org.apache.uima.examples.cpe;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class shangqingCVSconsummer {
	
	
	
	
	
	public void  readeCsv(){
        try {    
             
            ArrayList<String[]> csvList = new ArrayList<String[]>(); 
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            String csvFilePath = "resources/mayodata2.csv";
            CsvReader reader = new CsvReader(new FileReader(csvFilePath));
             //CsvReader reader = new CsvReader(csvFilePath,',',Charset.forName("SJIS"));    
           
            	 System.out.println(reader.get(0));
             
             reader.readHeaders(); 
             
             String[] nextLine;
             
                      
             reader.close();
            // System.out.println(csvList.size());
             for(int row=0;row<csvList.size();row++){
                 
                 String  cell = csvList.get(row)[0]; 
                 System.out.println(cell);
                 
             }
             
             
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
  
    public void writeCsv(){
        try {
           
            String csvFilePath = "c:/test.csv";
             CsvWriter wr =new CsvWriter(csvFilePath,',',Charset.forName("SJIS"));
             String[] contents = {"aaaaa","bbbbb","cccccc","ddddddddd"};  
             
             wr.writeRecord(contents);
             wr.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
    }
}