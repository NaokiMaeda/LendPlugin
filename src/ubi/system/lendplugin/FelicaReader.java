package ubi.system.lendplugin;

import com.sun.jna.Pointer;

public class FelicaReader {
	public final	static	short WILDCARD = (short) 0xFFFF;
	
	private	Pointer		pasoriHandle;
	private	Pointer		felicaHandle;
	
	public FelicaReader() throws FelicaException{
		pasoriHandle = FelicaLib.INSTANCE.pasori_open(null);
		if(pasoriHandle == null)	throw new FelicaException("felicalib.dll");
		if(FelicaLib.INSTANCE.pasori_init(pasoriHandle) != 0)
			throw new FelicaException("PaSoRi");
	}
	
	public void close(){
		if(felicaHandle != Pointer.NULL)	FelicaLib.INSTANCE.felica_free(felicaHandle);
		if(pasoriHandle != Pointer.NULL)	FelicaLib.INSTANCE.pasori_close(pasoriHandle);
	}
	
	public void polling(Short systemCode) throws FelicaException{
		FelicaLib.INSTANCE.felica_free(felicaHandle);
		felicaHandle = FelicaLib.INSTANCE.felica_polling(pasoriHandle, systemCode, (byte)0, (byte)0);
		if(felicaHandle == Pointer.NULL)	throw new FelicaException("");
	}
	
	public byte[] getIDm() throws FelicaException{
		if(felicaHandle == Pointer.NULL){
			throw new FelicaException("No polling executed");
		}
		byte[] buf = new byte[8];
		FelicaLib.INSTANCE.felica_getidm(felicaHandle , buf);
		return buf;
	}
	
	public String getID(short systemCode) throws FelicaException{
		FelicaLib.INSTANCE.felica_free(felicaHandle);
        felicaHandle = FelicaLib.INSTANCE.felica_polling(pasoriHandle, systemCode, (byte)0, (byte)0);
        if(felicaHandle==Pointer.NULL) {
        	return null;
        	//throw new FelicaException("�J�[�h�ǂݎ�莸�s");
        }
        byte[] buf = new byte[8];
        FelicaLib.INSTANCE.felica_getidm(felicaHandle, buf);
        return String.format("%02X%02X%02X%02X%02X%02X%02X%02X", buf[0],buf[1],buf[2],buf[3],buf[4],buf[5],buf[6],buf[7]);
	}
	
}