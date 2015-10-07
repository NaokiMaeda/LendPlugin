package ubi.system.lendplugin;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface FelicaLib extends Library{
	 static FelicaLib INSTANCE = (FelicaLib)Native.loadLibrary("felicalib", FelicaLib.class);

     Pointer	pasori_open(String dummy);
     int		pasori_init(Pointer pasoriHandle);
     void		pasori_close(Pointer pasoriHandle);
     Pointer	felica_polling(Pointer pasoriHandle, short systemCode, byte rfu, byte time_slot);
     void		felica_free(Pointer felicaHandle);
     void		felica_getidm(Pointer felicaHandle, byte[] data);
     void		felica_getpmm(Pointer felicaHandle, byte[] data);
     int 		felica_read_without_encryption02(Pointer felicaHandle, int serviceCode, int mode, byte addr, byte[] data);
}
