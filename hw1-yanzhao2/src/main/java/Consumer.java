import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceProcessException;
import org.xml.sax.SAXException;

import Tag.geneTag;


public class Consumer extends CasConsumer_ImplBase {

  File out = null;
  File test = null;
  HashMap<String, Integer> table = new HashMap<String, Integer>();
  BufferedWriter bw = null;
  
  int hit = 0, miss = 0, answer = 0;

  @Override
  public void initialize() {

    try {
    //out = new File((String) getConfigParameterValue("OUTPUT_FILE"));
    out = new File("src/main/resources/data/hw1-yanzhao2.out");
    bw = new BufferedWriter(new FileWriter(out));
    } catch (Exception e) {
      e.printStackTrace();
    }
    test = new File("src/main/resources/data/sample.out");
    Scanner dict = null;
    try {
      dict = new Scanner(test);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    while (dict.hasNext()) {
      table.put(dict.nextLine(), 0);
      answer++;
    }
    
  }
  
  @Override
  public void processCas(CAS arg0) throws ResourceProcessException {
    // TODO Auto-generated method stub
    JCas jcas;
    try {
      jcas = arg0.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    // retrieve the filename of the input file from the CAS
    FSIterator<org.apache.uima.jcas.tcas.Annotation> it = jcas.getAnnotationIndex(geneTag.type).iterator();
    System.out.println("Consuming CAS");
    String geneId = null;
    String geneContent = null;
    int begin = -1;
    int end = -1;
    String output = null;
    while (it.hasNext()) {
      geneTag annotate = (geneTag) it.next();
      geneId = annotate.getId();
      geneContent = annotate.getContent();      
      begin = annotate.getBegin();
      end = annotate.getEnd();  
      
      output = geneId + "|" + begin + " " + end + "|" + geneContent;
      if (table.containsKey(output)) {
        hit++;
      } else {
        miss++;
      }

    // write to output file
      try {
        writeIntoFile(output);
      } catch (IOException e) {
        throw new ResourceProcessException(e);
      } catch (SAXException e) {
        throw new ResourceProcessException(e);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
    }
    double precision = hit * 1.0 / (hit + miss);
    double recall = hit * 1.0 / answer;
    System.out.println("Precision: " + precision + " " + "Recall: " + recall + " " 
                      + "F-Meause: " + 2 * precision * recall / (precision + recall));
  }
  
  public void writeIntoFile(String output) 
      throws Exception {
        bw.write(output);
        bw.newLine();
        bw.flush();
      }

      @Override
  public void destroy() {

      try {
        if (bw != null) {
          bw.close();
          bw = null;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

}
