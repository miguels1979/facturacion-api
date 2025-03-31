package com.kosmostecnologia.facturador.domain.helpers;

import com.kosmostecnologia.facturador.domain.service.ActividadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

@Component
public class GZIPHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(GZIPHelper.class);

    private static final byte[] BUFFER_SIZE = new byte[1024];

    public boolean compress(File archivo){
        try(GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream(archivo.getAbsolutePath() + ".zip"));
            FileInputStream fileInputStream = new FileInputStream(archivo)){
            int len;
            while ((len = fileInputStream.read(BUFFER_SIZE)) != -1){
                gzipOutputStream.write(BUFFER_SIZE, 0 , len);
            }
        }catch(IOException e){
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }
}
