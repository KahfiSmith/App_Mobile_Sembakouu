<?php

header('Content-Type: application/jsont/html; charset=utf-8');
include('koneksi.php');

if ($_SERVER['REQUEST_METHOD']==='POST'){
    $NamaBarang = $_POST["NamaBarang"];

    $query = "SELECT * FROM barang WHERE Stok AND NamaBarang LIKE '%$NamaBarang%'";
    $eksekusi = mysqli_query($konek, $query);
    $check = mysqli_affected_rows($konek);

    if ($check>0){
        $response['kode'] = 1;
        $response["message"] = "Data Tersedia";
        $response["data"] = array();
        while($ambil= mysqli_fetch_object($eksekusi)) {
            $F["kdBarang"] = $ambil->kdBarang;
            $F["NamaBarang"] = $ambil->NamaBarang;
            $F["jenis_barang"] = $ambil->jenis_barang;
            $F["deskripsi"] = $ambil->deskripsi;   
            $F["gambar"] = $ambil->gambar;
            $F["HargaBeli"] = $ambil->HargaBeli;
            $F["HargaJual"] = $ambil->HargaJual;
            $F["Stok"] = $ambil->Stok;
            $F["Satuan"] = $ambil->Satuan;
            array_push($response["data"], $F);
            }
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