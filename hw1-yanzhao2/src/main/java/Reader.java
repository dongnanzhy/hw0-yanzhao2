// Licensed to the Apache Software Foundation (ASF) under one

//package org.apache.uima.examples.cpe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import java.io.File;
import java.io.IOException;
 
//import org.apache.commons.io.FileUtils;

/**
 * A simple collection reader that reads documents from a directory in the filesystem. It can be
 * configured with the following parameters:
 * <ul>
 * <li><code>InputDirectory</code> - path to directory containing files</li>
 * <li><code>Encoding</code> (optional) - character encoding of the input files</li>
 * <li><code>Language</code> (optional) - language of the input documents</li>
 * </ul>
 * 
 * 
 */
public class Reader extends CollectionReader_ImplBase {
  File file;
  public void initialize() throws ResourceInitializationException {
    file = new File((String) getConfigParameterValue("input"));
  }
  
  /**
   * @see org.apache.uima.collection.CollectionReader#hasNext()
   */
  boolean test = true;
  public boolean hasNext() {
//    System.out.println("1111");
    if (test) {
      test = false;
      return true;
    } else {
      return false;
    }
  }

  /**
   * @see org.apache.uima.collection.CollectionReader#getNext(org.apache.uima.cas.CAS)
   */
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jcas;
    try {
    jcas = aCAS.getJCas();
    } catch (CASException e) {
    throw new CollectionException(e);
    }
    
//    File file = new File((String) getConfigParameterValue("input"));
    String content = null;
    try {
        content = FileUtils.file2String(file);
    } catch (IOException e) {
        e.printStackTrace();
    }
    jcas.setDocumentText(content);
  }

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Progress[] getProgress() {
    // TODO Auto-generated method stub
    return null;
  }

}

