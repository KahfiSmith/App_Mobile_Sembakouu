<?php
require("koneksi.php");
header("Content-Type: application/jsont/html; charset: utf-8;");

if ($_SERVER['REQUEST_METHOD']==='POST'){
    $Username = $_POST['Username'];
    $Password = $_POST['Password'];
    $perintah =  "SELECT * FROM pembeli WHERE Username = '$Username' AND Password = '$Password'";
    $eksekusi = mysqli_query($konek, $perintah);
    $cek = mysqli_affected_rows($konek);
    
    if($cek > 0) {
        $response["kode"] = 1;
        $response["message"] = "Data Tersedia";
        $response["data"] = array();
    
        while($ambil= mysqli_fetch_object($eksekusi)) {
            $F["idPembeli"] = $ambil->idPembeli;
            $F["NamaPembeli"] = $ambil->NamaPembeli;
            $F["Alamat"] = $ambil->Alamat;
            $F["no_hp"] = $ambil->no_hp;
            $F["Username"] = $ambil->Username;
            $F["Password"] = $ambil->Password;
            
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
