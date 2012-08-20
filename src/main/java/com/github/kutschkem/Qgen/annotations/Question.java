

/* First created by JCasGen Thu May 17 14:45:17 CEST 2012 */
package com.github.kutschkem.Qgen.annotations;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Aug 18 13:41:23 CEST 2012
 * XML source: E:/workspace/QGen/src/main/resources/desc/type/QGentypes.xml
 * @generated */
public class Question extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Question.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Question() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Question(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Question(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Question(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets 
   * @generated */
  public String getText() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "com.github.kutschkem.Qgen.annotations.Question");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Question_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets  
   * @generated */
  public void setText(String v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "com.github.kutschkem.Qgen.annotations.Question");
    jcasType.ll_cas.ll_setStringValue(addr, ((Question_Type)jcasType).casFeatCode_text, v);}    
   
    
  //*--------------*
  //* Feature: answer

  /** getter for answer - gets 
   * @generated */
  public String getAnswer() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_answer == null)
      jcasType.jcas.throwFeatMissing("answer", "com.github.kutschkem.Qgen.annotations.Question");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Question_Type)jcasType).casFeatCode_answer);}
    
  /** setter for answer - sets  
   * @generated */
  public void setAnswer(String v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_answer == null)
      jcasType.jcas.throwFeatMissing("answer", "com.github.kutschkem.Qgen.annotations.Question");
    jcasType.ll_cas.ll_setStringValue(addr, ((Question_Type)jcasType).casFeatCode_answer, v);}    
  }

    