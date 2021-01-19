<?php

$con=new mysqli("localhost","root","","Salesweb");
$st=$con->prepare("SELECT price,qty FROM items inner join bill_det on items.id=bill_det.itemid where bill_det.bill_no=?");
$st->bind_param("i",$_GET["bill_no"]);
$st->execute();
$rs=$st->get_result();
$total=0;

while($row=$rs->fetch_assoc())
{
   $total=(($total + ($row["price"]*$row["qty"]))/73.28);
}

$st2=$con->prepare("update bill set total=? where bill_no=?");
$st2->bind_param("di",$total,$_GET["bill_no"]);
$st2->execute();

echo $total;

