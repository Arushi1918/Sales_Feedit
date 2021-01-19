<?php

$con=new mysqli("localhost","root","","Salesweb");
$st_check=$con->prepare("SELECT ID, NAME, PRICE, QTY, MOBILE FROM temp_order INNER JOIN ITEMS ON ITEMS.ID=temp_order.itemid WHERE MOBILE=?");
$st_check->bind_param("s",$_GET["mobile"]);
$st_check->execute();
$rs=$st_check->get_result();
$arr=array();
while($row=$rs->fetch_assoc())
{
    array_push($arr, $row);
}
echo json_encode($arr);
