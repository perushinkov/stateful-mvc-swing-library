package perushinkov.swinglib.model;

import java.util.List;

/**
 * A type of value that can only take a value from a predefined finite set.
 * It's sort of like an enum but list of values can be created dynamically.
 * @author eglavchev
 *
 * @param <T>
 */
public class EnumValue<T> {
  private int valueIndex;
  private List<T> values;

  /**
   * Creates an enumerable value and sets the current value by its index
   * @param currentValue
   * @param values
   */
  public EnumValue(int currentValue, List<T> values) {
    this.valueIndex = currentValue;
    this.values = values;
  }

  /**
   * Creates an enumerable value and sets the current value to be the first value
   * @param values
   */
  public EnumValue(List<T> values) {
    this(0, values);
  }

  /**
   * Returns the current value of the enumerable value
   * @return
   */
  public T getValue() {
    if (valueIndex < 0 || valueIndex >= values.size()) {
      return null;
    }
    return values.get(valueIndex);
  }

  /**
   * Returns a list of all the different values this enumerable can hold.
   * @return
   */
  public List<T> getValues() {
    return values;
  }

  /**
   * Sets the enumerable's value to its i-th possible value.
   * @param index
   */
  public void setValue(int index) {
    valueIndex = index;
  }

  @Override
  public String toString() {
    T value = getValue();
    if (value == null) {
      return "";
    }
    return value.toString();
  }

  /**
   * Gets the index of the current value in the list of possible values
   * @return
   */
  public int getIndex() {
    return valueIndex;
  }
  
  /**
   * Applied indexOf(value) to the underlying collection of possible values
   * @param value
   * @return
   */
  public int indexOf(String value) {
    return values.indexOf(value);
  }

  /**
   * Sets the value and updates the internal enumValue index
   * @param paramValue
   */
  public void setValue(String paramValue) {
    valueIndex = values.indexOf(paramValue);
  }
}
