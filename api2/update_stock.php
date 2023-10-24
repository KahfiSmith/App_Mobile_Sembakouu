<?php 

header('Content-Type: application/jsont/html; charset=utf-8');
include('koneksi.php');

if ($_SERVER['REQUEST_METHOD']==='POST'){
    
    $stock = $_POST['stock'];
    $kdBarang = $_POST['kdBarang'];
    $queryUpdate = "UPDATE  `barang` SET `Stok` = '$stock' WHERE `kdBarang` = '$kdBarang';";
    
    $result = mysqli_query($konek, $queryUpdate);
    $check = mysqli_affected_rows($konek);

    if ($check>0){
        $response['kode'] = 1;
        $response["message"] = "Update Stock Berhasil";


    }
    else{
        $response['kode'] = 0;
        $response["message"] = "Update Stock Gagal";
    }

    echo json_encode($response);
    mysqli_close($konek);
}
?>