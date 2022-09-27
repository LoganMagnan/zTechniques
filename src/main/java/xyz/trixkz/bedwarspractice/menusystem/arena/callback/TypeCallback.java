package xyz.trixkz.bedwarspractice.menusystem.arena.callback;

import java.io.Serializable;

public interface TypeCallback<T> extends Serializable {

    void callback(T data);
}
