<?php

mysql_connect("wm25.wedos.net","w30438_tr","RhBVMQcL") or die(mysql_error());
mysql_select_db("d30438_tr") or die(mysql_error());

switch($_GET["action"]){
 case "check":
  $res=mysql_query("SELECT * FROM projekty WHERE projekt='".$_GET["projekt"]."'");
  $arr=mysql_fetch_array($res);
  echo $arr["verze"];
  die();
 break;
 case "setnew":
  $res=mysql_query("UPDATE projekty SET verze='".$_GET["verze"]."' WHERE projekt='".$_GET["projekt"]."'");
  $res1=mysql_query("SELECT * FROM projekty WHERE projekt='".$_GET["projekt"]."'");
  $arr1=mysql_fetch_array($res1);
  echo $arr1["verze"];
  die();
 break;
 default:
  header("location:/");
  die();
  break;
} // switch

?>