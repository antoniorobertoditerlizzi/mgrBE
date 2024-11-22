package com.vigilfuoco.mgr.model;


public class ModelloCompilatoDTO {

	   	private Long idModello;
	    private Long idRichiesta;
	    private String fileModello;
	    private String transcodificaFile;
	    
	    
		public Long getIdModello() {
			return idModello;
		}


		public void setIdModello(Long idModello) {
			this.idModello = idModello;
		}


		public Long getIdRichiesta() {
			return idRichiesta;
		}


		public void setIdRichiesta(Long idRichiesta) {
			this.idRichiesta = idRichiesta;
		}


		public String getFileModello() {
			return fileModello;
		}


		public void setFileModello(String fileModello) {
			this.fileModello = fileModello;
		}


		public String getTranscodificaFile() {
			return transcodificaFile;
		}


		public void setTranscodificaFile(String transcodificaFile) {
			this.transcodificaFile = transcodificaFile;
		}


		@Override
		public String toString() {
			return "ModelloCompilatoDTO [idModello=" + idModello + ", idRichiesta=" + idRichiesta + ", fileModello="
					+ fileModello + ", transcodificaFile=" + transcodificaFile + "]";
		}

	    
	    
}
