<?php

header('Content-Type: application/jsont/html; charset=utf-8');
include('koneksi.php');

if ($_SERVER['REQUEST_METHOD']==='POST'){
    $idPembeli = $_POST["idPembeli"];
    $NamaPembeli = $_POST["NamaPembeli"];
    $Alamat = $_POST["Alamat"];
    $no_hp = $_POST["no_hp"];
    $Username = $_POST["Username"];

    $query = "UPDATE pembeli SET NamaPembeli = '$NamaPembeli', Alamat = '$Alamat', no_hp = '$no_hp',  Username = '$Username' WHERE idPembeli = '$idPembeli'";
    $eksekusi = mysqli_query($konek, $query);
    $check = mysqli_affected_rows($konek);

    if ($check>0){
        $response['kode'] = 1;
        $response["message"] = "Data Tersedia";
        $response["data"] = array();
        
        } 
        else {
            $response["kode"] = 0;
            $response["message"] = "Data Tidak Tersedia";
            $response["data"] = null;
    }

    echo json_encode($response);
    mysqli_close($konek);
}
?>