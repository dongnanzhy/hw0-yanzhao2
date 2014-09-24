package Annotate;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.uima.UimaContext;
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
  /**
   * the maximum length of potential gene words
   */
  private static final int MAX = 10;

  /**
   * the name of NER gene model
   */
  private File modelFile = null;

  /**
   * the handler to load the model and process given words
   */
  private ConfidenceChunker chunker = null;
  
  public void initialize(UimaContext context) {
    modelFile = new File("src/main/resources/ne-en-bio-genetag.HmmChunker");
    try {
      chunker = (ConfidenceChunker) AbstractExternalizable.readObject(modelFile);
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Override
  public void process(JCas arg0) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    /*   PosTagNamedEntityRecognizer Recognizer = null;
    
    try {
      Recognizer = new PosTagNamedEntityRecognizer();
    } catch (ResourceInitializationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    */
    /*
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
    */
    JCas jcas = arg0;
    
    try {
      chunker = (ConfidenceChunker) AbstractExternalizable.readObject(modelFile);
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    FSIterator<org.apache.uima.jcas.tcas.Annotation> it = jcas.getAnnotationIndex(sentenceTag.type).iterator(); 
    while (it.hasNext()) {
      sentenceTag annotate_s = (sentenceTag) it.next();
      String content = annotate_s.getContent();
      char[] temp = content.toCharArray();
      Iterator<Chunk> gene_iterator = chunker.nBestChunks(temp, 0, temp.length, MAX);
      while (gene_iterator.hasNext()) {
        Chunk chunk = gene_iterator.next();
        double conf = Math.pow(2.0, chunk.score());
        if (conf < 0.7) {
          break;
        }
        int begin = chunk.start();
        int end = chunk.end();
        String gene = content.substring(begin, end);
        begin = begin - countWhiteSpaces(content.substring(0,begin)) ;
        end = begin + gene.length() - countWhiteSpaces(gene) - 1;
        
        geneTag annotate_g = new geneTag(jcas);
        annotate_g.setBegin(begin);
        annotate_g.setEnd(end);
        annotate_g.setId(annotate_s.getId());
        annotate_g.setContent(gene);
        annotate_g.addToIndexes(jcas);
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
