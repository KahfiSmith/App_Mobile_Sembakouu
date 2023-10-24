<?php
	session_start();
	$timeout = 1; // setting timeout dalam menit
	$logout = "login.php"; // redirect halaman logout

	$timeout = $timeout * 360; // menit ke detik
	if(isset($_SESSION['start_session'])){
		$elapsed_time = time()-$_SESSION['start_session'];
		if($elapsed_time >= $timeout){
			session_destroy();
			echo "<script type='text/javascript'>alert('Sesi telah berakhir');window.location='$logout'</script>";
		}
	}

	$_SESSION['start_session']=time();

	include('config.php');
	if($_SESSION['status_login'] != true){
		echo '<script>window.location="login.php"</script>';
	}
	

?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width-device-width, initial-scale=1">
    <title>Transaksi || Sembakouu</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css2?family=Quicksand&display=swap" rel="stylesheet">
	<script src="dist/sweetalert2.all.min.js"></script>
</head>
<body>
	
    <!-- header -->
	<header>
		<div class="container">
		<h1><a href="home.php">Sembakouu</a></h1>
			<ul>
				<li><a href="home.php">Dashboard</a></li>
				<li><a href="admin.php">Profile</a></li>
				<li><a href="listbarang.php">Barang</a></li>
				<li><a href="listsupplier.php">Supplier</a></li>
				<li><a href="transaksi.php">Transaksi</a></li>
				<li><a href="laporan.php">Laporan</a></li>
				<li><a href="logout.php" id="logout">Logout</a></li>
			</ul>
		</div>
	</header>
	
	<!-- Content -->
	<h2 style="padding: 10px 15px 10px 15px; margin-left: 20%;">Transaksi</h2>
	<?php
	$sql = "SELECT * FROM penjualan ORDER BY kdPenjualan DESC LIMIT 1";
	
    $query = mysqli_query($conn, $sql);

    $data = mysqli_fetch_array($query);

	$sql = "SELECT * FROM penjual ";
    $query = mysqli_query($conn, $sql);

    $data = mysqli_fetch_array($query);
    $idAdmin = $data['idAdmin'];

	$sql = "SELECT MAX(kdPenjualan) FROM penjualan";
	$query = mysqli_query($conn, $sql);
	$data = mysqli_fetch_array($query);
	$MaxID = $data[0];
	$id = (int) substr($MaxID,1,4);
	$MaxID++;
	$NewID = "".sprintf("%04s",$MaxID++);
	?>
	<div class="section">
		<div class="container">
				<label for="kdPenjualan" class="input-left">Kode Transaksi :</label>
				<input type="text" value="<?= $NewID ?>" name="kdPenjualan" id="kdPenjualan" class="input-left" disabled>

				<label for="NamaAdmin" class="input-left">Nama Admin : </label>
				<input type="text" name="NamaAdmin" id="NamaAdmin" value="<?php echo $_SESSION['user_global']->NamaAdmin; ?>" class="input-left" disabled>

				<label for="Waktu" class="input-left">Tanggal : </label>
				<input type="text" name="Waktu" id="Waktu" value="<?php echo date('d F Y') ?>" class="input-left" disabled>
				

				<p>
					<label for="NamaBarang" class="input-left">Pilih Produk : </label>
					<select name="NamaBarang" id="NamaBarang" class="input-left">
						<option class="input-left" value="0">-- Pilih Produk --</option>
						<?php
					
						$no = 1;
						$qry = mysqli_query($conn, "SELECT * FROM barang ORDER BY NamaBarang ASC ");
						while ($data=mysqli_fetch_array($qry)) {
							$Stok = $data['Stok'];
						?>
						<option class="input-left" data="<?= $data['NamaBarang'] ?>" value="<?= $data['kdBarang'] ?>"><?= $data['NamaBarang'] ?>
						</option>
						<?php }
					?>
					</select>
				</p>
				<p>
					<label for="Stok" class="input-left">Stok Barang : </label>
					<input type="number" name="Stok" id="Stok" disabled class="input-left">
				</p>
				<p>
					
					<label for="Jumlah" class="input-left">Jumlah : </label>
					<input type="number" name="Jumlah" id="Jumlah" min="0" class="input-left">
				</p>
				<p>
					<label for="HargaJual" class="input-left">Harga : </label>
					<input type="number" name="HargaJual" id="HargaJual" disabled class="input-left">
					<label for="TotalHarga" class="input-left">Total : </label>
					<input type="number" name="TotalHarga" id="TotalHarga" class="input-left" disabled>
				</p>
				<p>
					<label for="bayar" class="input-left">Total Bayar : </label>
					<input type="number" name="TotalBayar" id="totalbayar" class="input-left" disabled>			
				</p>
				<p>
					<label for="bayar" class="input-left">Bayar : </label>
					<input type="number" name="bayar" id="bayar" class="input-left">
					<button id="bayar_transaksi" class="input-right">Bayar</button>
					<button id="batal_transaksi" class="input-right">Batal</button>
				</p>
				<p>
					<button id="proses" name="proses" class="input-right">Tambah</button>
				</p>
				
				
				<table border="1" align="center" width="100%">
					<thead>
						<tr>
							<td align="center">No</td>
							<td align="center">Nama Barang</td>
							<td align="center">Harga</td>
							<td align="center">Jumlah</td>
							<td align="center">Total</td>
						</tr>
					</thead>
					<tbody id="list_pesan">

					</tbody>
				</table>
				<script src="jquery.js"></script>
				<script>
					$(function () {
						$('#NamaBarang').on('click', function () {
							let kdBarang = $('#NamaBarang option:selected').attr('value');
							let NamaBarang = $('#NamaBarang option:selected').attr('data');
							$.ajax({
								url: 'http://sofiyatul.kencang.id//getbarang.php',
								data: {
									kdBarang: kdBarang,
									NamaBarang: NamaBarang
								},
								type: 'json',
								method: 'post',
								success: function (data) {
									$('#HargaJual').val(data);
								}
							})
						})
						$(function () {
						$('#NamaBarang').on('click', function () {
							let kdBarang = $('#NamaBarang option:selected').attr('value');
							let NamaBarang = $('#NamaBarang option:selected').attr('data');
							let Jumlah = $('#Jumlah').val();
							$.ajax({
								url: 'http://sofiyatul.kencang.id/getstok.php',
								data: {
									kdBarang: kdBarang,
									NamaBarang: NamaBarang
								},
								type: 'json',
								method: 'post',
								success: function (data) {
									$('#Stok').val(data);
									if (Jumlah > "0") {
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: 'Pilih Barang Terlebih dahulu!',
								})
							}
								}
							})
						})

						$('#Jumlah').on('change', function () {
							let HargaJual = $('#HargaJual').val();
							let Jumlah = $('#Jumlah').val();
							let TotalHarga = HargaJual * Jumlah;
							$('#TotalHarga').val(TotalHarga);
							
						})

						let bayar_kasir = 0;
						let order = [];
						let no = 1;
						$('#proses').on('click', function () {
							let list = [];

							// Mengambil nilai input
							
							let kdPenjualan = $('#kdPenjualan').val();
							let kdBarang = $('#NamaBarang option:selected').attr('value');
							let NamaBarang = $('#NamaBarang option:selected').attr('data');
							let Stok = $('#Stok').val();
							let Jumlah = $('#Jumlah').val();
							let HargaJual = $('#HargaJual').val();
							let TotalHarga = parseInt($('#TotalHarga').val());
							let bayar = $('#bayar').val();

							if  (parseInt(Jumlah) > Stok){
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: 'Jumlah Barang melebihi Stok!',
								})
							}else if (Jumlah == "") {
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: 'Isi Jumlah Pesanan Terlebih Dahulu!',
								})
							}else if (Jumlah == "0"){
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: 'Jumlah Pesanan tidak boleh "0"!',
								})
							}else{
								$.ajax({
									url: 'http://sofiyatul.kencang.id/tambahdetail.php',
									data: {
										kdPenjualan: kdPenjualan,
										kdBarang: kdBarang,
										NamaBarang: NamaBarang,
										HargaJual: HargaJual,
										Stok: Stok,
										Jumlah: Jumlah,
										TotalHarga: TotalHarga,
										bayar: bayar,
									},
									method: 'post',
									dataType: 'json',
									success: function (data) {
										console.log(data);
									}
								})

								// Menambahkan ke array list
								list.push({
									'kdBarang': kdBarang,
									'NamaBarang': NamaBarang,
									'HargaJual': HargaJual,
									'Stok': Stok,
									'Jumlah': Jumlah,
									'TotalHarga': TotalHarga,
									'bayar': bayar
								});

								// Otomotis menjumlahkan total bayar
								bayar_kasir += TotalHarga;
								$('#totalbayar').val(bayar_kasir);

								// Melakukan pengulangan di list pesanan
								$.each(list, function (i, data) {
									$('#list_pesan').append(`
									<tr>
										<td>` + no++ + `</td>
										<td>` + data.NamaBarang + `</td>
										<td>` + data.HargaJual + `</td>
										<td>` + data.Jumlah + `</td>
										<td>` + data.TotalHarga + `</td>
									</tr>
								`)
								})

								// Menghapus nilai
								$('#NamaBarang').val("")
								$('#HargaJual').val("");
								$('#Jumlah').val("");
								$('#TotalHarga').val("")
							}
						
						})

						$('#batal_transaksi').on('click', function () {
							let kdPenjualan = $('#kdPenjualan').val();
							let TotalBayar = $('#totalbayar').val();

							if (TotalBayar == "") {
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: 'Tambah Menu Terlebih Dahulu!',
								})
								console.log("oke")
							} else {
								$.ajax({
									url: 'http://sofiyatul.kencang.id/bataltransaksi.php',
									data: {
										kdPenjualan: kdPenjualan
									},
									method: 'post',
									dataType: 'json',
									success: function (data) {

									}
								})
								Swal.fire({
									icon: 'success',
									title: 'Berhasil!',
									text: 'Transaksi Berhasil Dibatalkan!',
								})
								window.location.reload();
							}
						})

						$('#bayar_transaksi').on('click', function () {
							let NamaAdmin = $('#NamaAdmin').val();
							let kdPenjualan = $('#kdPenjualan').val();
							let bayar = $('#bayar').val();
							let TotalBayar = $('#totalbayar').val();

							if (TotalBayar == "") {
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: 'Tambah Menu Terlebih Dahulu!',
								})
							}else if (bayar == "") {
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: 'Masukkan Uang Bayar Terlebih Dahulu!',
								})
							}else if (bayar < TotalBayar) {
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: 'Uang bayar kurang dari Total Bayar!',
								})
							} else {
								$.ajax({
									url: 'http://sofiyatul.kencang.id/bayartransaksi.php',
									data: {
										kdPenjualan: kdPenjualan,
										NamaAdmin: NamaAdmin,
										bayar: bayar,
										TotalBayar: TotalBayar
									},
									method: 'post',
									dataType: 'json',
									success: function (data) {

									}
								})
								Swal.fire({
									icon: 'success',
									title: 'Berhasil!',
									text: 'Transaksi Berhasil Ditambahkan!',
								})
								window.location.reload();
								window.location.href = 'http://sofiyatul.kencang.id/print.php?id=' + kdPenjualan
							}
						})
					})
				})
				</script>
			</div>
		</div>
	</div>
	<script src="jquery.js"></script>
	<script>
		$(document).on('click', '#logout', function(e) {
			e.preventDefault();

			Swal.fire({
				title: 'Apakah anda yakin?',
				text: "Anda akan Keluar!",
				icon: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Ya, Keluar Saja!'
				}).then((result) => {
				if (result.isConfirmed) {
					window.location ='login.php';				
				}
			})
		})
	</script>
</body>
</html>