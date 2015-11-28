package perushinkov.swinglib.model;

/**
 * Holds information about Parameter type, where parameter is either a value of some class,
 * or an enumerable that can take a certain number of specific values.
 * @author eglavchev
 *
 */
public class ParamType {
  private Class<?> typeClass;
  private boolean isEnumerable;
  private EnumValue<String> enumValue;

  /**
   * Constructs a class-type param type.
   */
  public ParamType(Class<?> typeClass) {
    this.typeClass = typeClass;
    isEnumerable = false;
    enumValue = null;
  }

  /**
   * Constructs an enumerable type from enumValue. 
   * (EnumValue can serve both as a type, defining which values it can take,
   * and as a value).
   * @param enumValue
   */
  public ParamType(EnumValue<String> enumValue) {
    typeClass = null;
    isEnumerable = true;
    this.enumValue = enumValue;
  }

  /**
   * @return null if enumerable, else the class-type.
   */
  public Class<?> getClassType() {
    return typeClass;
  }

  /**
   * @return if type is enumerable. If it is not, then it is class-type.
   */
  public boolean isEnumerable() {
    return isEnumerable;
  }

  /**
   * @return null if class-type, else the enumValue
   */
  public EnumValue<String> getEnumerable() {
    return enumValue;
  }
}
