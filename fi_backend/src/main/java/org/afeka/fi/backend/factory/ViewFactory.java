package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.exception.DataNotFoundException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ViewFactory <E> extends FiCommon {

    public abstract void export(String path) throws Exception;
    public abstract void add(E e) throws Exception;

    public void save(String data,String path) throws IOException {
        Files.write(Paths.get(path),data.getBytes());
    }

}
