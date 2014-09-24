package Annotate;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import Tag.sentenceTag;

public class anno_sentence extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas arg0) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub

    String text = arg0.getDocumentText();
    String[] sentence = text.split("\n");
    for (int i = 0; i< sentence.length; i++){
      int partition = sentence[i].indexOf(' ');
      //System.out.println(text[i]);
      String sentenceID = sentence[i].substring(0, partition);
      String sentenceContent = sentence[i].substring(partition).trim();
      
      sentenceTag annot = new sentenceTag(arg0);
      annot.setId(sentenceID);
      annot.setContent(sentenceContent);
      annot.addToIndexes();
    }
  }

}
