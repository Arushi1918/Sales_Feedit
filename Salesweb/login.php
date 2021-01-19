<?php

$con=new mysqli("localhost","root","","Salesweb");
$st_check=$con->prepare("select * from users where mobile=? and password=?");
$st_check->bind_param("ss",$_GET["mobile"], $_GET["password"]);
$st_check->execute();
$rs=$st_check->get_result();
if($rs->num_rows==0)
    echo "0";
else 
{
    $act=bin2hex(openssl_random_pseudo_bytes(64));  
    $st=$con->prepare("update users set access_token=? where mobile=?");
    $st->bind_param("ss",$act,$_POST["mobile"]);
    $st->execute();
    echo $act;
}