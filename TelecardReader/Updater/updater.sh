#!/bin/bash

#http://telecardreader.petrjelinek.net/updater.php?action=check&projekt=TelecardReader

FTP_USER=w30438_tr3:jp3na2gr
FILE=$1
NEW_VERSION=`cat build/version`
CURRENT_VERSION=`curl --silent -G -d action=check -d projekt=TelecardReader http://telecardreader.petrjelinek.net/updater.php`

echo
echo "#################################################################"
echo `date`": Uploading new program version"
echo "$FILE: $CURRENT_VERSION -> $NEW_VERSION"
echo

if [ "$NEW_VERSION" \> "$CURRENT_VERSION" ]; then
  echo "Uploading new version:"
  echo "Creating directory $NEW_VERSION..."
  curl --silent ftp://telecardreader.petrjelinek.net/ -Q "MKD $NEW_VERSION" --user -$FTP_USER
  echo "Uploading file $FILE..."
  curl --create-dirs -T build/$FILE ftp://telecardreader.petrjelinek.net/$NEW_VERSION/ --user $FTP_USER
  echo "Updating database..."
  UPDATED=`curl --silent  -G -d action=setnew -d verze=$NEW_VERSION -d projekt=TelecardReader http://telecardreader.petrjelinek.net/updater.php`
  if [ "$UPDATED" = "$NEW_VERSION" ]; then
    echo "Database update OK."
  else
    echo "Database update failed - version after update: $UPDATED"
  fi
  echo "Backing up local file... "
  mkdir "versions/$NEW_VERSION"
  mv build/$FILE versions/$NEW_VERSION/
  echo -n "OK"
  echo "" > build/version
  
  echo
  
  exit 0;
else
  echo "No new version available. Stop."
  echo
  exit 0
fi

