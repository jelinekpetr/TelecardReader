#!/bin/bash

cd src/petr/telecardreader
sed -e 's/\(public static String VERSION =\)\(.*\)$/\1 \"'"$1"'\";/g' < CurrentVersion.java > CurrentVersion.java.new
mv CurrentVersion.java CurrentVersion.java.old
mv CurrentVersion.java.new CurrentVersion.java
 