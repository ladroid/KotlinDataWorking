# KotlinDataWorking
Data Working using Krangl and MapDB

Hello everybody. Decided to work with data.

For this I used [Krangl](https://github.com/holgerbrandl/krangl/) and [MapDB](http://www.mapdb.org/blog/mmap_files_alloc_and_jvm_crash/)

## Krangl

1) Making database and table

```kotlin
val user: DataFrame = dataFrameOf("id", "name", "job", "salary")(
            1, "Jhon", "Programmer", 2500,
            2, "Anna", "Manager", 2000,
            3, "Steve", "CEO", 4000)
```

This is same with SQL

```sql
CREATE TABLE table_name (
    column1 datatype,
    column2 datatype,
    column3 datatype,
   ....
);
```

### For printing data use this

```
user.print()
```

### For sorting

```kotlin
//sort by
user.sortedBy("salary").print()

user.sortedByDescending("salary").print()
```

In SQL it will look like this

```sql
SELECT column1, column2, ...
FROM table_name
ORDER BY column1, column2, ... ASC|DESC;
```

### Select

```kotlin
//select somth
user.select("name").print()
```

In SQL

```sql
SELECT column1, column2, ...
FROM table_name;
```

### Min and Max

```kotlin
//min and max salary
user.summarize("min_salary") { it["salary"].min(true) }.print()
user.summarize("max_salary") { it["salary"].max(true) }.print()
```

In SQL

```sql
SELECT MIN(column_name)
FROM table_name
WHERE condition;
```

```sql
SELECT MAX(column_name)
FROM table_name
WHERE condition;
```

## MapDB

MapDB provides Maps, Sets, Lists, Queues, Bitmaps with range queries, expiration, compression, off-heap storage and streaming.

So how to work with it?

### Adding library in project

Add this in **build.gradle**

```
compile group: 'org.mapdb', name: 'mapdb', version: '3.0.7'
```

### Starting to work with this library

**Example1**

```kotlin
db = DBMaker.memoryDB().make();
map = db.hashMap("map").createOrOpen();
map.put("something", "here");
```

**Example2**

```kotlin
db = DBMaker
        .fileDB("file.db")
        .fileMmapEnable()
        .make();
map = db.hashMap<String, String>("map", Serializer.STRING, Serializer.LONG).createOrOpen();
map.put("something", 111L);

db.close();
```

### To see what is it inside your file

```kotlin
val db = DBMaker.fileDB("example.db").closeOnJvmShutdown().readOnly().make()
println("${db.hashMap("example").open().get("name")} ${db.hashMap("example").open().get("age")}")
```

And that's all. Thanks for attention. Maybe sometimes I will improve it
