package com.example.sembakomobile.Model.Retrofit;


public class DataItem{

	private String idPembeli;
	private String NamaPembeli;
	private String Alamat;
	private String no_hp;
	private String Username;
	private String Password;

	public DataItem(String idPembeli, String NamaPembeli, String Alamat, String no_hp, String Username, String Password){
		this.idPembeli = idPembeli;
		this.NamaPembeli = NamaPembeli;
		this.Alamat = Alamat;
		this.no_hp = no_hp;
		this.Username = Username;
		this.Password = Password;
	}

	public String getIdPembeli() { return idPembeli; }

	public void setIdPembeli(String idPembeli) {
		this.idPembeli = idPembeli;
	}

	public String getNamaPembeli() {
		return NamaPembeli;
	}

	public void setNamaPembeli(String namaPembeli) {
		NamaPembeli = namaPembeli;
	}

	public String getAlamat() {
		return Alamat;
	}

	public void setAlamat(String alamat) {
		Alamat = alamat;
	}

	public String getNo_hp() {
		return no_hp;
	}

	public void setNo_hp(String no_hp) {
		this.no_hp = no_hp;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
}