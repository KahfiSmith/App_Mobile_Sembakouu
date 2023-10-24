<?php
session_start();
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width-device-width, initial-scale=1">
    <title>Login || Sembakouu</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css2?family=Quicksand&display=swap" rel="stylesheet">
	<script src="dist/sweetalert2.all.min.js"></script>
</head>
<body id="bg-login">
    <div class="box-login">
        <h2>Login</h2>
		<img src="img/Logo500.png">
        <form action="" method="POST">
			<input type="text" name="user" placeholder="Username" id="nama" class="input-control" autocomplete="off">
			<input type="password" name="pass" placeholder="Password" id="pass" class="input-control" minlength="4" autocomplete="off">
			<input type="submit" name="submit" value="Login" class="btn" id="btn">
			<a href="https://wa.wizard.id/c5da7d" target="_blank"> <br>Lupa Password! </a>
		</form>
		<?php
			if(isset($_POST['submit'])){
				
				include 'config.php';
				
				$user = $_POST['user'];
				$pass = $_POST['pass'];

				$cek = mysqli_query($conn, "SELECT * FROM penjual WHERE Username ='".$user."' AND Password = '".MD5($pass)."'");
				$cek2 = mysqli_query($conn, "SELECT * FROM pembeli WHERE Username ='".$user."' AND Password = '".($pass)."'");
				$cek3 = mysqli_query($conn, "SELECT * FROM penjual WHERE Username ='".$user."'");
				$cek4 = mysqli_query($conn, "SELECT * FROM penjual WHERE Password ='".MD5($pass)."'");
				if(mysqli_num_rows($cek) > 0){
					$d = mysqli_fetch_object($cek);
					$_SESSION['status_login'] = true;
					$_SESSION['user_global'] = $d;
					$_SESSION['idAdmin'] = $d->idAdmin;
					?>
					<script>
					Swal.fire({
						title: 'Login Berhasil!',
						text: 'Selamat Datang <?php echo $_SESSION['user_global']->NamaAdmin ?>!',
						icon: 'success'
					}).then((result) => {
						window.location="home.php";
					})
					</script>
					<?php
				}elseif (mysqli_num_rows($cek2) > 0){
					$d = mysqli_fetch_object($cek);
					?>
					<script>
					Swal.fire({
						icon: 'error',
						title: 'Oops...',
						text: 'Anda tidak Memiliki Access Login, Silahkan kembali!',
					  }).then((result) => {
						window.location="login.php";
					})
					</script>
					<?php
				}elseif (mysqli_num_rows($cek3) > 0){
					$d = mysqli_fetch_object($cek);
					?>
					<script>
					Swal.fire({
						icon: 'error',
						title: 'Oops...',
						text: 'Password anda salah!',
					  }).then((result) => {
						window.location="login.php";
					})
					</script>
					<?php
				}elseif (mysqli_num_rows($cek4) > 0){
					$d = mysqli_fetch_object($cek);
					?>
					<script>
					Swal.fire({
						icon: 'error',
						title: 'Oops...',
						text: 'Username anda salah!',
					  }).then((result) => {
						window.location="login.php";
					})
					</script>
					<?php
				}else{
					?>
					<script>
					Swal.fire({
						icon: 'error',
						title: 'Oops...',
						text: 'Username dan Password anda salah!',
					  }).then((result) => {
						window.location="login.php";
					})
					</script>
					<?php
				}
			}
		?>
	</div>
</body>

</html>