<?php 

header('Content-Type: application/jsont/html; charset=utf-8');
include('koneksi.php');

if ($_SERVER['REQUEST_METHOD']==='POST'){
    $idTransaksi = $_POST['idTransaksi'];
    $idPembeli = $_POST['idPembeli'];

    $query = "UPDATE transaksi SET status = 'dibatalkan' WHERE idTransaksi = '$idTransaksi' AND idPembeli = '$idPembeli'";

    $result = mysqli_query($konek, $query);
    $check = mysqli_affected_rows($konek);

    if ($check>0){
        $response['kode'] = 1;
        $response["message"] = "Data Tersedia";
       
    }
    else{
        $response['kode'] = 0;
        $response["message"] = "Data Tidak Tersedia";
  
    }

    echo json_encode($response);
    mysqli_close($konek);
}
?>