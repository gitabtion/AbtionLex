package models;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author abtion.
 * @since 17/9/28 18:33.
 */
public class NFAModel {
    private HashSet<String> k;
    private HashSet<String> sigma;
    private HashMap<String,HashMap<String,HashSet<String>>> f;  //外层key为status.toString，内层key为sigma中元素。
    private HashSet<String> s;
    private HashSet<String> z;

    public NFAModel(HashSet<String> k, HashSet<String> sigma, HashMap<String, HashMap<String, HashSet<String>>> f, HashSet<String> s, HashSet<String> z) {
        this.k = k;
        this.sigma = sigma;
        this.f = f;
        this.s = s;
        this.z = z;
    }

    public NFAModel() {
        k = new HashSet<>();
        sigma = new HashSet<>();
        k = new HashSet<>();
        s = new HashSet<>();
        z = new HashSet<>();
    }

    public HashSet<String> getK() {
        return k;
    }

    public void setK(HashSet<String> k) {
        this.k = k;
    }

    public HashSet<String> getSigma() {
        return sigma;
    }

    public void setSigma(HashSet<String> sigma) {
        this.sigma = sigma;
    }

    public HashMap<String, HashMap<String, HashSet<String>>> getF() {
        return f;
    }

    public void setF(HashMap<String, HashMap<String, HashSet<String>>> f) {
        this.f = f;
    }

    public HashSet<String> getS() {
        return s;
    }

    public void setS(HashSet<String> s) {
        this.s = s;
    }

    public HashSet<String> getZ() {
        return z;
    }

    public void setZ(HashSet<String> z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NFAModel nfaModel = (NFAModel) o;

        if (k != null ? !k.equals(nfaModel.k) : nfaModel.k != null) return false;
        if (sigma != null ? !sigma.equals(nfaModel.sigma) : nfaModel.sigma != null) return false;
        if (f != null ? !f.equals(nfaModel.f) : nfaModel.f != null) return false;
        if (s != null ? !s.equals(nfaModel.s) : nfaModel.s != null) return false;
        return z != null ? z.equals(nfaModel.z) : nfaModel.z == null;
    }

    @Override
    public int hashCode() {
        int result = k != null ? k.hashCode() : 0;
        result = 31 * result + (sigma != null ? sigma.hashCode() : 0);
        result = 31 * result + (f != null ? f.hashCode() : 0);
        result = 31 * result + (s != null ? s.hashCode() : 0);
        result = 31 * result + (z != null ? z.hashCode() : 0);
        return result;
    }
}
