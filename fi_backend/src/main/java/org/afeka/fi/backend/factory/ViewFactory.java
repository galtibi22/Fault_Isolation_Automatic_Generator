package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.common.FiCommon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ViewFactory <V> extends FiCommon {

    protected V view;
    public abstract void export(Path path,V v) throws Exception;
    // public abstract void add(E e) throws Exception;

    protected V get(){
        return view;
    };

    public void save(String data, Path path) throws IOException {
        Files.write(path,data.getBytes());
    }

}
