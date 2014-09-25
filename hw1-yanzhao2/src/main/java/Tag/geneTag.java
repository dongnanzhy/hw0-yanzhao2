

/* First created by JCasGen Tue Sep 23 07:31:01 PDT 2014 */
package Tag;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Sep 23 07:31:01 PDT 2014
 * XML source: /home/dongnanzhy/11791/hw1-yanzhao2/src/main/resources/typeSystemDescriptor.xml
*/
public class geneTag extends Annotation {
  /** 
   *  
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(geneTag.class);
  /** 
   *  
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** 
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
*/
  protected geneTag() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public geneTag(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /**
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public geneTag(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** 
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public geneTag(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @return value of the feature 
   */
  public String getId() {
    if (geneTag_Type.featOkTst && ((geneTag_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "Tag.geneTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((geneTag_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (geneTag_Type.featOkTst && ((geneTag_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "Tag.geneTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((geneTag_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: content

  /** getter for content - gets 
   * @return value of the feature 
   */
  public String getContent() {
    if (geneTag_Type.featOkTst && ((geneTag_Type)jcasType).casFeat_content == null)
      jcasType.jcas.throwFeatMissing("content", "Tag.geneTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((geneTag_Type)jcasType).casFeatCode_content);}
    
  /** setter for content - sets  
   * @param v value to set into the feature 
   */
  public void setContent(String v) {
    if (geneTag_Type.featOkTst && ((geneTag_Type)jcasType).casFeat_content == null)
      jcasType.jcas.throwFeatMissing("content", "Tag.geneTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((geneTag_Type)jcasType).casFeatCode_content, v);}    
  }

    