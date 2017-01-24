# Date Difference

### How to build
In project directory: 
* MAC
```sh
$ ./gradlew clean build
```
* Windows
```sh
$ gradlew.bat clean build
```
Artifact is built into build/libs folder

### Input

* Pairs of dates in the following
  format
```
DD MM YYYY, DD MM YYYY
```

### How to run
```
java -jar <path-to>/date-diff.jar
```

### Assumptions
- Limit input range of dates from 1900 to 2010
- End date is exclusive on calculation of date difference
- If first date is later than the second, difference is displayed in negative

### Test data
```
2 1 1900, 30 12 2010
2 1 1900, 30 12 2010, 40539
```
```
08 01 2000, 07 03 2000
08 01 2000, 07 03 2000, 59
```
```
1 2 2000
More or less than 2 dates input
```
```
1 2 2000, 1 3 3 2000
More or less than 3 arguments to form a date
```
```
29 2 1999, 1 3 1999
Day 29 is out of range [1 - 28]
```
```
1 3 1999, 28 2 1999
1 3 1999, 28 2 1999, -1
```
```
abc 1 2000, 1 2 2000
Cannot convert to date
```