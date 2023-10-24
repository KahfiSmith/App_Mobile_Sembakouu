<?php

header('Content-Type: application/jsont/html; charset=utf-8');
include("koneksi.php");

if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $query = "SELECT * FROM pembeli";
    $execute = mysqli_query($konek, $query);
    $check = mysqli_affected_rows($konek);

    if ($check > 0) {
        $response['kode'] = 1;
        $response['message'] = 'Data Ada';
        $response['data'] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
            $f['idPembeli'] = $ambil->idPembeli;
            $f['NamaPembeli'] = $ambil->NamaPembeli;
            $f['Alamat'] = $ambil->Alamat;
            $f['no_hp'] = $ambil->no_hp;
            $f['Username'] = $ambil->Username;
            $f['Password'] = $ambil->Password;

            array_push($response['data'], $f);
        }
    } else {
        $response['kode'] = 0;
        $response['message'] = 'Data Tidak Ada';
    }
    echo json_encode($response);
    mysqli_close($konek);
}
else if($_SERVER['REQUEST_METHOD']==='POST'){
    $NamaPembeli = $_POST['NamaPembeli'];
    $Alamat = $_POST['Alamat'];
    $no_hp = $_POST['no_hp'];
    $Username = $_POST['Username'];
    $Password = $_POST['Password'];

    $query = "INSERT INTO pembeli(NamaPembeli, Alamat, no_hp, Username, Password)VALUES('$NamaPembeli', '$Alamat', '$no_hp', '$Username', '$Password')";
     $response = mysqli_query($konek, $query);
     $check = mysqli_affected_rows($konek);

     if($check>0){
         $data['kode'] = 1;
         $data['message'] = 'Simpan Data Berhasil';
     }else{
         $data['kode'] = 0;
         $data['message'] = 'Gagal Menyimpan Data';
     }
     echo json_encode($data);
     mysqli_close($konek);
}

?>