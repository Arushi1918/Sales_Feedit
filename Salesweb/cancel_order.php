<?php

$con=new mysqli("localhost","root","","Salesweb");
$st_check=$con->prepare("delete from temp_order where mobile=?");
$st_check->bind_param("s",$_GET["mobile"]);
$st_check->execute();

