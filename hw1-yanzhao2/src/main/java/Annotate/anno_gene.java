package Annotate;

import java.util.Map;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
//import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.ConfidenceChunker;
import com.aliasi.util.AbstractExternalizable;


import Tag.geneTag;
//import org.apache.uima.resource.ResourceProcessException;
//import edu.stanford.nlp.pipeline.Annotation;
import Tag.sentenceTag;
import Annotate.PosTagNamedEntityRecognizer;

public class anno_gene extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas arg0) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    JCas jcas = arg0;
    String sentenceID = null;
    String sentenceContent = null;
    PosTagNamedEntityRecognizer Recognizer = null;
    
    try {
      Recognizer = new PosTagNamedEntityRecognizer();
    } catch (ResourceInitializationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    FSIterator<org.apache.uima.jcas.tcas.Annotation> it = jcas.getAnnotationIndex(sentenceTag.type).iterator(); 
//    String genes = null;
    int begin;
    int end;
    String gene;
    
    while (it.hasNext()) {
      sentenceTag annotate_s = (sentenceTag) it.next();
      sentenceID = annotate_s.getId();
      sentenceContent = annotate_s.getContent(); 
      Map<Integer, Integer> BeginToEnd = Recognizer.getGeneSpans(sentenceContent);
      
      for (Map.Entry<Integer, Integer> entry : BeginToEnd.entrySet())
      {
          begin = entry.getKey();
          end = entry.getValue();
          gene = sentenceContent.substring(begin, end);
          begin = begin - countWhiteSpaces(sentenceContent.substring(0,begin)) ;
          end = begin + gene.length() - countWhiteSpaces(gene) - 1;
          

            geneTag annotate_g = new geneTag(jcas);
            annotate_g.setBegin(begin);
            annotate_g.setEnd(end);
            annotate_g.setId(sentenceID);
            annotate_g.setContent(gene);
            annotate_g.addToIndexes();

      }
    }
  }
  
  private int countWhiteSpaces(String phrase){
    int countBlank = 0;
    for(int i=0; i<phrase.length(); i++) {
      if(Character.isWhitespace(phrase.charAt(i))) {
          countBlank++;
      }
  }
    return countBlank;
  }

}
