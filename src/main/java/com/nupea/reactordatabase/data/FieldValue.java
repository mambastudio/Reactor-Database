package com.nupea.reactordatabase.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldValue<T> {
    private final Class<T> type;

    private T value;          // single value
    private List<T> values;   // multiple values
    private Set<T> options;   // allowed options (no duplicates)

    // --- private constructors (internal use only) ---
    private FieldValue(Class<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    private FieldValue(Class<T> type, Set<T> options, T value) {
        this.type = type;
        this.options = options;
        if (!options.contains(value)) {
            throw new IllegalArgumentException("Value not in options");
        }
        this.value = value;
    }

    private FieldValue(Class<T> type, Set<T> options, List<T> values) {
        this.type = type;
        this.options = options;
        this.values = values;
        if (!options.containsAll(values)) {
            throw new IllegalArgumentException("Some values not in options");
        }
    }

    // --- factories to avoid type inference issues ---
    @SuppressWarnings("unchecked")
    public static <X> FieldValue<X> of(Class<?> rawClass, Object value) {
        return new FieldValue<>((Class<X>) rawClass, (X) value);
    }

    @SuppressWarnings("unchecked")
    public static <X> FieldValue<X> of(Class<?> rawClass, Set<?> options, Object value) {
        return new FieldValue<>((Class<X>) rawClass, (Set<X>) options, (X) value);
    }

    @SuppressWarnings("unchecked")
    public static <X> FieldValue<X> ofMulti(Class<?> rawClass, Set<?> options, List<?> values) {
        return new FieldValue<>((Class<X>) rawClass, (Set<X>) options, (List<X>) values);
    }

    // --- Jackson integration ---
    @JsonCreator
    public static FieldValue<?> create(
            @JsonProperty("type") String typeName,
            @JsonProperty("value") Object value,
            @JsonProperty("values") List<?> values,
            @JsonProperty("options") Set<?> options) {

        try {
            Class<?> clazz = Class.forName(typeName);

            if (values != null) {
                return ofMulti(clazz, options, values);
            } else if (options != null) {
                return of(clazz, options, value);
            } else {
                return of(clazz, value);
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown type: " + typeName, e);
        }
    }

    // --- getters ---
    @JsonProperty("type")
    public String getTypeName() { return type.getName(); }

    public Class<T> getType() { return type; }

    public T getValue() { return value; }
    public void setValue(T value) { this.value = value; }

    public List<T> getValues() { return values; }
    public void setValues(List<T> values) { this.values = values; }

    public Set<T> getOptions() { return options; }
    public void setOptions(Set<T> options) { this.options = options; }

    public boolean isMultiSelect() { return values != null; }
    
    public List<String> asStringOptions() {
        return options == null ? null :
               options.stream().map(Object::toString).toList();
    }

    public String asStringValue() {
        return value == null ? null : value.toString();
    }
    
    public List<String> asStringValues() {
        return values == null ? null :
               values.stream().map(Object::toString).toList();
    }

    @Override
    public String toString() {
        return "FieldValue{" +
                "type=" + type.getSimpleName() +
                (value != null ? ", value=" + value : "") +
                (values != null ? ", values=" + values : "") +
                (options != null ? ", options=" + options : "") +
                '}';
    }
}
