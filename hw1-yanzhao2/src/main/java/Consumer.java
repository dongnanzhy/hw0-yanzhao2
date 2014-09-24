import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceProcessException;
import org.xml.sax.SAXException;

import Tag.geneTag;


public class Consumer extends CasConsumer_ImplBase {

  int docNum;
  File out = null;
  BufferedWriter bw = null;

  @Override
  public void initialize() {

    docNum = 0;
    try {
    //out = new File((String) getConfigParameterValue("OUTPUT_FILE"));
    out = new File("src/main/resources/data/hw1-yanzhao2.out");
    bw = new BufferedWriter(new FileWriter(out));
    } catch (Exception e) {
      e.printStackTrace();
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
    int start = -1;
    int end = -1;
    while (it.hasNext()) {
      geneTag annotate = (geneTag) it.next();
      geneId = annotate.getId();
      geneContent = annotate.getContent();      
      start = annotate.getBegin();
      end = annotate.getEnd();  

    // write to output file
    try {
      writeIntoFile(geneId, geneContent, start, end);
    } catch (IOException e) {
      throw new ResourceProcessException(e);
    } catch (SAXException e) {
      throw new ResourceProcessException(e);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    }
  }
  
  public void writeIntoFile(String geneId, String geneContent, int begin, int end) 
      throws Exception {
        bw.write(geneId + "|" + begin + " " + end + "|" + geneContent);
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
