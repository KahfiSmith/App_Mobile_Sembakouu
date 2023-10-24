<?php
require("koneksi.php");

if ($_SERVER['REQUEST_METHOD']==='GET') {
$perintah = "SELECT * FROM barang WHERE jenis_barang = 'Perlengkapan Mandi'";
$eksekusi = mysqli_query($konek, $perintah);
$cek= mysqli_affected_rows($konek);

if($cek > 0) {
    $response["kode"] = 1;
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
}
echo json_encode($response);
mysqli_close($konek);
?>