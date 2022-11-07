# Overview
This is a gson demo.

# Points
## Inner class 
Gson don't support inner class, but static inner class is supported.
Another solution is to write a custom instance creator for B, but this
is not recommended.
## Array
gson.fromJson("[1,2,3,4,5]", int[].class)
## Collection
```
// Deserialization
TypeToken<Collection<Integer>> collectionType = new TypeToken<Collection<Integer>>(){};
// Note: For older Gson versions it is necessary to use `collectionType.getType()` as argument below,
// this is however not type-safe and care must be taken to specify the correct type for the local variable
Collection<Integer> ints2 = gson.fromJson(json, collectionType);
```
### Collections Limitations
Gson can serialize collection of arbitrary objects but can not deserialize from it, because there is no way for the user to indicate the type of the resulting object.
## Maps
```aidl
TypeToken<Map<String, String>> mapType = new TypeToken<Map<String, String>>(){};
Map<String, String> stringMap = gson.fromJson(json, mapType);
```
### complex types as Map keys
```aidl
class PersonName {
  String firstName;
  String lastName;

  PersonName(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  // ... equals and hashCode
}

Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
Map<PersonName, Integer> complexMap = new LinkedHashMap<>();
complexMap.put(new PersonName("John", "Doe"), 30);
complexMap.put(new PersonName("Jane", "Doe"), 35);

// Serialization; complex map is serialized as a JSON array containing key-value pairs (as JSON arrays)
String json = gson.toJson(complexMap);
// ==> json is [[{"firstName":"John","lastName":"Doe"},30],[{"firstName":"Jane","lastName":"Doe"},35]]

Map<String, String> stringMap = new LinkedHashMap<>();
stringMap.put("key", "value");
// Serialization; non-complex map is serialized as a regular JSON object
String json = gson.toJson(stringMap); // json is {"key":"value"}
```
## Generic Types
```aidl
Type fooType = new TypeToken<Foo<Bar>>() {}.getType();
gson.toJson(foo, fooType);

gson.fromJson(json, fooType);
```
## Collection with Objects of Arbitrary Types
Ignore it, for the time being.

## Custom Serialization and Deserialization
Ignore it, for the time being.

## Writing an Instance Creator
```aidl
public class Id<T> {
  private final Class<T> classOfId;
  private final long value;
  public Id(Class<T> classOfId, long value) {
    this.classOfId = classOfId;
    this.value = value;
  }
}

class IdInstanceCreator implements InstanceCreator<Id<?>> {
  public Id<?> createInstance(Type type) {
    Type[] typeParameters = ((ParameterizedType)type).getActualTypeArguments();
    Type idType = typeParameters[0]; // Id has only one parameterized type T
    return new Id((Class)idType, 0L);
  }
}
```
## Compact Vs. Pretty Printing for JSON Output Format
The following is an example shows how to configure a Gson instance to use the default JsonPrintFormatter instead of the JsonCompactFormatter
```aidl
Gson gson = new GsonBuilder().setPrettyPrinting().create();
String jsonOutput = gson.toJson(someObject);
```
## Null Object Support
```aidl
Gson gson = new GsonBuilder().serializeNulls().create();
// {"s":null,"i":5}
```
## Versioning Support
```aidl
public class VersionedClass {
  @Since(1.1) private final String newerField;
  @Since(1.0) private final String newField;
  private final String field;

  public VersionedClass() {
    this.newerField = "newer";
    this.newField = "new";
    this.field = "old";
  }
}

VersionedClass versionedObject = new VersionedClass();
Gson gson = new GsonBuilder().setVersion(1.0).create();
String jsonOutput = gson.toJson(versionedObject);
System.out.println(jsonOutput);
System.out.println();

gson = new Gson();
jsonOutput = gson.toJson(versionedObject);
System.out.println(jsonOutput);
```
```aidl
{"newField":"new","field":"old"}

{"newerField":"newer","newField":"new","field":"old"}
```
## Excluding Fields From Serialization and Deserialization
Ignored for the time being.

## JSON Field Naming Support
1. @SerializedName
2. setFieldNamingPolicy
```aidl
private class SomeObject {
  @SerializedName("custom_naming") private final String someField;
  private final String someOtherField;

  public SomeObject(String a, String b) {
    this.someField = a;
    this.someOtherField = b;
  }
}

SomeObject someObject = new SomeObject("first", "second");
Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
String jsonRepresentation = gson.toJson(someObject);
System.out.println(jsonRepresentation);
```
## Streaming
Ignored for the time being.
`JsonReader and JsonWriter`