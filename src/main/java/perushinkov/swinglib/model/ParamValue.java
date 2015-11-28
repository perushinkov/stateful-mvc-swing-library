package perushinkov.swinglib.model;

import java.sql.Date;

/**
 * Builds upon the abstraction of the ParamType.
 * REpresents a value of type ParamType.
 * @author eglavchev
 */
public class ParamValue {
  private ParamType type;
  private Object classValue;
  
  /**
   * Creates a ParamValue of the given value type.
   * Since ParamValue serves both as value and type,
   * the ParamValue serves just as a wrapper here.
   */
  public ParamValue(EnumValue<String> value) {
    this.type = new ParamType(value);
    this.classValue = null;
  }
  
  /**
   * Creates a ParamValue based on a type.
   */
  public ParamValue(ParamType value) {
    this.type = value;
    this.classValue = null;
  }

  /**
   * Creates a ParamValue with the given Object value 
   * and class-type ParamType wrapper around the given type.
   */  
  public ParamValue(Object value, Class<?> classType) {
    this.type = new ParamType(classType);
    this.classValue = value;
  }
  
  /**
   * Creates String.class type ParamValue with the given value.
   */
  public ParamValue(String value) {
    this.type = new ParamType(String.class);
    this.classValue = value;
  }
  
  /**
   * Returns the underlying ParamType
   */
  public ParamType getType() {
    return type;
  }
  
  /**
   * Returns the actual value which is dependent on the ParamType.
   * If it is enumerable an EnumValue is returned, else
   * the classValue is returned.
   */
  public Object getValue() {    
    if (type.isEnumerable()) {
      return type.getEnumerable();
    }  else {
      return classValue;
    } 
  }
  
  /**
   * Guarantees a string representation safe for use in MVCModel.
   */
  @Override
  public String toString() {
    Object val = getValue();
    String returnValue;
    
    if (val == null) {
      returnValue = "";
    } else if (val instanceof Date) {
      returnValue = RootModel.getApplicationDateFormat().format((Date)val);
    } else {
      returnValue = val.toString();
    }
    
    return returnValue;
  }

  /**
   * Value will be set for both enumType and classType
   */
  public void setValue(Object paramValue) {
    if (getType().isEnumerable()) {
      getType().getEnumerable().setValue(paramValue.toString());
    } else {
      classValue = paramValue;
    }
  }
}
