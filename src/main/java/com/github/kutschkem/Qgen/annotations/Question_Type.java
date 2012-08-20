
/* First created by JCasGen Thu May 17 14:45:17 CEST 2012 */
package com.github.kutschkem.Qgen.annotations;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Sat Aug 18 13:41:23 CEST 2012
 * @generated */
public class Question_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Question_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Question_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Question(addr, Question_Type.this);
  			   Question_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Question(addr, Question_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Question.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.github.kutschkem.Qgen.annotations.Question");
 
  /** @generated */
  final Feature casFeat_text;
  /** @generated */
  final int     casFeatCode_text;
  /** @generated */ 
  public String getText(int addr) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "com.github.kutschkem.Qgen.annotations.Question");
    return ll_cas.ll_getStringValue(addr, casFeatCode_text);
  }
  /** @generated */    
  public void setText(int addr, String v) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "com.github.kutschkem.Qgen.annotations.Question");
    ll_cas.ll_setStringValue(addr, casFeatCode_text, v);}
    
  
 
  /** @generated */
  final Feature casFeat_answer;
  /** @generated */
  final int     casFeatCode_answer;
  /** @generated */ 
  public String getAnswer(int addr) {
        if (featOkTst && casFeat_answer == null)
      jcas.throwFeatMissing("answer", "com.github.kutschkem.Qgen.annotations.Question");
    return ll_cas.ll_getStringValue(addr, casFeatCode_answer);
  }
  /** @generated */    
  public void setAnswer(int addr, String v) {
        if (featOkTst && casFeat_answer == null)
      jcas.throwFeatMissing("answer", "com.github.kutschkem.Qgen.annotations.Question");
    ll_cas.ll_setStringValue(addr, casFeatCode_answer, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Question_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_text = jcas.getRequiredFeatureDE(casType, "text", "uima.cas.String", featOkTst);
    casFeatCode_text  = (null == casFeat_text) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_text).getCode();

 
    casFeat_answer = jcas.getRequiredFeatureDE(casType, "answer", "uima.cas.String", featOkTst);
    casFeatCode_answer  = (null == casFeat_answer) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_answer).getCode();

  }
}



    