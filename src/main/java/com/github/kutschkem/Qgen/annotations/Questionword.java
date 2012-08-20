

/* First created by JCasGen Thu May 17 14:08:52 CEST 2012 */
package com.github.kutschkem.Qgen.annotations;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Aug 18 13:41:23 CEST 2012
 * XML source: E:/workspace/QGen/src/main/resources/desc/type/QGentypes.xml
 * @generated */
public class Questionword extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Questionword.class);
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
  protected Questionword() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Questionword(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Questionword(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Questionword(JCas jcas, int begin, int end) {
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
  //* Feature: questionword

  /** getter for questionword - gets 
   * @generated */
  public String getQuestionword() {
    if (Questionword_Type.featOkTst && ((Questionword_Type)jcasType).casFeat_questionword == null)
      jcasType.jcas.throwFeatMissing("questionword", "com.github.kutschkem.Qgen.annotations.Questionword");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Questionword_Type)jcasType).casFeatCode_questionword);}
    
  /** setter for questionword - sets  
   * @generated */
  public void setQuestionword(String v) {
    if (Questionword_Type.featOkTst && ((Questionword_Type)jcasType).casFeat_questionword == null)
      jcasType.jcas.throwFeatMissing("questionword", "com.github.kutschkem.Qgen.annotations.Questionword");
    jcasType.ll_cas.ll_setStringValue(addr, ((Questionword_Type)jcasType).casFeatCode_questionword, v);}    
  }

    